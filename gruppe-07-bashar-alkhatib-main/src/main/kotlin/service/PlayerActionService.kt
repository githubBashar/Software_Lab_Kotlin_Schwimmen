package service
import entity.*


/**
 * Die Player Service gewährt Zugriff auf alle Aktionen,
 * die vom Spieler während des Spiels ausgeführt werden können
 * @param rootService  Bietet Zugriff auf alle anderen Dienstklassen und hält den currentGame-Zustand
 */
class PlayerActionService( val rootService: RootService): AbstractRefreshingService(){

    /**
     * Eine einzelne Karte wird von der Hand mit einer einzelnen Karte aus der Mitte ausgetauscht.
     *
     * @param [handCard] beschreibt die Karte auf der Hand,
     * die der Spieler durch die [midCard] ersetzen möchte
     * @param [midCard] beschreibt die Karte in der Mitte,
     * die der Spieler nehmen und ersetzen möchte
     */
    fun swapOneCard(handCard: Card, midCard: Card) {
        val game = rootService.currentGame
        checkNotNull(game)
        val current = game.currentPlayer
        val midCards = game.middle
        val hand = game.players[current].handCards
        //Hand cards are replaced by table's card
        hand.remove(handCard)
        hand.add(midCard)
        //Hand card replaces card on the table
        midCards.remove(midCard)
        midCards.add(handCard)
        nextPlayer()
        onAllRefreshables { refreshAfterTurnEnds(3) }
    }

    /**
     * Der Spieler nimmt alle Karten aus der Mitte auf die Hand und legt alle Karten,
     * die er zuvor auf der Hand hatte, in die Mitte.
     */
    fun swapAllCards() {
        val game = rootService.currentGame
        checkNotNull(game)
        val temp = game.middle
        val currentPlayer = game.currentPlayer

        game.middle = game.players[currentPlayer].handCards
        game.players[currentPlayer].handCards = temp

        nextPlayer()
        onAllRefreshables { refreshAfterTurnEnds(4) }
    }

    /**
     * beschreibt den Vorgang PASSEN in unserem Spiel
     * möchte ein Spieler keine Karten austauschen,
     * kann er passen. Haben der Reihe nach alle Spieler gepasst,
     * werden die drei Karten aus der Mitte auf den Ablagestapel gelegt und
     * drei neue vom Nachziehstapel in die Mitte gelegt.
     *
     */
    fun pass(){
        val swimGame = rootService.currentGame //pfad von MainGame
        checkNotNull(swimGame)
        swimGame.passCounter ++
        if (swimGame.passCounter ==swimGame.players.size) {
            swimGame.passCounter = 0
            rootService.gameService.changeAllCardInMid()
        }
        nextPlayer()
        onAllRefreshables { refreshAfterTurnEnds(1) }
    }

    /**
     * Denkt ein Spieler, ein gutes Blatt auf der Hand zu haben,
     * kann er klopfen.
     * Genauso wie beim Passen tauscht er hierbei keine Karten aus.
     * Nachdem ein Spieler geklopft hat,
     * sind alle anderen Spieler noch genau einmal an der Reihe. Danach ist das Spiel beendet.
     */
    fun knock(){
        val swimGame = rootService.currentGame
        checkNotNull(swimGame)
        swimGame.players[swimGame.currentPlayer].hasKnocked = true
        swimGame.passCounter = 0
        nextPlayer()
        onAllRefreshables { refreshAfterTurnEnds(2) }
    }


    /**
     * ruft den nächsten Player auf,
     * wenn der nächste Spieler geklopft hat, dann beende das Spiel
     */
    fun nextPlayer(){
        val swimGame = rootService.currentGame
        checkNotNull(swimGame)

        //prüfen, ob der nächste Spieler geklopft hat, wenn, ja dann Spiel zu Ende
        if (swimGame.players[(swimGame.currentPlayer + 1) % (swimGame.players.size)].hasKnocked){
            rootService.gameService.endGame()
        }
        //erhöhe um 1
        swimGame.currentPlayer = (swimGame.currentPlayer + 1) % (swimGame.players.size)
    }

    /**
     *  rechnet die Punkte gemäß der uns vorgeschriebenen Berechnungsregeln des Spiels "SWIM"
     *  und gibt die höchste Summe aus.
     *  Vorgehen: jede Sorte wird einer Stelle in meiner Liste zugewiesen
     *  in dieser Stelle wird der Wert von der Karte aufaddiert
     *  am Ende habe ich in dieser Liste an den ersten 4 Stellen alle Sorten mit den aufaddierten Values
     *  und wenn ein Spieler drei Karten unterschiedlicher Farbe,
     *  aber gleicher Art, (zum Beispiel 3x8 oder 3xDame) hat, dann wird in die 4te Stelle der Wert 30.5 kommen.
     *  @param player ist ein Spieler von dem wir die höchste mögliche Summe seiner Karten berechnen
     */
    fun calculatePointsOfPlayer(player: Player) : Double {
        val swimGame = rootService.currentGame
        checkNotNull(swimGame)
        val myCard: MutableList<Double> = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0)
        // jede Sorte wird einer Stelle in meiner Liste zugewiesen
        // in dieser Stelle wird der Wert von der Karte aufaddiert
        // am Ende habe ich in dieser Liste an den ersten 4 Stellen alle Sorten mit den aufaddierten Values
        // sowas : ["♣","♠","♥",0.0,0.0 ] mit den aufaddierten Values mithilfe von toDouble() [11,7,7,0.0,0.0 ],
        // wenn der Spieler folgende Karten hat, ["♣" ACE,"♠" 7,"♥" 7] am Ende wird die höchste Summe ausgegeben
        for (card: Card in player.handCards) {
            when (card.suit.toString()) {
                "♣" -> myCard[0] = myCard[0] + card.value.toDouble()
                "♠" -> myCard[1] = myCard[1] + card.value.toDouble()
                "♥" -> myCard[2] = myCard[2] + card.value.toDouble()
                "♦" -> myCard[3] = myCard[3] + card.value.toDouble()
            }
        }
        // hier spielt die 4te Stelle in meiner Liste die wichtige Rolle,
        // wenn ein Spieler drei Karten unterschiedlicher Farbe,
        // aber gleicher Art (zum Beispiel 3×8 oder 3xDame), dann wird in die 4te Stelle der Wert 30.5 kommen,
        // was definitiv der höchste Wert in der Liste sein und ausgegeben wird
        val cardVal1 = player.handCards[0].value.toString()
        val cardVal2 = player.handCards[1].value.toString()
        val cardVal3 = player.handCards[2].value.toString()
        if ((cardVal1 == cardVal2 && cardVal2 == cardVal3)) {
            myCard[4] = 30.5
        }
        return myCard.maxOrNull() as Double
    }
}