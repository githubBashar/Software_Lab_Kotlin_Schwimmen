package entity
import kotlin.test.*

/**
 * repräsentiert den Test für die Klasse  [CardSuit]
 */
class CardSuitTest {
    @Test
            /**
             * die Testmethode prüft, ob die Methode toString() richtig arbeitet
             */
    fun testCardSuite() {
        assertEquals("♣" , CardSuit.CLUBS.toString())
        assertEquals("♠" , CardSuit.SPADES.toString())
        assertEquals("♥" , CardSuit.HEARTS.toString())
        assertEquals("♦" , CardSuit.DIAMONDS.toString())
    }
}