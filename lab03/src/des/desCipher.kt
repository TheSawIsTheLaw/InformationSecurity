package des

import longToBytes


// Add zeroes until 64 bits and add a number of added bytes
fun prepareWorkBlocks(bytes: List<Byte>, mode: String): List<Long>
{
    val workBytes = bytes.toMutableList()

    if (mode == "cipher")
    {
        val addedZeroes: Byte = if (workBytes.size >= 8)
            (8 - workBytes.size % 8).toByte()
        else
            (8 - workBytes.size).toByte()
        for (i in 0 until addedZeroes + 7)
        {
            workBytes.add(0)
        }
        workBytes.add(addedZeroes)
    }

    print(workBytes)

    val outBlocks = MutableList<Long>(workBytes.size / 8) { _ -> 0 }

    // God, save me from my cycles...
    var curBlockNum = 0
    var passedBytes = 0
    for (i in workBytes.indices)
    {
        outBlocks[curBlockNum] = (outBlocks[curBlockNum] shl 8) or workBytes[i].toLong()
        passedBytes++
        if (passedBytes == 8)
        {
            passedBytes = 0
            curBlockNum++
        }
    }

    return outBlocks
}

fun makeBlocksToOneList(byteBlocks: List<List<Byte>>): List<Byte>
{
    val outList = mutableListOf<Byte>()
    for (block in byteBlocks)
    {
        for (byte in block)
        {
            outList.add(byte)
        }
    }

    return outList
}

fun formKeys(fKey: Int, sKey: Int): List<Long>
{
    val keys48bit = MutableList<Long>(16) { _ -> 0 } // In Long we have 64 bits

    var currentShift: Int

    var curFKey: Int = fKey
    var curSKey: Int = sKey

    for (i in 0 until 16)
    {
        currentShift = when (i)
        {
            0, 1, 8, 15 -> 1
            else        -> 2
        }

        curFKey = ((curFKey shl currentShift) or (curFKey shr (-currentShift and 27))) and ((1 shl 32) - 1)
        curSKey = ((curSKey shl currentShift) or (curSKey shr (-currentShift and 27))) and ((1 shl 32) - 1)

        val key56bit: Long = ((curFKey.toLong() shl 28) or curSKey.toLong()) shl 4

        // compress
        var curKey48bit: Long = 0
        for (j in 0 until 48)
        {
            curKey48bit = curKey48bit or ((key56bit shr (64 - DESTables.compressTable[j]) and 0x01) shl (63 - j))
        }
        keys48bit[i] = curKey48bit
    }

    return keys48bit
}

fun expandKey(key: List<Byte>): List<Long>
{
    var key64bit: Long = 0
    for (i in key.indices)
    {
        key64bit = (key64bit shl 8) or key[i].toLong()
    }

    var fKey: Int = 0
    var sKey: Int = 0

    for (i in 0 until 28)
    {
        fKey = fKey or (((key64bit shr (64 - DESTables.k1PTable[i]) and 0x01) shl (31 - i))).toInt()
        sKey = sKey or (((key64bit shr (64 - DESTables.k2PTable[i]) and 0x01) shl (31 - i))).toInt()
    }

    return formKeys(fKey, sKey)
}

fun makePermutationWith(block: Long, table: List<Byte>): Long
{
    var newBlock: Long = 0
    for (i in 0 until 64)
    {
        newBlock = newBlock or ((block shr (64 - table[i])) and 0x01) shl (63 - i)
    }
    return newBlock
}

fun makeInitialPermutationAndDivide(block: Long): Pair<Int, Int>
{
    val newBlock = makePermutationWith(block, DESTables.ipTable)

    return Pair((newBlock shr 32).toInt(), newBlock.toInt())
}

fun makeFinalPermutationAndMakeBlock(left: Int, right: Int): Long =
    makePermutationWith((left.toLong() shl 32) or right.toLong(), DESTables.fpTable)

fun expandTo48bit(block: Int): Long
{
    var newBlock: Long = 0
    for (i in 0 until 48)
    {
        newBlock = newBlock or ((((block shr (32 - DESTables.epTable[i])) and 0x01)).toLong() shl (63 - i))
    }

    return newBlock
}

fun lrBits(block6bit: Byte): Byte
{
    return (((block6bit.toInt() shr 6) and 0x2) or ((block6bit.toInt() shr 2) and 0x1)).toByte()
}

fun middleBits(block6bit: Byte): Byte
{
    return ((block6bit.toInt() shr 3) and 0xF).toByte()
}

fun substitutions(block: Long): Int
{
    val subBlocks6bit = MutableList<Byte>(8) { _ -> 0 }
    val outBlocks4bit = MutableList<Byte>(4) { _ -> 0 }

    for (i in 0 until 8)
    {
        subBlocks6bit[i] = ((block shr (58 - (i * 6))) shl 2).toByte()
    }

    var block2Bit: Byte
    var block4Bit: Byte
    var j = 0
    for (i in 0 until 8 step 2)
    {
        block2Bit = lrBits(subBlocks6bit[i])
        block4Bit = middleBits(subBlocks6bit[i])
        outBlocks4bit[j] = DESTables.sBox[i][block2Bit.toInt()][block4Bit.toInt()]

        block2Bit = lrBits(subBlocks6bit[i + 1])
        block4Bit = middleBits(subBlocks6bit[i + 1])
        outBlocks4bit[j] =
            ((outBlocks4bit[j].toInt() shl 4) or DESTables.sBox[i + 1][block2Bit.toInt()][block4Bit.toInt()].toInt()).toByte()

        j++
    }

    var outVal: Int = 0
    for (i in outBlocks4bit.indices)
    {
        outVal = (outVal shl 8) or outBlocks4bit[i].toInt()
    }

    return outVal
}

fun feistelFunc(blockPart: Int, key48bit: Long): Int
{
    var blockExpandedTo48bit = expandTo48bit(blockPart) xor key48bit

    var newBlock32: Int = substitutions(blockExpandedTo48bit)
    var permutedBlock32: Int = 0
    for (i in 0 until 32)
    {
        permutedBlock32 = permutedBlock32 or (((newBlock32 shr (32 - DESTables.permTable[i])) and 0x01) shl (31 - i))
    }
    return permutedBlock32
}

fun feistelCipher(leftPart: Int, rightPart: Int, keys: List<Long>): Pair<Int, Int>
{
    var left = leftPart
    var right = rightPart
    for (round in 0 until 16)
    {
        left = right.also { right = feistelFunc(right, keys[round]) xor left }
    }

    return Pair(right, left)
}

fun feistelDecipher(leftPart: Int, rightPart: Int, keys: List<Long>): Pair<Int, Int>
{
    var left = leftPart
    var right = rightPart
    for (round in 15 downTo 0)
    {
        left = right.also { right = feistelFunc(right, keys[round]) xor left }
    }

    return Pair(right, left)
}

fun cipherOrDecipher(bytesToCipher: List<Byte>, key: List<Byte>, mode: String = "cipher"): List<Long>
{
    if (key.size != 8)
    {
        throw InvalidKeyException("Key is invalid: we need 8 bytes in it")
    }

    val workBlocks = prepareWorkBlocks(bytesToCipher, mode)

    val keys48bit = expandKey(key)

    val outBlocks = mutableListOf<Long>()
    for (block in workBlocks)
    {
        val (curLeft, curRight) = makeInitialPermutationAndDivide(block)

        val (outLeft, outRight) = if (mode == "cipher")
        {
            feistelCipher(curLeft, curRight, keys48bit)
        }
        else
        {
            feistelDecipher(curLeft, curRight, keys48bit)
        }

        outBlocks.add(makeFinalPermutationAndMakeBlock(outLeft, outRight))
    }

    return outBlocks
}