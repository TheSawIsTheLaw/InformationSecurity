package des

// Add zeroes until 64 bits and add a number of added bytes
fun prepareWorkBytes(bytes: List<Byte>): List<Byte>
{
    val workBytes = bytes.toMutableList()
    val addedZeroes = (workBytes.size % 8).toByte()
    for (i in 0 until addedZeroes)
    {
        workBytes.add(0)
    }

    workBytes.add(addedZeroes)
    return workBytes
}

fun expandKey(key: List<Byte>): List<Long>
{
    val keys48bit = List<Long>(16) { _ -> 0 } // In Long we have 64 bits

    return keys48bit
}

fun cipher(bytesToCipher: List<Byte>, key: List<Byte>): List<Byte>
{
    if (key.size != 8)
    {
        throw InvalidKeyException("Key is invalid: we need 8 bytes in it")
    }

    val workBytes = prepareWorkBytes(bytesToCipher)

    val keys48bit = expandKey(key)

    val curLeftPart: Int // 32 bit
    val curRightPart: Int // 32 bit


    return workBytes
}