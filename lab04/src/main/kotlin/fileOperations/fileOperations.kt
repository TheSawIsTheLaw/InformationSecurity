package fileOperations

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileNotFoundException

fun getBinaryFillingForCipher(fileName: String): List<ULong> {
    val stream = DataInputStream(File(fileName).inputStream())

    val outList = mutableListOf<ULong>()
    while (stream.available() > 0) {
        outList.add(stream.readUnsignedByte().toULong())
    }
//
//    // Принимаем в качестве кодируемого блока размер ULong, соответственно потребуется также дополнить файл до конечного размера
//    var lastLongToAdd = 0uL
//    val byteSize = Byte.SIZE_BITS
//    val lastBytesNumber = stream.available()
//
//    while (stream.available() > 0) {
//        lastLongToAdd = ((lastLongToAdd shl byteSize) or stream.readByte().toULong())
//    }
//
//    outList.add(lastLongToAdd)
//    outList.add(lastBytesNumber.toULong())

    return outList.toList()
}

fun getBinaryFillingForDecipher(fileName: String): List<ULong> {
    val stream = DataInputStream(File(fileName).inputStream())

    val outList = mutableListOf<ULong>()
    while (stream.available() > 0) {
        outList.add(stream.readLong().toULong())
    }
//
//    // Принимаем в качестве кодируемого блока размер ULong, соответственно потребуется также дополнить файл до конечного размера
//    var lastLongToAdd = 0uL
//    val byteSize = Byte.SIZE_BITS
//    val lastBytesNumber = stream.available()
//
//    while (stream.available() > 0) {
//        lastLongToAdd = ((lastLongToAdd shl byteSize) or stream.readByte().toULong())
//    }
//
//    outList.add(lastLongToAdd)
//    outList.add(lastBytesNumber.toULong())

    return outList.toList()
}

fun writeToFile(fileName: String, bytes: List<Byte>)
{
    val outputFile = File(fileName)
    outputFile.createNewFile()

    DataOutputStream(outputFile.outputStream()).write(bytes.toByteArray())
}

@OptIn(ExperimentalUnsignedTypes::class)
fun createBinaryCiphered(fileName: String, data: List<ULong>) {
    val bytes = mutableListOf<Byte>()
    for (currentLongIndex in 0 until (data.size - 1)) {
        val currentLong = data[currentLongIndex]
        for (currentByte in ULong.SIZE_BYTES - 1 downTo 0) {
            bytes.add((currentLong shr (ULong.SIZE_BYTES * currentByte)).toByte())
        }
    }

//    // Add last bytes
//    val numberOfLastBytes = data.last()
//    val numberToProcess = data[data.size - 2]
//    for (i in 0 until numberOfLastBytes.toInt()) {
//        bytes.add((numberToProcess shr (Byte.SIZE_BITS * i)).toByte())
//    }
    writeToFile(fileName, bytes)
}

@OptIn(ExperimentalUnsignedTypes::class)
fun createBinaryDeciphered(fileName: String, data: List<ULong>) {
    val bytes = mutableListOf<Byte>()
    for (currentLongIndex in data.indices)
        bytes.add(data[currentLongIndex].toByte())

//    // Add last bytes
//    val numberOfLastBytes = data.last()
//    val numberToProcess = data[data.size - 2]
//    for (i in 0 until numberOfLastBytes.toInt()) {
//        bytes.add((numberToProcess shr (Byte.SIZE_BITS * i)).toByte())
//    }
    writeToFile(fileName, bytes)
}
