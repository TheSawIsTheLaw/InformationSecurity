import java.io.BufferedReader
import java.io.InputStreamReader

fun getLinuxMotherBoardSerialNumber(): String
{
    val command = "dmidecode -s baseboard-serial-number"
    var serialNumber = ""

    try
    {
        val SerialNumberProcess = Runtime.getRuntime().exec(command)

        val ISR = InputStreamReader(
            SerialNumberProcess.inputStream
        )
        val br = BufferedReader(ISR)
        serialNumber = br.readLine().trim { it <= ' ' }
        SerialNumberProcess.waitFor()
        br.close()
    } catch (e: Exception)
    {
    }

    return serialNumber
}