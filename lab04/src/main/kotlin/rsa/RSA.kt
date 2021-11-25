package rsa

import java.math.BigInteger
import kotlin.random.*

class RSA(private val pNumber: ULong = 17uL, private val qNumber: ULong = 19uL) {
    private val nModule = pNumber * qNumber
    private val phiEuler = (pNumber - 1uL) * (qNumber - 1uL)

    private val random = Random(1337L)

    val publicKey = Pair(generatePublicKeyENumber(), nModule)
    val privateKey = Pair(generatePrivateKeyDNumber(), nModule)

    init {
        println("${privateKey.first} * ${publicKey.first} mod $phiEuler == 1")
        println("Formed keys: $publicKey (public), $privateKey (private)")
    }

    private fun gcd(fNum_: ULong, sNum_: ULong): ULong {
        var fNum = fNum_
        var sNum = sNum_
        while (fNum != sNum) {
            if (fNum > sNum)
                fNum -= sNum
            else
                sNum -= fNum
        }
        println("For $fNum_ and $sNum_ GCD is $fNum")

        return fNum
    }

    private fun generatePublicKeyENumber(): ULong {
        // GCD - the greatest common divisor
        var currentGCD = 0uL
        var returnValue = 0uL

        while (currentGCD != 1uL) {
            returnValue = random.nextULong(ULongRange(2uL, phiEuler))
            currentGCD = gcd(returnValue, phiEuler)
        }

        return returnValue
    }

    // Returns number n from (n * fNumber) mod sNumber == 1
    private fun inverseByExtendedEuclideanAlgorithm(fNumber: ULong, sNumber: ULong): ULong {
        var outT = 0L
        var currentR = sNumber.toLong()

        var newT = 1L
        var newR = fNumber.toLong()

        var quotient: Long
        var temp: Long
        while (newR != 0L) {
            quotient = currentR / newR

            temp = newT
            newT = outT - quotient * newT
            outT = temp

            temp = newR
            newR = currentR - quotient * newR
            currentR = temp
        }

        if (currentR > 1L)
            throw Exception("No such inverse value")

        if (outT < 0)
            outT += sNumber.toLong()

        return outT.toULong()

//        Варварский метод
//        var cont = true
//        var number = 1uL
//        while (cont)
//        {
//            if ((number * fNumber).mod(sNumber) == 1uL)
//                cont = false
//            else
//                number++
//        }
//
//        println(number)
//        println("Public: $fNumber")
//        println("Phi: $sNumber")
//        return number
    }

    private fun generatePrivateKeyDNumber(): ULong {
        return inverseByExtendedEuclideanAlgorithm(publicKey.first, phiEuler)
    }

    private fun powULong(num: Long, degree: ULong): BigInteger {
        var outValue: BigInteger = BigInteger.valueOf(1)

        for (i in 0uL until degree) {
            outValue *= BigInteger.valueOf(num)
//            println(outValue)
        }

        return outValue
    }

    // Ловим переполнение, надо исправлять размеры блоков
    // Наверное, переделываем на шорт блоки, а работаем дальше с лонгами.
    private fun encryptULong(numberToEncrypt: ULong): ULong =
        (powULong(
            numberToEncrypt.toLong(),
            publicKey.first
        ).mod(BigInteger.valueOf(publicKey.second.toLong()))).toLong().toULong()

    private fun decryptULong(numberToDecrypt: ULong): ULong =
        (powULong(
            numberToDecrypt.toLong(),
            privateKey.first
        ).mod(BigInteger.valueOf(privateKey.second.toLong()))).toLong().toULong()

    fun encrypt(listToEncrypt: List<ULong>): List<ULong> {
        val outList = mutableListOf<ULong>()

        listToEncrypt.forEach {
            outList.add(encryptULong(it))
        }

        return outList
    }

    fun decrypt(listToDecrypt: List<ULong>): List<ULong> {
        val outList = mutableListOf<ULong>()

        listToDecrypt.forEach {
            outList.add(decryptULong(it))
        }

        return outList
    }
}