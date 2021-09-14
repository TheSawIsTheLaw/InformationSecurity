import javax.swing.*
import kotlin.random.Random
val anecs = listOf(
    "<html>Штирлиц играл в карты и проигрался.<br>Но Штирлиц умел делать хорошую мину при плохой игре.<br>Когда Штирлиц покинул компанию, мина сработала.</html>",
    "anec2"
)

fun changeCurrentAnec(label: JLabel)
{
    label.text = anecs[Random.nextInt(0, anecs.size)]
    label.size = label.preferredSize
}

fun createAndStartMainWindow()
{
    val button = JButton("Получить анекдот")
    button.setBounds(0, 100, 400, 30)

    val label = JLabel("Тут могла бы быть Ваша реклама")
    label.setBounds(0, 200, 400, 100)
    label.horizontalAlignment = JLabel.CENTER

    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.LINE_AXIS)
    panel.add(Box.createHorizontalBox())
    panel.add(button)
    panel.add(Box.createHorizontalBox())
    panel.add(label)

    val frame = JFrame()
    frame.setSize(400, 300)
    frame.title = "Анекдоты.ру, но не совсем .ру"
    frame.isVisible = true
    frame.add(panel)

    button.addActionListener { changeCurrentAnec(label) }
}

fun main()
{
    createAndStartMainWindow()
}