import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent


fun main()
{
    var frame = Frame()

    val button = Button("WOWOOWOWOWWOOW!!")
    button.setBounds(30, 100, 80, 30)
    frame.add(button)
    frame.setSize(300, 300)
    frame.title = "Lol, it works"
    frame.layout = null
    frame.isVisible = true

    frame.addWindowListener(object : WindowAdapter()
    {
        override fun windowClosing(we: WindowEvent)
        {
            frame.dispose()
        }
    }
    )
}