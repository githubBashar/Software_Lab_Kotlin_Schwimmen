package entity
import java.util.EnumSet
import kotlin.test.*


/**
 * hier wird die Klasse [CardValue] getestet
 */
class CardValueTest {

    /**
     * testet, ob die Methode toString() richtig arbeitet und das richtige ausgibt
     */
    @Test
    fun testCardValue() {
        assertEquals("2" , CardValue.TWO.toString())
        assertEquals("3" , CardValue.THREE.toString())
        assertEquals("4" , CardValue.FOUR.toString())
        assertEquals("5" , CardValue.FIVE.toString())
        assertEquals("6" , CardValue.SIX.toString())
        assertEquals("7" , CardValue.SEVEN.toString())
        assertEquals("8" , CardValue.EIGHT.toString())
        assertEquals("9" , CardValue.NINE.toString())
        assertEquals("10" , CardValue.TEN.toString())
        assertEquals("J" , CardValue.JACK.toString())
        assertEquals("Q" , CardValue.QUEEN.toString())
        assertEquals("K" , CardValue.KING.toString())
        assertEquals("A" , CardValue.ACE.toString())
    }

    /**
     * testet, ob die Methode testShortDeck() richtig arbeitet und das richtige ausgibt
     */
    @Test
    fun testShortDeck(){
        assertEquals( CardValue.shortDeck(), EnumSet.of(
            CardValue.ACE,
            CardValue.SEVEN,
            CardValue.EIGHT,
            CardValue.NINE,
            CardValue.TEN,
            CardValue.JACK,
            CardValue.QUEEN,
            CardValue.KING
        ))
    }

    /**
     * hierbei wird getestet, ob der Wert einer Karte richtig ausgegeben wird
     */
    @Test fun testToDouble(){
        assertEquals(2.0 , CardValue.TWO.toDouble())
        assertEquals(3.0 , CardValue.THREE.toDouble())
        assertEquals(4.0 , CardValue.FOUR.toDouble())
        assertEquals(5.0 , CardValue.FIVE.toDouble())
        assertEquals(6.0 , CardValue.SIX.toDouble())
        assertEquals(7.0 , CardValue.SEVEN.toDouble())
        assertEquals(8.0 , CardValue.EIGHT.toDouble())
        assertEquals(9.0 , CardValue.NINE.toDouble())
        assertEquals(10.0 , CardValue.TEN.toDouble())
        assertEquals(10.0 , CardValue.JACK.toDouble())
        assertEquals(10.0 , CardValue.QUEEN.toDouble())
        assertEquals(10.0 , CardValue.KING.toDouble())
        assertEquals(11.0 , CardValue.ACE.toDouble())
    }
}