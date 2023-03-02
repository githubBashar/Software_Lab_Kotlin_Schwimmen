package service
import view.Refreshable

/**
 *
 * Abstract service Klasse, die mehrere [Refreshable]s (normalerweise UI Elemente, wie z. B.
 * spezialisierte [tools.aqua.bgw.core.BoardGameScene] classes/instances) verarbeitet, die benachrichtigt werden,
 * wenn Änderungen über die Methode:  [onAllRefreshables] aktualisiert werden.
 * @property: eine Liste von [Refreshable]
 * @constructor steuert
 */
abstract class AbstractRefreshingService {
    var refreshables = mutableListOf<Refreshable>()
    /**
     * füge der aufgerufenen Liste ein [Refreshable]
     * immer wenn [onAllRefreshables] verwendet wird.
     */
    fun addRefreshable(newRefreshable : Refreshable) {
        refreshables += newRefreshable
    }
    /**
     * Führt die übergebene Methode (normalerweise ein Lambda) für alle aus
     * [Refreshable]s, die in der service Klasse sind und
     * die Klasse [AbstractRefreshingService] erweitern
     * als Beispiel (von irgendeiner Methode in der Service Klasse):
     * ```
     * onAllRefreshables {
     *   refreshAfterStartGame()
     *   refreshAfterTurnEnds()
     *   .
     *   .
     *   refreshAfterEndGame()
     * }
     * ```
     */
    fun onAllRefreshables(method: Refreshable.() -> Unit) =
        refreshables.forEach { it.method() }
}

