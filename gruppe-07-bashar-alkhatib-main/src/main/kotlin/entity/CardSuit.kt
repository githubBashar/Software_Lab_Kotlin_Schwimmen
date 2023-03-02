package entity
/**
 * Enum to distinguish between the four possible suits in a french-suited card game:
 * clubs, spades, hearts, or diamonds
 * @property CLUBS ist eine Karten-Sorte "♣"
 * @property SPADES ist eine Karten-Sorte "♠"
 * @property HEARTS ist eine Karten-Sorte "♥"
 * @property DIAMONDS ist eine Karten-Sorte "♦"

 */
enum class CardSuit {
    CLUBS,
    SPADES,
    HEARTS,
    DIAMONDS,
    ;

    /**
     * provide a single character to represent this suit.
     * Returns one of: ♣/♠/♥/♦
     */
    override fun toString() = when(this) {
        CLUBS -> "♣"
        SPADES -> "♠"
        HEARTS -> "♥"
        DIAMONDS -> "♦"
    }
}