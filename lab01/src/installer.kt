import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

// kotlinc anecdotes.kt linuxChecker.kt -include-runtime -d anecdotes.jar

fun String.runCommand(workingDir: File): String
{
    try
    {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    } catch (e: IOException)
    {
        println("Cannot use the command.")
        return ""
    }
}

fun prepareFileForCompilation() : Boolean
{
    var isSuccess = false

    val serialNumberToAdd = getLinuxMotherBoardSerialNumber()
    if (serialNumberToAdd == "")
    {
        println("Please, start installer using sudo.")
        return isSuccess
    }

    val licenseFile = File("anecdotes.kt")
    if (licenseFile.exists())
    {
        licenseFile.appendText("\nconst val licenseKey = \"$serialNumberToAdd\"")
        isSuccess = true
    }

    return isSuccess
}

fun compileFile() : Boolean
{
    var isSuccess = true

    "kotlinc anecdotes.kt linuxChecker.kt -include-runtime -d anecdotes.jar".runCommand(File("").absoluteFile)

    return isSuccess
}

fun main()
{
    if (prepareFileForCompilation() && compileFile())
        println("Successfully installed!")
    else
        println("Installation failed :(")
}