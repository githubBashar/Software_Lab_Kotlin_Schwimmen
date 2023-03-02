package service
import entity.*
import java.lang.IllegalStateException
import kotlin.test.*


/**
 * testet alle Dienste, die die Klasse [GameService] bietet
 */
class GameServiceTest {
    private var root = RootService()
    private var card1 = Card(CardSuit.CLUBS, CardValue.SEVEN)
    private var card2 = Card(CardSuit.HEARTS, CardValue.ACE)
    private var card3 = Card(CardSuit.DIAMONDS, CardValue.EIGHT)
    private var card4 = Card(CardSuit.SPADES, CardValue.ACE)
    private var card5 = Card(CardSuit.CLUBS, CardValue.ACE)
    private var card6 = Card(CardSuit.HEARTS, CardValue.ACE)
    private var cards1 = mutableListOf(card1,card2,card3)
    private var cards2 = mutableListOf(card4,card5,card6)
    private val listOfNames = listOf("Bashar","Bob")

    /**
     * testet die Methode [GameService.startGame] auf alle Funktionen, die sie anbietet
     */
    @Test
    fun startGameTest() {
        root.gameService.startGame(listOfNames)
        assertEquals("Bashar", root.currentGame!!.players[0].name)
        assertEquals("Bob", root.currentGame!!.players[1].name)
        assertEquals(root.currentGame!!.drawPile, root.gameService.myDrawPile)
    }

    /**
     * testet die Methode, wenn es mainGame auf null zeigt also, wenn kein Spiel gestartet ist
     */
    @Test
    fun testChangeAllCardInMid(){
        assertFailsWith<IllegalStateException> {
            root.currentGame = null
            root.gameService.changeAllCardInMid()
        }
    }

    /**
     * testet, wenn es kein Spiel existiert
     * und dass der richtige Winner angezeigt wird
     */
    @Test fun testComputeWinner(){
        assertFailsWith<IllegalStateException> {
            root.gameService.computeWinner()
        }
        createMainGame()
        assertEquals("Bob", root.gameService.computeWinner().keys.last().name)
        assertEquals(30.5, root.gameService.computeWinner().values.last())

    }

    /**
     *  nur für den Test [testComputeWinner] erstellt, dies gibt mir ein [MainGame] Objekt
     */
    private  fun createMainGame (){
        val player1 = Player(cards1, "Bashar")
        val player2 = Player(cards2, "Bob")
        val listOfPlayers = mutableListOf(player1,player2)
        val middleCards = mutableListOf(card1,card2,card3)
        val drawPile = mutableListOf(card4,card5,card6)
        root.currentGame = MainGame(listOfPlayers,drawPile,middleCards)
    }
}

