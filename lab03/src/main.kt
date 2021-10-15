import fileOperations.getBinaryFilling

const val addToName: String = "wololo"

fun main(args: Array<String>)
{
    if (args.isEmpty())
    {
        println("Required one argument: name of binary file to cipher and decipher")
        return
    }

    val fileName = args[0]

    val fillingToCipher = getBinaryFilling(fileName)
    if (fillingToCipher.isEmpty())
    {
        println("Nothing to cipher")
        return
    }
}