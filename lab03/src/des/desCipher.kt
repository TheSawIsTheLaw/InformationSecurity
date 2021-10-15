package des

// Add zeroes until 64 bits and add a number of added bytes
fun prepareWorkBlocks(bytes: List<Byte>): List<Long>
{
    val workBytes = bytes.toMutableList()
    val addedZeroes: Byte = if (workBytes.size >= 8)
        (workBytes.size % 8).toByte()
    else
        (8 - workBytes.size).toByte()
    for (i in 0 until addedZeroes + 7)
    {
        workBytes.add(0)
    }
    workBytes.add(addedZeroes)

    val outBlocks = MutableList<Long>(workBytes.size / 8) { _ -> 0 }

    // God, save me from my cycles...
    var curBlockNum = 0
    var passedBytes = 0
    for (i in workBytes.indices)
    {
        outBlocks[curBlockNum] = (outBlocks[curBlockNum] shl 8) or workBytes[i].toLong()
        passedBytes++
        if (passedBytes == 7)
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

fun expandKey(key: List<Byte>): List<Long>
{
    val keys48bit = List<Long>(16) { _ -> 0 } // In Long we have 64 bits

    return keys48bit
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

fun cipher(bytesToCipher: List<Byte>, key: List<Byte>): List<Long>
{
    if (key.size != 8)
    {
        throw InvalidKeyException("Key is invalid: we need 8 bytes in it")
    }

    val workBlocks = prepareWorkBlocks(bytesToCipher)

    val keys48bit = expandKey(key)

    for (block in workBlocks)
    {
        val (curLeft, curRight) = makeInitialPermutationAndDivide(block)
    }

    return workBlocks
}