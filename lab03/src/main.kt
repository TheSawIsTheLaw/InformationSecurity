import des.cipherOrDecipher
import fileOperations.createBinary
import fileOperations.getBinaryFilling

const val addToName: String = "wololo"

@OptIn(ExperimentalUnsignedTypes::class) fun uLongToByteArray(value: ULong): UByteArray
{
    val bytes = UByteArray(8)
    var convertValue = value
    for (i in 7 downTo 0)
    {
        bytes[i] = (convertValue and 0xFFFFu).toUByte()
        convertValue = convertValue shr 8
    }
    return bytes
}

@OptIn(ExperimentalUnsignedTypes::class) fun main(args: Array<String>)
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
        for (i in uLongToByteArray(it))
        {
            outInBytes.add(i)
        }
    }
    println("Output: $outInBytes")

    createBinary(addToName + fileName, outInBytes)
}