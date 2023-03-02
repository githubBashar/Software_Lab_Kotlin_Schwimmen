package view
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * @constructor board game scene component
 */
class HelloScene : BoardGameScene(500, 500), Refreshable {

    private val helloLabel = Label(
        width = 500,
        height = 500,
        posX = 0,
        posY = 0,
        text = "Hello, SoPra!",
        font = Font(size = 20)
    )

    init {
        background = ColorVisual(108, 168, 59)
        addComponents(helloLabel)
    }

    /**
     * aktualisiert nach Start-Game
     */
    override fun refreshAfterStartGame(){
        //kommt noch
    }

    /**
     * aktualisiert nach Spielende und gibt die Spieler sortiert nach Punktanzahl aus
     */
    override fun refreshAfterEndGame() {
        //kommt noch
    }

    /**
     * aktualisiert nach Kartenaustausch
     */
    override fun refreshAfterCardsChanged(){
        //kommt noch
    }

        //kommt noch
}