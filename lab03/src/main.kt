import des.cipherOrDecipher
import fileOperations.createBinary
import fileOperations.getBinaryFilling
import java.nio.ByteBuffer

const val addToName: String = "wololo"

fun longToUInt32ByteArray(value: Long): ByteArray
{
    val bytes = ByteArray(4)
    bytes[3] = (value and 0xFFFF).toByte()
    bytes[2] = ((value ushr 8) and 0xFFFF).toByte()
    bytes[1] = ((value ushr 16) and 0xFFFF).toByte()
    bytes[0] = ((value ushr 24) and 0xFFFF).toByte()
    return bytes
}

fun main(args: Array<String>)
{
    if (args.size < 3 || args[1].length != 8)
    {
        println(
            "Required three arguments:\n" +
                    "1. Name of binary file to cipher and decipher;\n" +
                    "2. Key (8 byte);\n" +
                    "3. Mode (cipher or decipher; if not valid - decipher)"
        )
        return
    }

    val fileName = args[0]

    val fillingToCipher = getBinaryFilling(fileName)
    if (fillingToCipher.isEmpty())
    {
        println("Nothing to cipher")
        return
    }
    println("Input: $fillingToCipher")

    val key = mutableListOf<UByte>()
    for (letter in args[1])
    {
        key.add(letter.code.toUByte())
    }

    val out = cipherOrDecipher(fillingToCipher, key, args[2])
    val outInBytes = mutableListOf<UByte>()

    out.forEach {
        for (i in longToUInt32ByteArray(it.toLong()))
        {
            outInBytes.add(i.toUByte())
        }
    }
    println("Output: $outInBytes")

    createBinary(addToName + fileName, outInBytes)
}