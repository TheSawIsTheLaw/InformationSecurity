import java.io.File
import java.security.*
import java.security.spec.X509EncodedKeySpec

const val privateKeyFilename = "privatekey.json"
const val publicKeyFilename = "publickey.json"

fun generateKeys() {
    val generator = KeyPairGenerator.getInstance("RSA")
    val keys: KeyPair = generator.generateKeyPair()

    keys.public.encoded.forEach { print(it) }
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = X509EncodedKeySpec(keys.public.encoded)
    println()
    keyFactory.generatePublic(keySpec).encoded.forEach { print(it) }

}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println(
            "Required arguments:\n" +
                    "1. Mode [keygen, sign, verify];" +
                    "2. File name (not used in key generation)"
        )
    }

    val mode = args[0]
    if (mode !in listOf("keygen", "sign", "verify")) {
        println("Invalid mode, check required arguments.")

        return
    }

    when (mode) {
        "keygen" -> generateKeys()
        "sign" -> TODO()
        "verify" -> TODO()
    }
}