package entity
import kotlin.test.*

/**
 * hier wird ein Test für das [MainGame] implementiert und die Funktion des Codes getestet
 */
class MainGameTest {
    /**
     * in der [mainGameTestMethod] methode wird mithilfe von mehreren Hilfsvariablen die Funktion und Korrektheit getestet
     */
    @Test fun mainGameTestMethod() {
        val drawPile = mutableListOf<Card>()    //enthält den Nachziehstapel
        val middleCard = mutableListOf<Card>()    //enthält die Karten in der Mitte
        val listOfPlayers = mutableListOf<Player>()    //Liste von allen Spielern
        val mainGame1 = MainGame(listOfPlayers, drawPile , middleCard) // hier wird ein Objekt der Klasse [MainGame] erzeugt

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

        val listOfThreeCardsPlayer1 = mutableListOf<Card>()   // hilfsvariable list die 3 Karten für den ersten Spieler hat
        val listOfThreeCardsPlayer2 = mutableListOf<Card>()   // hilfsvariable list die 3 Karten für den zweiten Spieler hat


        // hier wird in der ersten For-Schleife mit i über alle Sorten und mit b wird über alle
        //möglichen Werte von Karten iteriert und dann die 32 Karten erzeugt und drawpile
        //shuffeld hinzugefügt

        for (i in 0..3) {
            for (b in 0..7) {
                drawPile.add(Card(listOfCardsuit[i],listOfCardValue[b]))
            }
            drawPile.shuffle() //4 Mal shuffeln reicht ;)
        }

        //hier werden nur 2 Spieler erzeugt und der Liste hinzugefügt
        listOfPlayers.add(Player(listOfThreeCardsPlayer1,"Bashar"))
        listOfPlayers.add(Player(listOfThreeCardsPlayer2,"Bob"))


        // hier werden Karten verteilt (Verteilungsprinzip wie in der Realität
        // eine Karte für dich, eine karte für mich und eine Karte in die Mitte)
        //bereits verteilte Karten werden gelöscht, damit sie nie wieder vorkommen
        for (i in 0..2){
            listOfThreeCardsPlayer1.add(drawPile[i])
            drawPile.removeAt(i)
            listOfThreeCardsPlayer2.add(drawPile[i])
            drawPile.removeAt(i)
            middleCard.add(drawPile[i])
            drawPile.removeAt(i)
        }

        // hier wird getestet
        assertEquals(mainGame1.players[0].name,"Bashar")
        assertEquals(mainGame1.players[1].name,"Bob")
        assertEquals(mainGame1.players[0].handCards, listOfThreeCardsPlayer1)
        assertEquals(mainGame1.players[1].handCards, listOfThreeCardsPlayer2)
        assertEquals(0 , mainGame1.currentPlayer )
        assertEquals(0 , mainGame1.passCounter)
        assertEquals(middleCard , mainGame1.middle)
        assertEquals(drawPile , mainGame1.drawPile)


        //setCurrentPlayer testen
        mainGame1.currentPlayer = 1
        assertEquals(1 , mainGame1.currentPlayer )

        //setPassCounter testen
        mainGame1.passCounter = 1
        assertEquals(1 , mainGame1.passCounter )

        //setDrawPile testen
        mainGame1.drawPile = mutableListOf(drawPile[0],drawPile[1],drawPile[2])
        assertEquals(mainGame1.drawPile, mutableListOf(drawPile[0],drawPile[1],drawPile[2]) )

        //setMiddle testen
        mainGame1.middle = mutableListOf(drawPile[3],drawPile[4],drawPile[5])
        assertEquals(mainGame1.middle, mutableListOf(drawPile[3],drawPile[4],drawPile[5]) )

        //setPlayers testen
        val listofkarten = mutableListOf(drawPile[6],drawPile[7],drawPile[8])
        mainGame1.players = mutableListOf(Player(listofkarten, "Alex"))
        assertEquals(mainGame1.players, mutableListOf(Player(listofkarten, "Alex")))

    }
}