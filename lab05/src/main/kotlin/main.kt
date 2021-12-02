import java.io.File
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

const val privateKeyFilename = "privatekey"
const val publicKeyFilename = "publickey"
const val signatureFilename = "signature"

object KeyHelper {
    private val keyFactory by lazy { KeyFactory.getInstance("RSA") }

    fun getPublicKey(encodedPublic: ByteArray): PublicKey {
        val keySpec = X509EncodedKeySpec(encodedPublic)
        return keyFactory.generatePublic(keySpec)
    }

    fun getPrivateKey(encodedPrivate: ByteArray): PrivateKey {
        val keySpec = PKCS8EncodedKeySpec(encodedPrivate)
        return keyFactory.generatePrivate(keySpec)
    }
}

fun getEncodedKey(keyFile: File): ByteArray {
    return keyFile.readBytes()
}

fun getPublicKey(): PublicKey {
    val encodedPublic = getEncodedKey(File(publicKeyFilename))
    return KeyHelper.getPublicKey(encodedPublic)
}

fun getPrivateKey(): PrivateKey {
    val encodedPrivate = getEncodedKey(File(privateKeyFilename))
    return KeyHelper.getPrivateKey(encodedPrivate)
}

fun generateKeys() {
    val generator = KeyPairGenerator.getInstance("RSA")
    val keys: KeyPair = generator.generateKeyPair()

//    keys.public.encoded.forEach { print(it) }
//    println()
//    KeyHelper.getPublicKey(keys.public.encoded).encoded.forEach { print(it) }

    File(publicKeyFilename).writeBytes(keys.public.encoded)
    File(privateKeyFilename).writeBytes(keys.private.encoded)
}

fun signFile(filename: String) {
    val signature = Signature.getInstance("SHA256withRSA")
    signature.initSign(getPrivateKey())
    signature.update(File(filename).readBytes())
    File(signatureFilename).writeBytes(signature.sign())
}

fun verifyDigitalSignature(filename: String) {
    val signature = Signature.getInstance("SHA256withRSA")
    signature.initVerify(getPublicKey())
    signature.update(File(filename).readBytes())

    if (signature.verify(File(signatureFilename).readBytes()))
        println("It's ok")
    else
        println("File corrupted, don't open it o.o")
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

    if (mode != "keygen" && args.size < 2) {
        println("File name to be signed or verified is needed.")

        return
    }

    when (mode) {
        "keygen" -> generateKeys()
        "sign" -> signFile(args[1])
        "verify" -> verifyDigitalSignature(args[1])
    }
}