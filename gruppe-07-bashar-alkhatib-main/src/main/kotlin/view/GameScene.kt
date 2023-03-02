package view
import entity.*
import service.*
import tools.aqua.bgw.animation.*
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.Orientation
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.*
import java.awt.*

/**
 * das ist die Spielszene wo das Spiel stattfindet. Es gibt die mittleren drei Karten in der Mitte
 * und unten werden die Karten des aktuellen Spielers angezeigt
 * die Szene verfügt über mehrere Buttons, die fürs Spiel benötigt werden.
 *
 * @param [swimApplication] aktuelle swim Applikation
 */

class GameScene( private val swimApplication: SwimApplication):BoardGameScene(3000, 2000), Refreshable  {
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()
    private val titel  = Label(
        posX = 1000, posY = 20,
        width = 1000, height = 160,
        visual = ColorVisual(139,69,19),text = "Game menu ", font = Font(size =100, family = "Papyrus")
    )

    private val passC= Label(
        posX = 30, posY = 80,
        width = 500, height = 90, visual = ColorVisual(139,69,19),
        font = Font(50, family = "Papyrus")
    )

    /**
     * selectedMidCard ist bei Index i wahr, wenn die mittlere Karte mit Index i
     * durch Austausch einer Karte ausgewählt wurde
     */
    private var selectedMidCard = mutableListOf(false, false, false)

    /**
     * selectedHandCard ist bei Index i wahr, wenn die Handkarte mit Index i
     * durch Tauschen einer Karte ausgewählt wurde
     */

    private var selectedHandCard = mutableListOf(false, false, false)

    /**
     * ist wahr, wenn die mittlere Karte zum Tauschen ausgewählt wurde
     */
    private val midCardSelected: Boolean get() = selectedMidCard.contains(true)

    /**
     * ist wahr, wenn die Handkarte zum Tauschen ausgewählt wurde
     */
    private val handCardSelected: Boolean get() = selectedHandCard.contains(true)

    //middle cards
    private val midCardsView  = LinearLayout<CardView>(
        posX = 1000, posY = 450,
        width = 1000, height = 480,
        spacing = 50, alignment = Alignment.CENTER,
        orientation = Orientation.HORIZONTAL,
    )
    private val midCardTitel = Label(
        posX = 1000, posY = 310,
        width = 1000, height = 130,visual = ColorVisual(139,69,19),
        text = "Middle cards", font = Font(60, color = Color(0,0,0), family = "Papyrus"))

    //buttons that allow user to choose which middle card is to be changed
    private val midCard1Button = Button(
        posX = 1070, posY = 950,
        width = 180, height = 50,
        text = "Card 1", visual = ColorVisual(139,69,19), font = Font(30)
    ).apply {
        isVisible = false
        onMouseClicked = {
            middleCardsButtonFunction(0)
        }
    }
    private val midCard2Button = Button(
        posX = 1420, posY = 950,
        width = 180, height = 50,
        text = "Card 2", visual = ColorVisual(139,69,19), font = Font(30)
    ).apply {
        isVisible = false
        onMouseClicked = {
            middleCardsButtonFunction(1)
        }
    }
    private val midCard3Button = Button(
        posX = 1770, posY = 950,
        width = 180, height = 50,
        text = "Card 3", visual = ColorVisual(139,69,19), font = Font(30)
    ).apply {
        isVisible = false
        onMouseClicked = {
            middleCardsButtonFunction(2)
        }
    }
    private val handCardsTitel = Label(
        posX = 1000, posY = 1090,visual = ColorVisual(139,69,19),
        width = 1000, height = 125,text = "",
        font = Font(60, color = Color(0,0,0), family = "Papyrus"))
    //hand cards
    private val handCardsView : LinearLayout<CardView> = LinearLayout(
        posX = 1000, posY = 1225,
        width = 1000, height = 480,
        spacing = 50, alignment = Alignment.CENTER,
        orientation = Orientation.HORIZONTAL
    )
    //buttons that allow user to choose which hand card is to be changed
    private val handCard1Button = Button(
        posX = 1060, posY = 1720,
        width = 180, height = 50,
        text = "Card 1", visual = ColorVisual(139,69,19), font = Font(30)
    ).apply {
        isVisible = false
        onMouseClicked = {
            back.isVisible = true
            back.isDisabled = false
            handCardButtonsFunction(0)
        }
    }
    private val handCard2Button = Button(
        posX = 1410, posY = 1720,
        width = 180, height = 50,
        text = "Card 2", visual = ColorVisual(139,69,19), font = Font(30)
    ).apply {
        isVisible = false
        onMouseClicked = {
            back.isVisible = true
            back.isDisabled = false
            handCardButtonsFunction(1)
        }
    }
    private val handCard3Button = Button(
        posX = 1760, posY = 1720,
        width = 180, height = 50,
        text = "Card 3", visual = ColorVisual(139,69,19), font = Font(30 )
    ).apply {
        isVisible = false
        onMouseClicked = {
            back.isVisible = true
            back.isDisabled = false
            handCardButtonsFunction(2)
        }
    }
    //button to see hand cards
    private val showHandButton = Button(
        width = 500, height = 200,
        posX = 1250, posY = 1730,
        text = "Show hand cards",
        font = Font(50, color = Color(139,69,19))
    ).apply {
        visual = ColorVisual.BLACK
        onMouseClicked = {

            // Flip-Animation
            handCardsView.forEach {
                playAnimation(
                    FlipAnimation(
                        gameComponentView = it,
                        fromVisual = it.backVisual,
                        toVisual = it.frontVisual,
                        duration = 500
                    )
                )
            }
            isVisible = false
            back.isDisabled = true
            changeOneCardButton.isDisabled = false
            klopfenButton.isDisabled = false
            passenButton.isDisabled = false
            changeThreeCardsButton.isDisabled = false
        }
    }
    private val titelKnock  = Label(
        posX = 20, posY = 950,
        width = 700, height = 160,text = "",
        visual = ColorVisual.GREEN, font = Font(size =50, family = "ITALIC")
    ).apply { isVisible = false }

    private var hasKnocked = false
    //player action buttons
    val klopfenButton = Button(
        width = 300, height = 200,
        posX = 200, posY = 1100,
        text = "Knock",
        font = Font(50, color = Color(139,69,19))
    ).apply{
        visual = ColorVisual.BLACK
        onMouseClicked={
            if (!hasKnocked){
                titelKnock.isVisible = true
                val name = swimApplication.rootService.currentGame!!.
                players[swimApplication.rootService.currentGame!!.currentPlayer].name
                font = Font(10, color = Color(139,69,19))
                titelKnock.text = "$name has knocked!"
                hasKnocked = true
            }
            back.isDisabled = true
            swimApplication.rootService.playerActionService.knock()
            visual = ColorVisual.GREEN
            font = Font(50, color = Color(0,0,0))
        }
    }
    private val passenButton = Button(
        width = 300, height = 200,
        posX = 200, posY = 1310,
        text = "Pass",
        font = Font(50, color = Color(139,69,19))
    ).apply{
        visual = ColorVisual.BLACK
        onMouseClicked={
            back.isDisabled = true
            swimApplication.rootService.playerActionService.pass()
        }
    }
    private val changeThreeCardsButton = Button(
        width = 300, height = 200,
        posX = 200, posY = 1520,
        text = "Change 3\n   cards",
        font = Font(50, color = Color(139,69,19))
    ).apply{
        visual = ColorVisual.BLACK
        onMouseClicked={
            back.isDisabled = true
            swimApplication.rootService.playerActionService.swapAllCards()
            }
        }
    private val changeOneCardButton = Button(
        width = 300, height = 200,
        posX = 200, posY = 1730,
        text = "Change 1\n    card",
        font = Font(50, color = Color(139,69,19))
    ).apply{
        visual = ColorVisual.BLACK
        onMouseClicked={
            //unable buttons to choose a hand card
            handCard1Button.isVisible = true
            handCard2Button.isVisible = true
            handCard3Button.isVisible = true
            menuButton.isDisabled = true
            // Disable other Buttons
            klopfenButton.isDisabled = true
            passenButton.isDisabled = true
            changeThreeCardsButton.isDisabled = true
            back.isDisabled = true
        }
    }
    private val back:Button = Button(
        width = 500, height = 200,
        posX = 2300, posY = 1100,
        text = "Back to choose\n another card?", font = Font(50 , color = Color(0,0,0)), visual =  ColorVisual(149,0,0)
    ).apply {

        isVisible = false
        this.isDisabled = true
        onMouseClicked = {
            klopfenButton.isDisabled = false
            passenButton.isDisabled = false
            changeThreeCardsButton.isDisabled = false
            refreshAfterBackButton()
            isVisible = false
        }
    }

    //card stack view
    private val cardStackView: LinearLayout<CardView> = LinearLayout(
        posX = 200, posY = 450, height = 500, width = 500,
        spacing = 10,
        orientation = Orientation.HORIZONTAL
    )
    //card stack size label
    private val cardStackSizeLabel = Label(
        posX = 200, posY = 360,
        width = 500, height = 90, text = "",visual = ColorVisual(139,69,19),
        font = Font(60, family = "Papyrus")
    )
    //button to exit or continue the game
    val menuButton:Button = Button(
        width = 500, height = 200,
        posX = 2300, posY = 1730,
        text = "Menu", font = Font(70 , color = Color(0,0,0)), visual = ColorVisual(139,69,19)
    ).apply { isDisabled = false }

    //button to get help
    val helpButton:Button = Button(
        width = 500, height = 200,
        posX = 2300, posY = 1400,
        text = "Help?", font = Font(70 , color = Color(0,0,0)), visual = ColorVisual(139,69,19)
    )
    init{
        addComponents(
            changeOneCardButton,changeThreeCardsButton,passenButton,
            klopfenButton, midCardsView, handCardsView, showHandButton,
            cardStackView, cardStackSizeLabel,
            midCard1Button, midCard2Button, midCard3Button,
            handCard1Button,handCard2Button, handCard3Button, menuButton,
            midCardTitel, handCardsTitel, titel,helpButton,back,titelKnock
        )
        background = ImageVisual("2.jpg")

    }

    /**
     * berechnet den Gewinner und die Punkte aller spieler und zeigt die Ergebnisse in der Endszene
     */
    override fun refreshAfterEndGame() {
        titelKnock.isVisible = false
        val swim = swimApplication.rootService.currentGame
        checkNotNull(swim)
        klopfenButton.text = "Knock"
        val resultKeyList: List<Player> = ArrayList(swimApplication.rootService.gameService.computeWinner().keys)
        val resultValueList: List<Double> = ArrayList(swimApplication.rootService.gameService.computeWinner().values)
        val playerService = swimApplication.rootService.playerActionService
        when (swim.players.size) {
            2 -> {
                if (resultValueList[0] == resultValueList[1]) {
                    swimApplication.endScene.sameResult.text = "The game ended in a draw, points: ${resultValueList[0]}"
                    return
                }
                swimApplication.endScene.gameResult1.text =
                    "Winner : ${resultKeyList[1].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[1])} points"
                swimApplication.endScene.gameResult2.text =
                    "${resultKeyList[0].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[0])} points"
            }

            3 -> {
                if (resultValueList[0] == resultValueList[1] && resultValueList[1] == resultValueList[2]) {
                    swimApplication.endScene.sameResult.text = "The game ended in a draw, points: ${resultValueList[0]}"
                    return
                }
                swimApplication.endScene.gameResult1.text =
                    "Winner : ${resultKeyList[2].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[2])} points"
                swimApplication.endScene.gameResult2.text =
                    "${resultKeyList[1].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[1])} points"
                swimApplication.endScene.gameResult3.text =
                    "${resultKeyList[0].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[0])} points"
            }

            4 -> {
                if (resultValueList[0] == resultValueList[1] && resultValueList[1] == resultValueList[2]
                    && resultValueList[2] == resultValueList[3]
                ) {
                    swimApplication.endScene.sameResult.text = "The game ended in a draw, points: ${resultValueList[0]}"
                    return
                }
                swimApplication.endScene.gameResult1.text =
                    "Winner : ${resultKeyList[3].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[3])} points"
                swimApplication.endScene.gameResult2.text =
                    "${resultKeyList[2].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[2])} points"
                swimApplication.endScene.gameResult3.text =
                    "${resultKeyList[1].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[1])} points"
                swimApplication.endScene.gameResult4.text =
                    "${resultKeyList[0].name}\n${playerService.calculatePointsOfPlayer(resultKeyList[0])} points"
            }
        }
        this.klopfenButton.apply {
            font = Font(50, color = Color(139, 69, 19))
            visual = ColorVisual.BLACK
        }
    }
    /**
     * handelt unterschiedlich, je nachdem in welcher Aktion diese Methode aufgerufen wird. Dies bestimmt
     * @param action zeigt, welche Aktion ausgeführt wird
     */
    override fun refreshAfterTurnEnds(action: Int) {
            refreshAfterCardsChanged()
    }

    /**
     * setzt das Spiel nach Wahl einer Karte zurück,
     * falls der Spieler sich umentschieden hat und eine andere Karte
     * Wählen möchte
     */
    private fun refreshAfterBackButton() {
        val swimGame = swimApplication.rootService.currentGame
        checkNotNull(swimGame)
        cardMap.clear()
        val cardImageLoader = CardImageLoader()
        initializeLinearLayoutView(swimGame.middle, midCardsView, cardImageLoader)
        initializeLinearLayoutView(
            swimGame.players[swimGame.currentPlayer].handCards, handCardsView, cardImageLoader
        )
        initializeStackView(swimGame.drawPile, cardStackView, cardImageLoader)
        midCardsView.forEach { it.showFront() }
        cardStackView.forEach { it.showBack() }
        handCardsView.forEach{ it.showFront() }

        cardStackSizeLabel.text = "Cards in stack: ${swimGame.drawPile.size}"
        showHandButton.isVisible = false
        changeOneCardButton.isDisabled = false
        changeThreeCardsButton.isDisabled = true
        passenButton.isDisabled = true
        klopfenButton.isDisabled = true
        midCard1Button.isVisible = false
        midCard2Button.isVisible = false
        midCard3Button.isVisible = false
        selectedMidCard = mutableListOf(false, false, false)

        handCard1Button.isVisible = true
        handCard2Button.isVisible = true
        handCard3Button.isVisible = true
        selectedHandCard = mutableListOf(false, false, false)

        val currentPlayer = swimApplication.rootService.currentGame!!.currentPlayer
        removeComponents(handCardsTitel)
        handCardsTitel.text ="Cards of: ${swimApplication.rootService.currentGame!!.players[currentPlayer].name}"
        addComponents(handCardsTitel)
        removeComponents(passC)
        passC.text ="Passcounter: ${swimApplication.rootService.currentGame!!.passCounter}/" +
                "${swimApplication.rootService.currentGame!!.players.size}"

        addComponents(passC)
        hasKnocked = false

    }

    /**
     * aktualisiert die Szene nach jedem Zug
     */
    override fun refreshAfterCardsChanged() {
        val swimGame = swimApplication.rootService.currentGame
        checkNotNull(swimGame)
        cardMap.clear()
        val cardImageLoader = CardImageLoader()
        initializeLinearLayoutView(swimGame.middle, midCardsView, cardImageLoader)
        initializeLinearLayoutView(
            swimGame.players[swimGame.currentPlayer].handCards, handCardsView, cardImageLoader
        )
        initializeStackView(swimGame.drawPile, cardStackView, cardImageLoader)
        midCardsView.forEach { it.showFront() }
        cardStackView.forEach { it.showBack() }

        cardStackSizeLabel.text = "Cards in stack: ${swimGame.drawPile.size}"
        showHandButton.isVisible = true
        changeOneCardButton.isDisabled = true
        changeThreeCardsButton.isDisabled = true
        passenButton.isDisabled = true
        klopfenButton.isDisabled = true
        midCard1Button.isVisible = false
        midCard2Button.isVisible = false
        midCard3Button.isVisible = false
        selectedMidCard = mutableListOf(false, false, false)



        handCard1Button.isVisible = false
        handCard2Button.isVisible = false
        handCard3Button.isVisible = false
        menuButton.isDisabled = false

        selectedHandCard = mutableListOf(false, false, false)

        val currentPlayer = swimApplication.rootService.currentGame!!.currentPlayer
        removeComponents(handCardsTitel)
        handCardsTitel.text ="Cards of: ${swimApplication.rootService.currentGame!!.players[currentPlayer].name}"
        addComponents(handCardsTitel)
        removeComponents(passC)
        passC.text ="Passcounter: ${swimApplication.rootService.currentGame!!.passCounter}/" +
                "${swimApplication.rootService.currentGame!!.players.size}"

        addComponents(passC)
    }

    /**
     * clears log area and the game result label in the end scene
     */
    override fun refreshAfterStartGame() {
        hasKnocked = false
        titelKnock.isVisible = false
        titelKnock.text = ""

        val currentPlayer = swimApplication.rootService.currentGame!!.currentPlayer
        removeComponents(passC)
        passC.text ="Passcounter: ${swimApplication.rootService.currentGame!!.passCounter}"
        addComponents(passC)
        removeComponents(handCardsTitel)
        handCardsTitel.text ="Cards of: ${swimApplication.rootService.currentGame!!.players[currentPlayer].name}"
        addComponents(handCardsTitel)
        swimApplication.endScene.gameResult1.text = ""
        swimApplication.endScene.gameResult2.text = ""
        swimApplication.endScene.gameResult3.text = ""
        swimApplication.endScene.gameResult4.text = ""
        refreshAfterCardsChanged()
        this.klopfenButton.apply {
            font = Font(50, color = Color(139, 69, 19))
            visual = ColorVisual.BLACK
        }
    }
    /**
     * löscht [linearLayout] und fügt für jedes [CardView] ein neues Element
     * von [cards] darauf hinzu und fügt das neu erstellte Ansicht/Karten-Paar
     * zur globalen [cardMap] hinzu.
     */
    private fun initializeLinearLayoutView(
        cards: MutableList<Card>,
        linearLayout: LinearLayout<CardView>,
        cardImageLoader: CardImageLoader
    ) {
        linearLayout.clear()
        cards.forEach { card ->
            val cardView = CardView(
                height = 500,
                width = 300,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            linearLayout.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    /**
     * löscht [stackView], fügt für jede [CardView]
     * ein neues Element von [stack] darauf und fügt das neu erstellte Ansicht/Karten-Paar
     * zur globalen [cardMap] hinzu.
     */
    private fun initializeStackView(stack: MutableList<Card> , stackView: LinearLayout<CardView>,
                                    cardImageLoader: CardImageLoader) {
        stackView.clear()
        for (card in stack){
            val cardView = CardView(
                height = 300,
                width = 180,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    /**
     * Aktion, die ausgeführt wird, nachdem eine mittlere Karte gewählt wurde.
     * Sie bewegt die ausgewählte Karte vorwärts, markiert sie.
     * Wenn die HandKarte ausgewählt ist, werden mittel und handkarte ausgetauscht
     */
    private fun middleCardsButtonFunction(index: Int){
        val game = swimApplication.rootService.currentGame
        checkNotNull(game)
        if (!midCardSelected) {
            playAnimationFunction(midCardsView,index)
            midCardsView.components[index].frontVisual = ColorVisual.RED.apply { transparency = 0.2 }
            selectedMidCard[index] = true
            if (midCardSelected && handCardSelected) {
                val indexHand = selectedHandCard.indexOf(true)
                swimApplication.rootService.playerActionService.swapOneCard(
                    game.players[game.currentPlayer].handCards[indexHand],
                    game.middle[index]
                )
            }
        }
        back.isDisabled = true
        back.isVisible = false
    }

    /**
     * Aktion, die ausgeführt wird, nachdem eine Hand Karte gewählt wurde
     * bewegt die ausgewählte Karte vorwärts, markiert sie,
     * wenn die mittlere Karte danach ausgewählt wird, werden mittel und handkarte ausgetauscht
     */
    private fun handCardButtonsFunction(index:Int){
        if (!handCardSelected) {
            playAnimationFunction(handCardsView,index)
            handCardsView.components[index].frontVisual = ColorVisual(149,0,0).apply { transparency = 0.9 }
            selectedHandCard[index] = true

            midCard1Button.isVisible = true
            midCard2Button.isVisible = true
            midCard3Button.isVisible = true
        }
    }
    /**
     * Animation, die eine ausgewählte Karte vorwärts bewegt
     */
    private fun playAnimationFunction(cardsView: LinearLayout<CardView>,index:Int){
        playAnimation(
            MovementAnimation(
                componentView = cardsView.components[index],
                byX = 0, byY = -50
            ).apply { onFinished = { componentView.posY -= 50 } }
        )
    }
}