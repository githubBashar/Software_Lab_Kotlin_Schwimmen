package view
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * [MenuScene], das zum Starten eines neuen Spiels verwendet wird. Sie wird direkt bei Programmstart angezeigt oder erreicht,
 * wenn in [EndScene] auf "new game" geklickt wird oder in [GameScene] auf „go to home menu“ geklickt wird.
 * Nachdem man die Namen der Spieler angegeben hat, kann [startButton] gedrückt werden. Es gibt auch einen [quitButton]
 * um das Programm zu beenden.
 *
 * @param swimApplication Verweis auf Schwimmspieldienst
 */

class HomeScene(private val swimApplication: SwimApplication) : MenuScene(1160, 900),
    Refreshable {
    private val text = Label(
        width = 700, height = 100,
        posX = 230, posY = 60,
        text = "Swim game",
        font = Font(size = 60, color = Color(210,105,30),family = "Papyrus")

    )
     private val p1Label = Label(
        width = 200, height = 100,
        posX = 500, posY = 190,
        text = "Player 1:",
        font = Font(size = 30 , Color(210,105,30),family = "Papyrus")
    )

     val p1Input: TextField = TextField(
        width = 200, height = 60,
        posX = 500, posY = 270,
        font = Font(size = 30, Color(210,105,30))
    ).apply {
        onKeyTyped = {
            p2Input.isDisabled = this.text.isBlank()
            p3Input.isDisabled = true
            p4Input.isDisabled = true
        }
    }

     private val p2Label = Label(
        width = 200, height = 100,
        posX = 500, posY = 300,
        text = "Player 2:",
        font = Font(size = 30 , Color(210,105,30),family = "Papyrus")
    )


     val p2Input: TextField = TextField(
        width = 200, height = 60,
        posX = 500, posY = 380,
         font = Font(size = 30, Color(210,105,30))
    ).apply {
         this.isDisabled = p1Input.text.isBlank()
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank()
            p3Input.isDisabled = this.text.isBlank()
        }
    }


     private val p3Label = Label(
        width = 200, height = 100,
        posX = 500, posY = 410,
        text = "Player 3:",
        font = Font(size = 30 , Color(210,105,30),family = "Papyrus")
    )


     val p3Input: TextField = TextField(
        width = 200, height = 60,
        posX = 500, posY =490,
         font = Font(size = 30, Color(210,105,30)),  prompt = "(optional)"
    ).apply {
         this.isDisabled = p2Input.text.isBlank()
         onKeyTyped = {
             p4Input.isDisabled =this.text.isBlank()
        }
    }

     private val p4Label = Label(
        width = 200, height = 100,
        posX = 500, posY = 520,
        text = "Player 4:",
        font = Font(size = 30 , Color(210,105,30),family = "Papyrus")
    )
     val p4Input: TextField = TextField(
        width = 200, height = 60,
        posX = 500, posY = 600,
         font = Font(size = 30, Color(210,105,30)), prompt = "(optional)"
    ).apply {
         this.isDisabled = p3Input.text.isBlank()
     }


     private val startButton = Button(
        width = 200, height = 100,
        posX = 300, posY = 700,
        text = "Start",
        font = Font(size = 40 , color = Color.BLACK,family = "Papyrus"),

        ).apply {
         this.isDisabled = p2Input.text.isBlank()
        visual = ColorVisual(0,100,0)
        onMouseClicked = {
            val playerNames = mutableListOf<String>()
            playerNames.clear()
            if (p1Input.text.isNotBlank()) {
                playerNames.add(p1Input.text.trim())
            }
            if (p2Input.text.isNotBlank()) {
                playerNames.add(p2Input.text.trim())
            }
            if (p3Input.text.isNotBlank()&& !p3Input.isDisabled) {
                playerNames.add(p3Input.text.trim())
            }
            if (p4Input.text.isNotBlank()&& !p4Input.isDisabled) {
                playerNames.add(p4Input.text.trim())
            }
            if (playerNames.size < 2) {
                this.isDisabled = true
                throw IllegalArgumentException("Der Button wird aktiviert erst, wenn es mindestens zwei Spieler gibt")
            }
            swimApplication.rootService.gameService.startGame(playerNames.toList())
        }
    }

     val quitButton= Button (
        width = 200, height = 100,
        posX = 700, posY = 700,
        text = "Quit",
        font = Font(size = 40 , color = Color.BLACK,family = "Papyrus"),
    ).apply {
        visual = ColorVisual(139,0,0)
        onMousePressed = {
            swimApplication.exit()
        }
    }
    init {
        apply { background = ImageVisual("cards.jpg") }
        addComponents(
            text,
            p1Label,
            p1Input,
            p2Label,
            p2Input,
            p3Label,
            p3Input,
            p4Label,
            p4Input,
            startButton,
            quitButton
        )
    }
}

