package des

// Add zeroes until 64 bits and add a number of added bytes
fun prepareWorkBlocks(bytes: List<UByte>, mode: String): List<ULong>
{
    val workBytes = bytes.toMutableList()

    if (mode == "cipher")
    {
        val addedZeroes: UByte = if (workBytes.size >= 8)
            (8 - workBytes.size % 8).toUByte()
        else
            (8 - workBytes.size).toUByte()
        for (i in 0 until addedZeroes.toInt() + 7)
        {
            workBytes.add(0u)
        }
        workBytes.add(addedZeroes)
    }

    val outBlocks = MutableList<ULong>(workBytes.size / 8) { _ -> 0u }

    // God, save me from my cycles...
    var curBlockNum = 0
    var passedBytes = 0
    for (i in workBytes.indices)
    {
        outBlocks[curBlockNum] = (outBlocks[curBlockNum] shl 8) or workBytes[i].toULong()
        passedBytes++
        if (passedBytes == 8)
        {
            passedBytes = 0
            curBlockNum++
        }
    }

    return outBlocks
}

fun formKeys(fKey: UInt, sKey: UInt): List<ULong>
{
    val keys48bit = MutableList<ULong>(16) { _ -> 0u } // In Long we have 64 bits

    var currentShift: Int

    var curFKey = fKey
    var curSKey = sKey

    for (i in 0 until 16)
    {
        currentShift = when (i)
        {
            0, 1, 8, 15 -> 1
            else        -> 2
        }

////        println()
//        println("До curFKey $curFKey curSKey $curSKey")
//        println(((1.toULong() shl 32) - 1u).toString(2))
//        println(((curSKey.toULong() shl currentShift) or (curSKey.toULong() shr (-currentShift and 27))).toString(2))
        curFKey =
            (((curFKey.toULong() shl currentShift) or (curFKey.toULong() shr (-currentShift and 27))) and ((1.toULong() shl 32) - 1u)).toUInt()
        curSKey =
            (((curSKey.toULong() shl currentShift) or (curSKey.toULong() shr (-currentShift and 27))) and ((1.toULong() shl 32) - 1u)).toUInt()
//        println("После curFKey $curFKey curSKey $curSKey")


        val key56bit: ULong = (((curFKey.toULong() shr 4) shl 32) or curSKey.toULong()) shl 4
//        println("Key: $key56bit")

        // compress
        var curKey48bit: ULong = 0u
        for (j in 0 until 48)
        {
            curKey48bit = curKey48bit or (((key56bit shr (64 - DESTables.compressTable[j].toInt())) and 1u) shl (63 - j))
        }
        keys48bit[i] = curKey48bit
    }

    return keys48bit
}

fun expandKey(key: List<UByte>): List<ULong>
{
    var key64bit: Long = 0
    for (i in key.indices)
    {
        key64bit = (key64bit shl 8) or key[i].toLong()
    }

    var fKey: UInt = 0u
    var sKey: UInt = 0u

    for (i in 0 until 28)
    {
        fKey = fKey or (((key64bit shr (64 - DESTables.k1PTable[i].toInt()) and 0x01) shl (31 - i))).toUInt()
        sKey = sKey or (((key64bit shr (64 - DESTables.k2PTable[i].toInt()) and 0x01) shl (31 - i))).toUInt()
    }

    return formKeys(fKey, sKey)
}

fun makePermutationWith(block: ULong, table: List<UByte>): ULong
{
    var newBlock: ULong = 0u
    for (i in 0 until 64)
    {
        newBlock = newBlock or (((block shr (64 - table[i].toInt())) and 1u) shl (63 - i))
    }
    return newBlock
}

fun makeInitialPermutationAndDivide(block: ULong): Pair<UInt, UInt>
{
    val newBlock = makePermutationWith(block, DESTables.ipTable)

    return Pair((newBlock shr 32).toUInt(), newBlock.toUInt())
}

fun makeFinalPermutationAndMakeBlock(left: UInt, right: UInt): ULong =
    makePermutationWith((left.toULong() shl 32) or right.toULong(), DESTables.fpTable)

fun expandTo48bit(block: UInt): ULong
{
    var newBlock: ULong = 0u
    for (i in 0 until 48)
    {
        newBlock = newBlock or ((((block shr (32 - DESTables.epTable[i].toInt())) and 1u)).toULong() shl (63 - i))
    }

    return newBlock
}

fun lrBits(block6bit: UByte): UByte
{
    return (((block6bit.toUInt() shr 6) and 2u) or ((block6bit.toUInt() shr 2) and 1u)).toUByte()
}

fun middleBits(block6bit: UByte): UByte
{
    return ((block6bit.toUInt() shr 3) and 0xFu).toUByte()
}

fun substitutions(block: ULong): UInt
{
    val subBlocks6bit = MutableList<UByte>(8) { 0u }
    val outBlocks4bit = MutableList<UByte>(4) { 0u }

    for (i in 0 until 8)
    {
        subBlocks6bit[i] = ((block shr (58 - (i * 6))) shl 2).toUByte()
    }

    var block2Bit: UByte
    var block4Bit: UByte
    var j = 0
    for (i in 0 until 8 step 2)
    {
        block2Bit = lrBits(subBlocks6bit[i])
        block4Bit = middleBits(subBlocks6bit[i])
        outBlocks4bit[j] = DESTables.sBox[i][block2Bit.toInt()][block4Bit.toInt()]

        block2Bit = lrBits(subBlocks6bit[i + 1])
        block4Bit = middleBits(subBlocks6bit[i + 1])
        outBlocks4bit[j] =
            ((outBlocks4bit[j].toUInt() shl 4) or DESTables.sBox[i + 1][block2Bit.toInt()][block4Bit.toInt()].toUInt()).toUByte()

        j++
    }

    var outVal: UInt = 0u
    for (i in outBlocks4bit.indices)
    {
        outVal = (outVal shl 8) or outBlocks4bit[i].toUInt()
    }

    return outVal
}

fun feistelFunc(blockPart: UInt, key48bit: ULong): UInt
{
    val blockExpandedTo48bit = expandTo48bit(blockPart) xor key48bit

    val newBlock32: UInt = substitutions(blockExpandedTo48bit)
    var permutedBlock32: UInt = 0u
    for (i in 0 until 32)
    {
        permutedBlock32 = permutedBlock32 or (((newBlock32 shr ((32u - DESTables.permTable[i].toUInt()).toInt())) and 1u) shl (31 - i))
    }
    return permutedBlock32
}

fun feistelCipher(leftPart: UInt, rightPart: UInt, keys: List<ULong>): Pair<UInt, UInt>
{
    var left = leftPart
    var right = rightPart
    for (round in 0 until 16)
    {
        left = right.also { right = feistelFunc(right, keys[round]) xor left }
    }

    return Pair(right, left)
}

fun feistelDecipher(leftPart: UInt, rightPart: UInt, keys: List<ULong>): Pair<UInt, UInt>
{
    var left = leftPart
    var right = rightPart
    for (round in 15 downTo 0)
    {
        left = right.also { right = feistelFunc(right, keys[round]) xor left }
    }

    return Pair(right, left)
}

fun cipherOrDecipher(bytesToCipher: List<UByte>, key: List<UByte>, mode: String = "cipher"): List<ULong>
{
    if (key.size != 8)
    {
        throw InvalidKeyException("Key is invalid: we need 8 bytes in it")
    }

    val workBlocks = prepareWorkBlocks(bytesToCipher, mode)

    val keys48bit = expandKey(key)

    val outBlocks = mutableListOf<ULong>()
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