package entity
import kotlin.test.*

/**
 * Test-Klasse, die die Klasse [Card] und ihre Funktionen testet
 */
class CardTest {
    /**
     * hier teste ich die Funktion von der Kartenerzeugung
     */
    @Test
    fun testCard(){
        val cardsToTest = Card(CardSuit.CLUBS, CardValue.EIGHT)
        assertEquals(cardsToTest, Card(CardSuit.CLUBS, CardValue.EIGHT))
        assertEquals(CardSuit.CLUBS, cardsToTest.suit)
        assertEquals(CardValue.EIGHT, cardsToTest.value)
    }

}
