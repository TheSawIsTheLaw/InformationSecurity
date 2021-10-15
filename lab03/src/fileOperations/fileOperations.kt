package fileOperations

import java.io.DataInputStream
import java.io.DataOutputStream
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

fun createBinary(fileName: String, bytes: List<Byte>): Int
{
    val stream: DataOutputStream
    try
    {
        val outputFile = File(fileName)
        outputFile.createNewFile()
        stream = DataOutputStream(outputFile.outputStream())
    }
    catch (exception: Exception)
    {
        println("Error while creating file")
        return 1
    }

    stream.write(bytes.toByteArray())
    return 0
}