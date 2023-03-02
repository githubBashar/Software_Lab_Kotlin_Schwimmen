package service
import entity.*
import java.lang.*
import kotlin.test.*

/**
 * testet alle Dienste, die die Klasse [PlayerActionService] bietet
 */
class PlayerActionServiceTest {
    private var root = RootService()
    private var card1 = Card(CardSuit.CLUBS, CardValue.SEVEN)
    private var card2 = Card(CardSuit.HEARTS, CardValue.ACE)
    private var card3 = Card(CardSuit.DIAMONDS, CardValue.EIGHT)
    private var card4 = Card(CardSuit.SPADES, CardValue.ACE)
    private var card5 = Card(CardSuit.CLUBS, CardValue.ACE)
    private var card6 = Card(CardSuit.HEARTS, CardValue.ACE)
    private var cards1 = mutableListOf(card1,card2,card3)
    private var cards2 = mutableListOf(card4,card5,card6)

    /**
     * testet ob der Konstruktor von [PlayerActionService] richtig als Parameter übergeben bekommt
     */
    @Test
    fun testConstructor() {
        val playerService = PlayerActionService(root)
        assertEquals(root,playerService.rootService)
    }

    /**
     * testet ob der knock-flag richtig gesetzt und der Pass-counter auf null gesetzt wird
     */
    @Test
    fun testKnock() {
        assertFailsWith<IllegalStateException> {
            root.playerActionService.knock()
        }
        root.currentGame = createMainGame()
        root.currentGame!!.passCounter = 2
        root.playerActionService.knock()
        assertTrue { root.currentGame!!.players[0].hasKnocked }
        assertEquals(0, root.currentGame!!.passCounter)
        root.playerActionService.knock()
        assertTrue { root.currentGame!!.players[1].hasKnocked }
        assertEquals(0, root.currentGame!!.passCounter)
    }

    /**
     * testet, ob die Karten richtig ausgetauscht wurden
     */
    @Test
    fun testSwapAllCards() {
        assertFailsWith<IllegalStateException> {
            root.currentGame = null
            root.playerActionService.swapAllCards()
        }
        root.currentGame = createMainGame()
        root.currentGame!!.passCounter = 2
        root.playerActionService.swapAllCards()
        assertEquals(2, root.currentGame!!.passCounter)
        assertEquals(cards1, root.currentGame!!.players[0].handCards)
        assertEquals(cards1, root.currentGame!!.middle)
    }

    /**
     * testet, ob die Methode korrekt arbeitet und richtig handelt, wenn es nicht genug karten gibt
     */
    @Test
    fun testNextPlayer(){
        assertFailsWith<IllegalStateException> {
            root.currentGame = null
            root.playerActionService.nextPlayer()
        }
        root.currentGame = createMainGame()
        root.currentGame!!.drawPile.removeFirst()
        root.playerActionService.nextPlayer()
    }

    /**
     * testet, ob es richtig gepasst wird und alle Fälle richtig abgefangen und behandelt werden
     */
    @Test
    fun testPass(){
        assertFailsWith<IllegalStateException> {
            root.currentGame = null
            root.playerActionService.pass()
        }
        root.currentGame = createMainGame()
        root.playerActionService.pass()
        assertEquals(1, root.currentGame!!.passCounter)

        root.currentGame!!.passCounter = 1
        root.playerActionService.pass()
        assertEquals(0, root.currentGame!!.passCounter)
    }

    /**
     * testet, ob die 2 Karten korrekt ausgetauscht und alle Fälle abgefangen und korrekt behandelt werden
     */
    @Test
    fun testSwapOneCard(){
        assertFailsWith<NullPointerException> {
            root.playerActionService.swapOneCard(
                root.currentGame!!.players[0].handCards[0], root.currentGame!!.middle[0])
        }
        root.currentGame = createMainGame()
        root.currentGame!!.passCounter = 2
        root.playerActionService.swapOneCard(
            root.currentGame!!.players[0].handCards[0], root.currentGame!!.middle[0])
        assertEquals(2, root.currentGame!!.passCounter)
        assertEquals(root.currentGame!!.players[0].handCards[0], root.currentGame!!.middle[0])
        assertEquals(root.currentGame!!.middle[0], root.currentGame!!.players[0].handCards[0])
    }



    /**
     * testet, ob die Methode richtig berechnet
     * ich habe alle Fälle gedeckt
     * auch der Fall mit 30.5
     */
    @Test fun testCalculatePointsOfPlayer() {
        assertFailsWith<NullPointerException> {
            root.playerActionService.calculatePointsOfPlayer(root.currentGame!!.players[0])
        }

        root.currentGame = createMainGame()
        assertEquals(11.0, root.playerActionService.calculatePointsOfPlayer(root.currentGame!!.players[0]))

        root.currentGame!!.players[0].handCards.removeLast()
        root.currentGame!!.players[0].handCards.add(card4)
        assertEquals(11.0, root.playerActionService.calculatePointsOfPlayer(root.currentGame!!.players[0]))

        root.currentGame!!.players[0].handCards.clear()
        root.currentGame!!.players[0].handCards.add(card1)
        root.currentGame!!.players[0].handCards.add(card1)
        root.currentGame!!.players[0].handCards.add(card1)
        assertEquals(30.5, root.playerActionService.calculatePointsOfPlayer(root.currentGame!!.players[0]))
    }

    /**
     * erstellt ein Objekt der Klasse [MainGame]
     * @return ein Objekt der Klasse [MainGame]]
     */
    private  fun createMainGame () : MainGame {
        val player1 = Player(cards1, "Bashar")
        val player2 = Player(cards2, "Bob")
        val listOfPlayers = mutableListOf(player1,player2)
        val middleCards = mutableListOf(card1,card2,card3)
        val drawPile = mutableListOf(card4,card5,card6)
        return MainGame(listOfPlayers,drawPile,middleCards)
    }
}