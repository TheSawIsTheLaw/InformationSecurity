import fileOperations.createBinary
import fileOperations.getBinaryFilling
import rsa.RSA
import kotlin.reflect.KFunction

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Arguments:\n")
        println("1. Name of file;")
        println("2. Mode [c, dec] (default = c).")

        return
    }

    val fileName = args[0]
    val extension = fileName.substringAfter(".")
    val pureName = fileName.substringBefore(".")
    val mode = args[1]

    val rsaMachine = RSA()
    val filling = getBinaryFilling(fileName)

    println("Ok, let's try it in prod")
    val listToTest = listOf(66666uL)
    println(listToTest)
    val shit = rsaMachine.encrypt(listToTest)
    println("Ciphered = $shit")
    val decShit = rsaMachine.decrypt(shit)
    println("Deciphered = $decShit")

    val functionToUse: KFunction<List<ULong>>
    val extendedName: String

//    if (mode == "dec") {
//        functionToUse = rsaMachine::decrypt
//        extendedName = "Deciphered."
//    } else {
//        functionToUse = rsaMachine::encrypt
//        extendedName = "Ciphered."
//    }
//
//    val needed = functionToUse(filling)
//    createBinary(pureName + extendedName + extension, needed)
}