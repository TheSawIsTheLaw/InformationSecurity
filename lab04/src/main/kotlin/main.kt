import fileOperations.createBinary
import fileOperations.createBinaryDeciphered
import fileOperations.getBinaryFilling
import fileOperations.getBinaryFillingCiphered
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

//    println("Ok, let's try it in prod")
//    val listToTest = listOf(108uL, 111uL, 108uL, 10uL)
//    println(listToTest)
//    val shit = rsaMachine.encrypt(listToTest)
//    println("Ciphered = $shit")
//    val decShit = rsaMachine.decrypt(shit)
//    println("Deciphered = $decShit")

    val functionToGetBinaryFilling: KFunction<List<ULong>>
    val functionToUseDecOrC: KFunction<List<ULong>>
    val extendedName: String
    val functionToCreateBinary: KFunction<Boolean>

    if (mode == "dec") {
        functionToGetBinaryFilling = ::getBinaryFillingCiphered
        functionToUseDecOrC = rsaMachine::decrypt
        extendedName = "Deciphered."
        functionToCreateBinary = ::createBinaryDeciphered
    } else {
        functionToGetBinaryFilling = ::getBinaryFilling
        functionToUseDecOrC = rsaMachine::encrypt
        extendedName = "Ciphered."
        functionToCreateBinary = ::createBinary
    }

    val filling = functionToGetBinaryFilling(fileName)
    val needed = functionToUseDecOrC(filling)
    functionToCreateBinary(pureName + extendedName + extension, needed)
}