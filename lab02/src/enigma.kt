import kotlin.random.Random

const val sizeOfRotorsTape = 1071 // Так как у нас тут Unicode, то будем тыркаться с лентами побольше)))
// Есть добавить возможность кодировать сообщения на греческом!

class Rotor
{
    var tape = Array(sizeOfRotorsTape) {index -> (index + 33).toChar()}

    fun rotateRight()
    {
        val temp = tape.last()
        for (i in (tape.size - 1) downTo 1)
        {
            tape[i] = tape[i - 1]
        }
        tape[0] = temp
    }

    fun rotateLeft()
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
    }
}

const val numOfRotors = 4

class EnigmaMachine()
{
    val listOfRotors = List(numOfRotors) { _ -> Rotor()}

    fun printRotors()
    {
        listOfRotors.forEach { it.printTape() }
    }

    fun randomAllRotors(seed: Int)
    {
        val randomGenerator = Random(seed)

        for (i in listOfRotors.indices)
        {
            listOfRotors[i].tape.shuffle(randomGenerator)
        }
    }
}

fun main()
{
    print("lox")
}