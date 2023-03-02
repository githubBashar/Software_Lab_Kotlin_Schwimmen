package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * [MenuScene] ist ein Button auf der Game-szene.
 * es gibt 3 Buttons drin, eins für continuing com Spiel, eins für das Beenden der Applikation
 * und eins für das Anzeigen von home-scene
 */
class OverlayMenuScene : MenuScene(900,900,
    background = ColorVisual(Color.WHITE)) {
    val exitButton: Button = Button(
        height = 150,
        width = 300,
        posX = 300,
        posY = 320,
        text = "Exit application",
        font = Font(32,color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC)
    )

    val continueGameButton: Button = Button(
        height = 150,
        width = 300,
        posX = 300,
        posY = 490,
        text = "Continue",
        font = Font(32,color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC)
    )

    val homeButton = Button(
        height = 150,
        width = 300,
        posX = 300,
        posY = 660,
        text = "Go to home menu",
        font = Font(30,color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC)
    )

    private val menuLabel: Label = Label(
        height = 100,
        width = 200,
        posX = 350,
        posY = 200,
        text = "Game menu",
        font = Font(32,fontWeight = Font.FontWeight.BOLD)
    )

    init {
        background = ImageVisual("playground.png")
        addComponents(
            menuLabel, exitButton, continueGameButton, homeButton
        )
    }
}