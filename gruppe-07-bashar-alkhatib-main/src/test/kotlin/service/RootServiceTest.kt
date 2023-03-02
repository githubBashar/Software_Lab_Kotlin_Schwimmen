package service
import view.HelloScene
import view.Refreshable
import kotlin.test.*


/**
 * testet die Klasse [RootService]
 * @property : Pfad zum [gameService]]
 */
class RootServiceTest {
    private val gameService: GameService = RootService().gameService

    /**
     * testet, ob ein refreshable richtig übergeben wird
     */
    @Test
    fun testAddRefreshable(){
        gameService.refreshables = emptyList<Refreshable>().toMutableList()
        gameService.refreshables
    }

    /**
     * testet, ob ein refreshable richtig übergeben wird
     */
    @Test
    fun testOnAllRefreshables(){
        gameService.addRefreshable(HelloScene())
        gameService.onAllRefreshables { refreshAfterCardsChanged() }
    }
}