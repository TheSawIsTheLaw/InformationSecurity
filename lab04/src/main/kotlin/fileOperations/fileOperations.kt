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

    println("Read from file: $outList")
    return outList.toList()
}

fun getBinaryFillingCiphered(fileName: String): List<ULong> {
    val stream: DataInputStream
    try {
        stream = DataInputStream(File(fileName).inputStream())
    } catch (exception: FileNotFoundException) {
        println("File not found")
        return listOf()
    }

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

    println("Read from file: $outList")
    return outList.toList()
}

//<!!!> There last long is for needed last bytes
@OptIn(ExperimentalUnsignedTypes::class)
fun createBinary(fileName: String, data: List<ULong>): Boolean {
    println("Got in create binary: $data")
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

    println("Ok, creating file $fileName")
    println(bytes)
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

@OptIn(ExperimentalUnsignedTypes::class)
fun createBinaryDeciphered(fileName: String, data: List<ULong>): Boolean {
    val bytes = mutableListOf<Byte>()
    for (currentLongIndex in data.indices) {
        println("Added byte: ${data[currentLongIndex].toByte()}")
        bytes.add(data[currentLongIndex].toByte())
    }

//    // Add last bytes
//    val numberOfLastBytes = data.last()
//    val numberToProcess = data[data.size - 2]
//    for (i in 0 until numberOfLastBytes.toInt()) {
//        bytes.add((numberToProcess shr (Byte.SIZE_BITS * i)).toByte())
//    }

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
