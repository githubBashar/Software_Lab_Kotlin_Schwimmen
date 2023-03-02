package service
// import view.*
import entity.*
import view.Refreshable


/**
 *Hauptklasse der Service-Schicht für unser Spiel.
 *  Sie bietet Zugriff auf alle anderen Dienstklassen und hält den [currentGame]-Zustand
 *  für den Zugriff auf diese Dienste.
 *  @property gameService Objekt der Klasse [GameService]
 *  @property playerActionService Objekt der Klasse [PlayerActionService]
 *  @property currentGame Das derzeit aktive Spiel kann `null` sein, wenn noch kein Spiel gestartet wurde.
 *  @constructor repräsentiert der Service-Schicht für unser Spiel.
 */
class RootService {

     val gameService = GameService(this)
     val playerActionService = PlayerActionService(this)
     var currentGame : MainGame? = null


     /**
      * Fügt das bereitgestellte [newRefreshable] zu allen Services hinzu, die mit diesem [RootService] verbunden sind
      */
     fun addRefreshable(newRefreshable: Refreshable) {
          gameService.addRefreshable(newRefreshable)
          playerActionService.addRefreshable(newRefreshable)
     }
     /**
      * Fügt alle bereitgestellten [newRefreshables] allen Services hinzu,
      * die mit diesem [RootService] verbunden sind
      */
     fun addRefreshables(vararg newRefreshables: Refreshable) {
          newRefreshables.forEach { addRefreshable(it) }
     }
}
