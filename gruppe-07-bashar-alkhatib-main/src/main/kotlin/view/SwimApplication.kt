package view

import service.*
import tools.aqua.bgw.core.BoardGameApplication
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * Implementierung der BGW [BoardGameApplication] für das Kartenspiel „Swim game“.
 * @property rootService Zentraler Dienst, von dem aus alle anderen erstellt/aufgerufen werden
 *  und hält auch das aktuelle aktive Spiel
 * @property gameScene Hier findet das eigentliche Spiel statt und wenn in [endScene auf „play again“ geklickt wird
 * @property homeScene Diese Menüszene wird angezeigt, nachdem die Anwendung gestartet wurde und wenn
 * das "new game" Button wird in EndScene angeklickt wurde
 * @property overlayMenuScene die auf der  [gameScene]] angezeigt wird, um ein Spiel zu beenden, fortzusetzen oder
 * neu zu starten
 * @property endScene Diese Menüszene wird nach jedem beendeten Spiel angezeigt
 * (d.h. wenn keine Karten mehr zu ziehen sind oder
 * ein Spieler geklopft hat)
*/


class SwimApplication : BoardGameApplication("Swim game"), Refreshable{
    val rootService = RootService()
    private var gameScene = GameScene(this).apply {
        menuButton.onMouseClicked = {this@SwimApplication.showMenuScene(overlayMenuScene)
       }
        helpButton.onMouseClicked = {this@SwimApplication.showMenuScene(helpScene)}
    }

    private val homeScene = HomeScene(this).apply {
        quitButton.onMouseClicked={
            exit()
        }
    }
    private val overlayMenuScene = OverlayMenuScene().apply {
        exitButton.onMouseClicked = {exit()}
        continueGameButton.onMouseClicked = {this@SwimApplication.hideMenuScene()}
        homeButton.onMouseClicked = {this@SwimApplication.showMenuScene(homeScene)}
    }

    private val helpScene = HelpScene().apply {
        continueGameButton.onMouseClicked = {this@SwimApplication.hideMenuScene()}
    }



    val endScene = EndScene().apply {
        quitButton.onMouseClicked ={exit()}
        newGameButton.onMouseClicked = {
            this.gameResult1.text = ""
            this.gameResult2.text = ""
            this.gameResult3.text = ""
            this.gameResult4.text = ""
            this@SwimApplication.hideMenuScene()
            this@SwimApplication.showMenuScene(homeScene)

            gameScene.klopfenButton.apply {
                font = Font(50, color = Color(139, 69, 19))
                visual = ColorVisual.BLACK
            }
        }
        playAgainButton.onMouseClicked = {
            this.gameResult1.text = ""
            this.gameResult2.text = ""
            this.gameResult3.text = ""
            this.gameResult4.text = ""
            val playerList = mutableListOf<String>()
            val swimGame = rootService.currentGame
            checkNotNull(swimGame)
            swimGame.players.forEach {
                playerList.add(it.name)
            }
            hideMenuScene()
            rootService.gameService.startGame(playerList)
            gameScene.klopfenButton.apply {
                font = Font(50, color = Color(139, 69, 19))
                visual = ColorVisual.BLACK
            }
        }
    }
    init {
        rootService.addRefreshables(this)
        rootService.addRefreshables(gameScene)
        rootService.addRefreshables(endScene)
        rootService.addRefreshables(homeScene)
        this.showMenuScene(homeScene,0)
    }

    /**
     * Zeigt die Hauptspielszene, nachdem das Spiel erstellt wurde, und leert die Eingabetexte,
     * sodass bei erneuter Anzeige einer main game kein Text in den Textfeldern vorhanden ist
     */
    override fun refreshAfterStartGame() {
        homeScene.p1Input.text=""
        homeScene.p2Input.text=""
        homeScene.p3Input.text=""
        homeScene.p4Input.text=""
        this.hideMenuScene()
        this.showGameScene(gameScene)
    }
    /**
     * zeigt die Endszene
     */
    override fun refreshAfterEndGame() {
        this.showMenuScene(endScene)
    }
}