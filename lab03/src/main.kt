import java.io.DataInputStream
import java.io.File
import java.io.FileNotFoundException

fun getBinaryFilling(fileName: String): List<Byte>
{
    val stream: DataInputStream
    try
    {
        stream = DataInputStream(File(fileName).inputStream())
    }
    catch (exception: FileNotFoundException)
    {
        println("File not found")
        return listOf()
    }

    val outList = mutableListOf<Byte>()
    while (stream.available() > 0)
    {
        outList.add(stream.readByte())
    }

    return outList.toList()
}

fun main(args: Array<String>)
{
    if (args.isEmpty())
    {
        println("Required one argument: name of binary file to cipher and decipher")
        return
    }

    val fillingToCipher = getBinaryFilling(args[0])
    if (fillingToCipher.isEmpty())
    {
        println("Nothing to cipher")
        return
    }
}