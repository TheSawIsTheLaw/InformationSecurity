package fileOperations

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException

fun getBinaryFilling(fileName: String): List<UByte>
{
    val stream: DataInputStream
    try
    {
        stream = DataInputStream(File(fileName).inputStream())
    } catch (exception: FileNotFoundException)
    {
        println("File not found")
        return listOf()
    }

    val outList = mutableListOf<UByte>()
    while (stream.available() > 0)
    {
        outList.add(stream.readByte().toUByte())
    }

    return outList.toList()
}

@OptIn(ExperimentalUnsignedTypes::class) fun createBinary(fileName: String, bytes: List<UByte>): Int
{
    val stream: DataOutputStream
    try
    {
        val outputFile = File(fileName)
        outputFile.createNewFile()
        stream = DataOutputStream(outputFile.outputStream())
    } catch (exception: Exception)
    {
        println("Error while creating file")
        return 1
    }

    stream.write(bytes.toUByteArray().toByteArray())
    return 0
}