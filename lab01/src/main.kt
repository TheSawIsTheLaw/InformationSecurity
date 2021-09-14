import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.random.Random

val anecs = listOf(
    "anec1",
    "anec2"
)

fun changeCurrentAnec(label: Label)
{
    label.text = anecs[Random.nextInt(0, anecs.size)]
}

fun createAndStartMainWindow()
{
    val button = Button("Получить анекдот")
    button.setBounds(0, 100, 400, 30)

    val frame = Frame()
    frame.add(button)
    frame.setSize(400, 300)
    frame.title = "Анекдоты.ру, но не совсем .ру"
    frame.layout = null
    frame.isVisible = true

    val label = Label("Тут могла бы быть Ваша реклама")
    label.setBounds(0, 200, 400, 100)
    label.alignment = Label.CENTER
    frame.add(label)

    button.addActionListener { changeCurrentAnec(label) }

    frame.addWindowListener(object : WindowAdapter()
    {
        override fun windowClosing(we: WindowEvent)
        {
            frame.dispose()
        }
    })
}

fun main()
{
    createAndStartMainWindow()
}