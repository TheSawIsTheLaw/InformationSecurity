import java.awt.Font
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import kotlin.random.Random
import kotlin.system.exitProcess

val anecs = listOf(
    "<html>Штирлиц играл в карты и проигрался.<br>Но Штирлиц умел делать хорошую мину при плохой игре.<br>Когда Штирлиц покинул компанию, мина сработала.</html>",
    "<html>Идет Штирлиц ночью по городу, навстречу мужик бородатый и в чалме.<br>- Будь он не Ладен - подумал Штирлиц.</html>",
    "<html>Штирлиц и Мюллер ездили по очереди на танке.<br>Очередь редела, но не расходилась...</html>",
    "<html>Штирлиц стрелял вслепую.<br>Слепая испугалась и побежала скачками,<br>но качки быстро отстали.</html>",
    "<html>Штирлиц шёл по улице, когда внезапно перед ним что-то упало.<br>Штирлиц поднял глаза - это были глаза профессора Плейшнера.</html>",
    "<html>Штирлиц всю ночь топил камин. На утро камин утонул.</html>",
    "<html>Письмо из центра до Штиpлица не дошло... Пришлось читать во второй раз.</html>",
    "<html>Штирлиц долго смотрел в одну точку. Потом в другую.<br>\"Двоеточие!\" - наконец-то смекнул Штирлиц.</html>"
)

fun changeCurrentAnec(label: JLabel)
{
    label.text = anecs[Random.nextInt(0, anecs.size)]
}

fun createAndStartMainWindow()
{
    val fontForAll = Font("", 0, 14)

    val button = JButton("Получить анекдот")
    button.setBounds(0, 100, 400, 30)
    button.font = fontForAll

    val label = JLabel("Тут могла бы быть Ваша реклама")
    label.size = label.preferredSize
    label.font = fontForAll
    label.horizontalAlignment = JLabel.CENTER
    label.verticalAlignment = JLabel.CENTER

    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.LINE_AXIS)
    panel.add(Box.createVerticalBox())
    panel.add(button)
    panel.add(label)

    val frame = JFrame()
    frame.setSize(400, 300)
    frame.title = "Анекдоты.ру, но не совсем .ру"
    frame.isVisible = true
    frame.add(panel)
    button.addActionListener {
        changeCurrentAnec(label)
    }


    frame.addWindowListener(object : WindowAdapter()
    {
        override fun windowClosing(e: WindowEvent)
        {
            exitProcess(0)
        }
    })

}

fun isLicenseVerified(): Boolean
{
    var answ = false

    val gotSerial = getLinuxMotherBoardSerialNumber()
    if (gotSerial == "")
    {
        println("Please, start program using sudo.")
    }
    else if (licenseKey == "")
    {
        println("Please, activate the program.")
    }
    else if (licenseKey != gotSerial)
    {
        println("You've stole the program. Now i'm going to starve to death :(")
    }
    else
    {
        answ = true
    }

    return answ
}

fun main()
{
    if (isLicenseVerified())
    {
        createAndStartMainWindow()
    }
}