package entity
import kotlin.test.*
/**
 * Entity-class repräsentiert den Test für die Klasse [Player].
 */

class PlayerTest {
    /**
     * hier werden Funktionen der Klasse [Player] getestet. Dazu werden 3 Karten erstellt und einem Player
     * zugewiesen und dann getestet, ob das erwartete auch das aktuelle ist.
     */
    @Test
    fun testPlayerCard(){
        val handCardsTest = mutableListOf<Card>()
        handCardsTest.add(Card(CardSuit.DIAMONDS, CardValue.JACK))
        handCardsTest.add(Card(CardSuit.HEARTS, CardValue.KING))
        handCardsTest.add(Card(CardSuit.CLUBS, CardValue.SEVEN))
        val player1 = Player(handCardsTest,"Bashar")
        assertEquals("Bashar", player1.name)
        assertEquals( handCardsTest,player1.handCards)
        assertEquals(false, player1.hasKnocked)
        player1.hasKnocked = true
        assertEquals(true, player1.hasKnocked)
    }
}