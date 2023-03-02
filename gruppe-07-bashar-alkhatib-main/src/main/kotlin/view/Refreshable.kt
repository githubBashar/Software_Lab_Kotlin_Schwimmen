package view
import service.AbstractRefreshingService
/**
 * Diese Schnittstelle stellt einen Mechanismus bereit,
 * mit dem die Service-Klassen den View-Klassen mitteilen können,
 * dass bestimmte Änderungen an der Entitätsschicht vorgenommen wurden,
 * sodass die Oberfläche beim Nutze auch dementsprechend geändert wird.
 * Alle folgenden Methoden sind leer, sodass sie von den implementierenden Klassen in der View-Schicht
 * vervollständigt werden
 * @see AbstractRefreshingService
 *
 */

interface Refreshable {
    /**
     * wird nach Start-Game aktualisiert
     */
    fun refreshAfterStartGame(){}

    /**
     * wird nach Spielende aktualisiert und die Spieler sortiert nach Punktanzahl ausgibt
     */
    fun refreshAfterEndGame() {}

    /**
     * wird nach Kartenaustausch aktualisiert
     */
    fun refreshAfterCardsChanged(){}

    /**
     * wird nach dem Zugende aktualisiert
     */
    fun refreshAfterTurnEnds(action :Int){}

}
