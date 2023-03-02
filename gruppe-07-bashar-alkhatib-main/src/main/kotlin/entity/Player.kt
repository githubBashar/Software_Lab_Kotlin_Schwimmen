package entity

/**
 * Entity class um einen Spiueler zu repräsentieren hat einen Namen [name] und eine Liste von Karten [handCards] und zeigt
 * durch [hasKnocked], ob der Spieler geklopt hat
 * @param [handCards] beschreibt die Liste von den Karten, die der Spieler hat
 * @param [name] zeigt den Spielernamen
 * @property [hasKnocked] zeigt, ob der Spieler geklopft hat
 */
data class Player (var handCards : MutableList<Card>, val name: String){
        var hasKnocked :Boolean = false
}
