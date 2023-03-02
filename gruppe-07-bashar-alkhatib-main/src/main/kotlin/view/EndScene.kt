package view
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
/**
 * [MenuScene], das angezeigt wird, wenn das Spiel beendet ist. Es zeigt das sortierte Endergebnis aller Spieler,
 * außerdem gibt es drei Schaltflächen: eine zum Starten eines neuen Spiels, eine zum erneuten Spielen des aktuellen Spiels
 * und eine zum Beenden des Programms.
 */
class EndScene :
    MenuScene(900,900 ),Refreshable {

    private val result  = Label(
        posX = 280, posY = 20,
        width = 350, height = 120,
        visual = ColorVisual(139,69,19),text = "End of the game\nHere the results: ", font = Font(size =40)
    )

    val sameResult = Label(width = 920, height = 400,
        posX = 0, posY = 100,
        text= "", font = Font(30, Color.BLACK))

    //label that shows a winner if he/she exists
    val gameResult1 = Label(width = 800, height = 400,
        posX = 20, posY = 100,
        text= "", font = Font(30, Color.BLACK))

    val gameResult2 = Label(width = 500, height = 400,
        posX = 300, posY = 230,
        text= "", font = Font(30, Color.BLACK))

    val gameResult3 = Label(width = 500, height = 400,
        posX = 90, posY = 370,
        text= "", font = Font(30, Color.BLACK))

    val gameResult4 = Label(width = 500, height = 100,
        posX = 210, posY = 650,
        text= "", font = Font(30, Color.BLACK))


    val newGameButton = Button(width = 200, height = 100,
        posX = 100, posY = 750, text = "New Game",
        font = Font(24)
    ).apply {
        visual = ColorVisual(139,69,19)
    }

    val quitButton = Button(width = 200, height = 100, posX = 350,
        posY = 750, text = "Quit",font = Font(24)
    ).apply {
        visual = ColorVisual(89,0,0)
    }

    val playAgainButton = Button(
        width = 200, height = 100,
        posX = 600,posY = 750,
        text = "Play again", font = Font(24)
    ).apply {
        visual = ColorVisual(139,69,19)
    }

    init {
        addComponents(sameResult,newGameButton,quitButton,playAgainButton,
            gameResult1,gameResult2,gameResult3,gameResult4, result)
        background = ImageVisual("podest.jpg")
    }

}