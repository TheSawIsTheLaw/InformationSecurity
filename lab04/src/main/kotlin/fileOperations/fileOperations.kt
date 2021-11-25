package fileOperations

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException

fun getBinaryFilling(fileName: String): List<ULong> {
    val stream: DataInputStream
    try {
        stream = DataInputStream(File(fileName).inputStream())
    } catch (exception: FileNotFoundException) {
        println("File not found")
        return listOf()
    }

    val outList = mutableListOf<ULong>()
    while (stream.available() >= ULong.SIZE_BYTES) {
        outList.add(stream.readLong().toULong())
    }

    // Принимаем в качестве кодируемого блока размер ULong, соответственно потребуется также дополнить файл до конечного размера
    var lastLongToAdd = 0uL
    val byteSize = Byte.SIZE_BITS
    val lastBytesNumber = stream.available()

    for (i in 0 until lastBytesNumber) {
        lastLongToAdd = (lastLongToAdd or stream.readByte().toULong()) shl byteSize
    }

    for (i in stream.available() until ULong.SIZE_BYTES) {
        lastLongToAdd = lastLongToAdd shl byteSize
    }

    outList.add(lastLongToAdd)
    outList.add((ULong.SIZE_BYTES - lastBytesNumber).toULong())

    return outList.toList()
}

@OptIn(ExperimentalUnsignedTypes::class)
fun createBinary(fileName: String, data: List<ULong>): Boolean {
    val bytes = mutableListOf<Byte>()
    data.forEach { currentLong ->
        for (currentByte in ULong.SIZE_BYTES - 1 downTo 0) {
            bytes.add((currentLong shr (ULong.SIZE_BYTES * currentByte)).toByte())
        }
    }

    val stream: DataOutputStream
    try {
        val outputFile = File(fileName)
        outputFile.createNewFile()
        stream = DataOutputStream(outputFile.outputStream())
    } catch (exception: Exception) {
        println("Error while creating file")
        return false
    }

    stream.write(bytes.toByteArray())
    return true
}