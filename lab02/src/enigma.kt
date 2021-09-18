const val sizeOfRotorsTape = 1103 // Так как у нас тут Unicode, то будем тыркаться с лентами побольше)))
// Есть добавить возможность кодировать сообщения на греческом!

class Rotor
{
    var tape = Array(sizeOfRotorsTape) { _ -> "" }

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

