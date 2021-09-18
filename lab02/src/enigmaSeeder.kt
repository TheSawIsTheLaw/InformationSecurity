@OptIn(ExperimentalStdlibApi::class) fun main()
{
    print(Char(33))
    val rot = Rotor()
    for (i in rot.tape.indices)
    {
        rot.tape[i] = i.toChar().toString()
    }
    rot.printTape()
    println()
//
//    rot.rotateRight()
//    println("After right rotation")
//    rot.printTape()
//
//    rot.rotateLeft()
//    println("After left rotation")
//    rot.printTape()
}