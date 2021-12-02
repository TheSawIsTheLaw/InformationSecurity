package lzw

class DictionaryLZW {
    val dictionary: HashMap<ByteArray, Int> = hashMapOf()
    val inverseDictionary: HashMap<Int, ByteArray> = hashMapOf()

    fun initDictionary() {
        dictionary.clear()

        // So that I'll use byte as read value from file - I replace string to ByteArray because of Char = 16 bit
        for (currentByte in 0..Byte.MAX_VALUE) {
            dictionary[byteArrayOf(currentByte.toByte())] = currentByte
            inverseDictionary[currentByte] = byteArrayOf(currentByte.toByte())
        }
    }
}