import des.cipherOrDecipher
import fileOperations.createBinary
import fileOperations.getBinaryFilling
import java.nio.ByteBuffer

const val addToName: String = "wololo"

fun longToBytes(x: Long): ByteArray
{
    val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.putLong(x)
    return buffer.array()
}

fun main(args: Array<String>)
{
    if (args.size < 3 || args[1].length != 8)
    {
        println("Required three arguments:\n" +
                "1. Name of binary file to cipher and decipher;\n" +
                "2. Key (8 byte);\n" +
                "3. Mode (cipher or decipher; if not valid - decipher)")
        return
    }

    val fileName = args[0]

    val fillingToCipher = getBinaryFilling(fileName)
    if (fillingToCipher.isEmpty())
    {
        println("Nothing to cipher")
        return
    }

    val key = mutableListOf<Byte>()
    for (letter in args[1])
    {
        key.add(letter.code.toByte())
    }

    val out = cipherOrDecipher(fillingToCipher, key, args[2])
    val outInBytes = mutableListOf<Byte>()

    out.forEach {
        for (i in longToBytes(it))
        {
            outInBytes.add(i)
        }
    }
    println(outInBytes)
    createBinary(addToName + fileName, outInBytes)
}