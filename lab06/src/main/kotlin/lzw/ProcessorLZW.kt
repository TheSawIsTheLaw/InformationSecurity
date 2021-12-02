package lzw

import java.io.File

class ProcessorLZW(val filename: String) {
    val dictionary = DictionaryLZW()

    private fun getFileBytes(): ByteArray {
        return File(filename).readBytes()
    }

    fun compressFile() {
        dictionary.initDictionary()

        val bytesToCompress = getFileBytes()
        val compressedBytes = mutableListOf<Byte>()

        // Короче, блять, меня это заебало, будем работать на стрингах

        var currentWord = mutableListOf<Byte>(bytesToCompress.first())
        for (currentByte in bytesToCompress.slice(1 until bytesToCompress.size)) {
            val currentWordWithByteAdded = currentWord.toByteArray() + currentByte

            if (dictionary.dictionary[currentWordWithByteAdded] != null) {
                currentWord = currentWordWithByteAdded.toMutableList()
            } else {
                compressedBytes.add(dictionary.dictionary.filterKeys { it.contentEquals(currentWord.toByteArray()) }.values.first().toByte())
                dictionary.dictionary[currentWordWithByteAdded] = dictionary.dictionary.size
                currentWord = mutableListOf(currentByte)
            }
        }

        dictionary.dictionary.forEach { println(); it.key.forEach { innerIt -> print("$innerIt ") } }

        compressedBytes.add(dictionary.dictionary.filterKeys { it.contentEquals(currentWord.toByteArray()) }.values.first().toByte())
        File("compressed$filename").writeBytes(compressedBytes.toByteArray())
    }

    fun decompressFile() {
        dictionary.initDictionary()

        val bytesToDecompress = getFileBytes()
        val decompressedBytes = mutableListOf<Byte>()

        var currentWord = mutableListOf<Byte>()
        for (currentCode in bytesToDecompress) {
            val currentWordWithByteAdded =
                currentWord.toByteArray() + dictionary.dictionary.filterValues { it == currentCode.toInt() }.keys.first()

            if (dictionary.dictionary[currentWordWithByteAdded] != null) {
                currentWord = currentWordWithByteAdded.toMutableList()
            } else {
                dictionary.dictionary.filterValues { it == currentCode.toInt() }.keys.first().forEach { decompressedBytes.add(it) }
                dictionary.dictionary[currentWordWithByteAdded] = dictionary.dictionary.size
                currentWord = mutableListOf(currentCode)
            }
        }

        File("decompressed$filename").writeBytes(decompressedBytes.toByteArray())
    }
}