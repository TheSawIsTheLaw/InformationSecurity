import fileOperations.createBinary
import fileOperations.getBinaryFilling

fun main(args: Array<String>)
{
    val out = getBinaryFilling("test.txt")
    createBinary("testOut.txt", out)
    println(out)

    if (args.size < 2)
    {
        println("Arguments:\n")
        println("1. Name of file;")
        println("2. Mode [c, dec].")
    }


}