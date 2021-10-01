import java.lang.Exception
import kotlin.random.Random

class Reflector(randomizer: Random)
{
    var reflectivePairs = Array(sizeOfRotorsTape / 2) { _ -> Pair(' ', ' ') }

    init
    {
        val listOfLetters = MutableList(sizeOfRotorsTape) { index: Int -> Char(index) }
        listOfLetters.shuffle(randomizer)

        for (i in listOfLetters.indices step 2)
        {
            reflectivePairs[i / 2] = Pair(listOfLetters[i], listOfLetters[i + 1])
        }
    }

    fun printPairs()
    {
        reflectivePairs.forEach { print("$it ") }
    }
}

var sizeOfRotorsTape = 20000 //1104 // Так как у нас тут Unicode, то будем тыркаться с лентами побольше)))
// Есть добавить возможность кодировать сообщения на греческом!

class Rotor(randomizer: Random)
{
    var tape = Array(sizeOfRotorsTape) { index -> index.toChar() }

    init
    {
        tape.shuffle(randomizer)
    }

    fun rotateRight()
    {
        val temp = tape.last()
        for (i in (tape.size - 1) downTo 1)
        {
            tape[i] = tape[i - 1]
        }
        tape[0] = temp
    }

    private fun rotateLeft()
    {
        val temp = tape.first()
        for (i in 0 until tape.size - 1)
        {
            tape[i] = tape[i + 1]
        }
        tape[tape.lastIndex] = temp
    }

    fun printTape()
    {
        tape.forEach { print("$it ") }
        println()
    }
}

class EnigmaMachine(randomizer: Random)
{
    val numOfRotors = 3
    val numOfRotatesForOneRotor = sizeOfRotorsTape

    private val listOfRotors = List(numOfRotors) { _ -> Rotor(randomizer) }
    private val reflector = Reflector(randomizer)

    // Для вращения роторов
    private var rotatableRotorIndex = 0
    private var currentRotates = 0

    fun printRotors()
    {
        for (i in listOfRotors.indices)
        {
            print("Rotor №$i: ")
            listOfRotors[i].printTape()
        }
    }

    fun printReflector()
    {
        print("Reflector: ")
        reflector.printPairs()
    }

    fun printMachineProperties()
    {
        printRotors()
        printReflector()
    }

    fun rotateRotor()
    {
        if (currentRotates < numOfRotatesForOneRotor)
        {
            currentRotates++
        }
        else
        {
            currentRotates = 1
            if (rotatableRotorIndex < numOfRotatesForOneRotor)
            {
                rotatableRotorIndex++
            }
            else
            {
                rotatableRotorIndex = 0
            }
        }

        listOfRotors[rotatableRotorIndex].rotateRight()
    }

    @Throws(Exception::class)
    fun encryptOrDecrypt(str: String): String
    {
        var outString = ""

        var wayLetter: Char
        for (letter in str)
        {
            // Прямой ход по роторам
            wayLetter = letter
            for (rotorIndex in listOfRotors.indices)
            {
                wayLetter = listOfRotors[rotorIndex].tape[wayLetter.code]
            }

            // Поиск рефлективной пары для замены
            var currentReflectorIndex = 1
            var checkPair = reflector.reflectivePairs[0]
            while (
                wayLetter != checkPair.first &&
                wayLetter != checkPair.second &&
                currentReflectorIndex < reflector.reflectivePairs.size
            )
            {
                checkPair = reflector.reflectivePairs[currentReflectorIndex]
                currentReflectorIndex++
            }

            // Определение замены на рефлекторе
            wayLetter = when
            {
                currentReflectorIndex == reflector.reflectivePairs.size -> throw Exception("Passed unsupported symbol.")
                wayLetter == checkPair.first                            -> checkPair.second
                else                                                    -> checkPair.first
            }

            // Обратный ход по роторам
            for (rotorIndex in numOfRotors - 1 downTo 0)
            {
                wayLetter = listOfRotors[rotorIndex].tape.indexOf(wayLetter).toChar()
            }

            outString += wayLetter

            // Вращение нужного ротора
            rotateRotor()
        }

        return outString
    }
}

const val numOfUsedUnicodeSymbols = 5000

fun main(args: Array<String>)
{
//    if (args.size != 3 || args[0].toIntOrNull() == null || args[1].toIntOrNull() == null || args[1].toInt() > 143858)
//    {
//        println(
//            "\u001B[35mNot enough arguments. There is a need for 2 arguments: " +
//                    "seed (Int), number of unicode symbols to use (Int, <= 143 858), message to encrypt/decrypt\u001B[0m"
//        )
//
//        return
//    }

    val seed = 133// args[0].toInt()
//    val numOfUsedUnicodeSymbols = args[1].toInt()
    val toEndecrypt = "\u0C04֮ఆఇ̅ݷຝ\u0C3BݺৼቐݏݐҌһᄠ༤ރזေ͘\u008Fثᇵᇶ௺"// args[2]

    if (numOfUsedUnicodeSymbols % 2 != 0)
    {
        throw Exception("Number of used unicode symbols is odd. But it should be even for the reflector.")
    }
    sizeOfRotorsTape = numOfUsedUnicodeSymbols

    val enigma = EnigmaMachine(Random(seed))

    var endecrypted = ""
    try
    {
        endecrypted = enigma.encryptOrDecrypt(toEndecrypt)
    } catch (e: Exception)
    {
        println()
        println("\u001B[35m Some symbols are not supported. \u001B[0m")
    }

    println()
    println("\u001B[32mResult:")
    println(endecrypted)
    print("\u001B[0m")
}