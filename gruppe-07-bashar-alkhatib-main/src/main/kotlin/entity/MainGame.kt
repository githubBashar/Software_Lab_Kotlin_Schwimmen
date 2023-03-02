package entity

/**
 * hier ist die Entity class, die den Zustand vom Spiel repräsentiert.
 * Für das Spiel sind Spieler [players], Nachziehstapel [drawPile] und Mittelkarten [middle] benötigt
 * @param [players] beschreibt eine Liste von Spieler-Objekten
 * @param [drawPile] beschreibt den Nachziehstapel
 * @param [middle] beschreibt die Karten in der Mitte
 * @property [currentPlayer] beschreibt den aktuellen Spieler
 * @property [passCounter] zeigt die Anzahl von pass
 */
data class MainGame(var players: MutableList<Player>, var drawPile: MutableList<Card>, var middle: MutableList<Card>){
       var currentPlayer: Int = 0
       var passCounter: Int = 0
}