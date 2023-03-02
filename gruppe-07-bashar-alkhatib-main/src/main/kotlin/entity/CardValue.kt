package entity

import java.util.*

/**
 * Enum to distinguish between the 13 possible values in a french-suited card game:
 * 2-10, Jack, Queen, King, and Ace.
 *
 * The values are ordered according to their most common ordering:
 * 2 < 3 < ... < 10 < Jack < Queen < King < Ace
 *@property TWO -> "2"
 *@property THREE -> "3"
 *@property FOUR -> "4"
 *@property FIVE -> "5"
 *@property SIX -> "6"
 *@property SEVEN -> "7"
 *@property EIGHT -> "8"
 *@property NINE -> "9"
 *@property TEN -> "10"
 *@property JACK -> "J"
 *@property QUEEN -> "Q"
 *@property KING -> "K"
 *@property ACE -> "A"
 */
enum class CardValue {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE,
    ;

    /**
     * provide a single character to represent this value.
     * Returns one of: 2/3/4/5/6/7/8/9/10/J/Q/K/A
     */
    override fun toString() =
        when(this) {
            TWO -> "2"
            THREE -> "3"
            FOUR -> "4"
            FIVE -> "5"
            SIX -> "6"
            SEVEN -> "7"
            EIGHT -> "8"
            NINE -> "9"
            TEN -> "10"
            JACK -> "J"
            QUEEN -> "Q"
            KING -> "K"
            ACE -> "A"
        }

    /**
     * wandelt den Value der Karte i ein Double-Wert um
     */
    fun toDouble(): Double {
        return when (this) {
            TWO -> 2.0
            THREE -> 3.0
            FOUR -> 4.0
            FIVE -> 5.0
            SIX -> 6.0
            SEVEN -> 7.0
            EIGHT -> 8.0
            NINE -> 9.0
            TEN -> 10.0
            JACK -> 10.0
            QUEEN -> 10.0
            KING -> 10.0
            ACE -> 11.0
        }
    }

    /**
     * A set of values for a reduced set of 4x8=32 cards (starting with the 7)
     */
    companion object {
        /**
         * A set of values for a reduced set of 4x8=32 cards (starting with the 7)
         */
        fun shortDeck(): Set<CardValue> {
            return EnumSet.of(ACE, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING)
        }

    }

}