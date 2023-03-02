package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
/**
 * [HelpScene] ist ein Button auf der Game-szene.
 * beschreibt die Funktionen des Spiels
 */
class HelpScene : MenuScene(900,900,
    background = ColorVisual(Color.WHITE)) {
    private val titel  = tools.aqua.bgw.components.uicomponents.TextArea(
        posX = 10, posY = 20,
        width = 890, height = 770,text = "Spielablauf von Swim game:\n" +
                "Beginnend beim ersten Spieler sind reihum alle Spieler an der Reihe,\n" +
                "bis das Spiel beendet ist. Ist ein Spieler an der Reihe,\n" +
                "stehen ihm vier mögliche Aktionen zur Auswahl:\n\n" +
                "Change 3 cards: der Spieler nimmt alle Karten aus der Mitte auf die\n" +
                "Hand und legt alle Karten, die er zuvor auf der Hand hatte, in die Mitte.\n\n" +
                "Change 1 cards: eine einzelne Karte\n wird von der Hand mit einer einzelnen\n" +
                "Karte aus der Mitte ausgetauscht.\n\n" +
                "Pass: Möchte ein Spieler keine Karten austauschen,\n" +
                "kann er passen. Haben der Reihe nach alle Spieler gepasst,\n" +
                "werden die drei Karten aus der Mitte auf den Ablagestapel\n" +
                "gelegt und drei neue vom Nachziehstapel in die Mitte gelegt.\n\n" +
                "Knock: Denkt ein Spieler, ein gutes Blatt auf der Hand zu haben,\n" +
                "kann er klopfen. Genauso wie beim Passen tauscht er hierbei keine\n" +
                "Karten aus. Nachdem ein Spieler geklopft hat, sind alle anderen Spieler\n" +
                "noch genau einmal an der Reihe. Danach ist das Spiel beendet.\n\n" +
                "Spielende:\n" +
                "Das Spiel kann auf zwei Arten beendet werden. Der normale Weg ist,\n" +
                "wie zuvor erklärt, dass ein Spieler klopft. Waren danach alle anderen\n" +
                "Spieler noch einmal an der Reihe, ist das Spiel beendet. Es kann aber\n" +
                "auch passieren, dass wenn alle Spieler gepasst haben, nicht mehr genügend\n" +
                "Karten im Nachziehstapel sind, um die Mitte mit drei Karten neu zu bestücken.\n" +
                "In diesem Fall endet das Spiel sofort.\n" +
                "Gewonnen hat bei Spielende der Spieler mit der höchsten Punktzahl.\n" +
                "Bei Punktegleichstand wird das Spiel als unentschieden gewertet.\n\n" +
                "Punktewertung:\n" +
                "Ziel des Spiels ist es, am Ende die meisten Punkte auf der Hand zu haben.\n" +
                "Gewertet wird hierbei die höchste Summe einer der vier Farben,\n" +
                "wobei {Bube, Dame, König} jeweils als 10 Punkte zählen und das Ass als\n" +
                "11 Punkte. Damit sind maximal 31 Punkte (Ass plus zwei Karten mit je 10 Punkten)\n" +
                "möglich. Drei Karten unterschiedlicher Farbe, aber gleicher Art\n" +
                " (z.B 3×8 oder 3xDame) ergeben 30.5 Punkte.\n" +
                "Beispiele für Punkte\n" +
                "-Karo-9, Karo-Bube, Karo-Ass → 30 Punkte\n" +
                "-Herz-7, Herz-10, Herz-Bube → 27 Punkte\n" +
                "-Pik-Ass, Pik-7, Kreuz-Dame → 18 Punkte\n" +
                "-Herz-Bube, Karo-Dame, Kreuz-König → 10 Punkte\n" +
                "-Kreuz-8, Karo-8, Pik-8 → 30.5 Punkte \n\n" +
                "Dieses Spiel ist ein Projekt des Software-Praktikums an der TU Dortmund:\n" +
                "https://sopra.cs.tu-dortmund.de/wiki/sopra/22d/projekt1" ,
        font = Font(size =25, family = "ItaLIC")
    )

    val continueGameButton: Button = Button(
        height = 80,
        width = 160,
        posX = 365,
        posY = 800,
        text = "Continue",
        font = Font(20,color = Color.BLACK, fontStyle = Font.FontStyle.ITALIC)
    )

    init {
        addComponents(
            continueGameButton,titel
        )
    }
}