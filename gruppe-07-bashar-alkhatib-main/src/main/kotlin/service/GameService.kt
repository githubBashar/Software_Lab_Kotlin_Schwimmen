package service
import view.*
import entity.*


/**
 * Klasse [GameService] Service fungiert als Wrapper für alle Entitäten und verbindet sie mit den Services
 * @param rootService siehe [RootService]
 * @property myDrawPile enthält alle 32 Karten, erstellt und gemischt mithilfe von [createCards]
 */
class GameService(private val rootService: RootService) : Refreshable , AbstractRefreshingService() {
     var myDrawPile : MutableList<Card> = createCards() // hier weise ich meine 32 Karten zu

    /**
     * hier werden 32 Karten erzeugt und als Liste ausgegeben
     * @return gibt eine Liste von 32 gemischten Karten aus
     */
    private fun  createCards() : MutableList<Card> {
        val drawPile = mutableListOf<Card>()
        val listOfCardsuit = mutableListOf(
            CardSuit.CLUBS,
            CardSuit.SPADES,
            CardSuit.HEARTS,
            CardSuit.DIAMONDS
        )   // Liste, die alle Sorten von Karten enthält
        val listOfCardValue =mutableListOf(
            CardValue.SEVEN,
            CardValue.EIGHT,
            CardValue.NINE,
            CardValue.TEN,
            CardValue.QUEEN,
            CardValue.JACK,
            CardValue.KING,
            CardValue.ACE)// Liste, die alle werte von Karten enthält

        for (i in 0..3) {
            for (b in 0..7) {
                drawPile.add(Card(listOfCardsuit[i],listOfCardValue[b]))
            }
            drawPile.shuffle() //4 Mal shuffeln reicht ;)
        }
        return drawPile
    }

    /**
     * gibt 3 Karten aus und löscht sie aus der Liste von [createCards]
     * @return gibt eine Liste von 3 Karten aus
     */
    private fun getThreeCards() : MutableList<Card> {
        val threeCards = mutableListOf<Card>()
        if (myDrawPile.size < 3) {
            this.endGame()
        } else {
            for (i in 0..2) {
                threeCards.add(myDrawPile[0])
                myDrawPile.removeAt(0)
            }
        }
        return threeCards
    }

    /**
     * hier wird das Spiel gestartet
     * 3 Karten werden in die Mitte gelegt
     * 3 Karten für jeden Spieler
     * 26 Karten bleiben in [myDrawPile]
     * @param playerNames enthält die Namen der Spieler
     */
    fun startGame(playerNames: List<String>) {
        this.myDrawPile.clear()
        this.myDrawPile = createCards()
        val players : MutableList<Player> = mutableListOf()// wird in [startGame] gefüllt
        val middle : MutableList<Card> = getThreeCards() //hier gebe ich von 3 Karten von cards
        for (name in playerNames){
            players.add(Player(getThreeCards(), name))
        }
        rootService.currentGame = MainGame(players,myDrawPile,middle)
        onAllRefreshables { refreshAfterStartGame() }
    }


    /**
     * hierbei wird das Spiel beendet
     * und die Aktualisierung an die Benutzeroberfläche vermittelt
     * Punktanzahl wird hier sortiert berechnet zu übergeben
     */
    fun endGame() {
        onAllRefreshables { refreshAfterEndGame() }
    }

    /**
     * hierbei werden die Karten in der Mitte mit 3 neuen Karten von [myDrawPile] ausgetauscht
     */
    fun changeAllCardInMid() {
        val swimGame = rootService.currentGame
        checkNotNull(swimGame)
        if (myDrawPile.size < 3){
            rootService.gameService.endGame()
        }
        // speichert die auszutauschenden Karten von der Mitte
        val tmpOfMiddleCards =swimGame.middle
        swimGame.middle.clear()
        swimGame.middle = getThreeCards()
        myDrawPile.addAll(tmpOfMiddleCards)
        onAllRefreshables { refreshAfterCardsChanged() }
    }

    /**
     * Hier wird mithilfe einer HashMap und calculatePointsOfPlayer() in der [PlayerActionService]
     * alle Spieler aufsteigend anhand der berechneten Karten geordnet und den Namen mit den höchsten Karten
     * am Ende ausgegeben
     * @return gibt eine hashmap aus
     */
     fun computeWinner(): Map<Player, Double> {
        val swimGame = rootService.currentGame
        checkNotNull(swimGame)
        val hashMapOfPlayers = HashMap<Player, Double>()
        for (player in swimGame.players) {
            hashMapOfPlayers[player] = rootService.playerActionService.calculatePointsOfPlayer(player)
        }
        // sortiere die HasMap anhand der values aufsteigend und gib sie aus

        return hashMapOfPlayers.toList().sortedBy { (_, value) -> value }.toMap()
        //return "Winner: ${result.keys.last().name} \n" +
     // "with: ${ rootService.playerActionService.calculatePointsOfPlayer(result.keys.last())} points"
    }
}

