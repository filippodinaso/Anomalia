package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.AvanzamentoCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//schermata principale: statistiche e mappa capitolo 
public final class SchermataHub extends BorderPane {

    public SchermataHub(GestoreSchermate gestore, Partita partita) {
        AvanzamentoCapitolo avanzamento = partita.getAvanzamento();

        setTop(creaBarraStatistiche(partita.getGiocatore()));
        setCenter(creaAreaCentrale(gestore, partita, avanzamento));
        setBottom(creaBarraInferiore(gestore, partita));
    }

    //creiamo la barra in cima con le statistiche
    private HBox creaBarraStatistiche(Giocatore giocatore) {
        Label integrita = new Label("INTEGRITÀ: " + giocatore.getIntegrita() + "/" + giocatore.getIntegritaMassima());
        Label cpu = new Label("CPU: " + giocatore.getCpu().getCorrente() + "/" + giocatore.getCpu().getMassima());
        Label dati = new Label("DATI: " + giocatore.getProgressione().getFrammentiDati());
        Label anomalia = new Label("ANOMALIA: Lvl " + giocatore.getProgressione().getLivelloAnomalia());

        HBox barraStatistiche = new HBox(35, integrita, cpu, dati, anomalia);
        barraStatistiche.setAlignment(Pos.CENTER_LEFT);
        barraStatistiche.getStyleClass().add("barra-statistiche");
        return barraStatistiche;
    }

    //titolo del capitolo e riquadro della mappa + messaggioStato
    private VBox creaAreaCentrale(GestoreSchermate gestore, Partita partita, AvanzamentoCapitolo avanzamento) {
        String nomeCapitolo = "[ " + avanzamento.getMappa().getNome().toUpperCase() + " ]";
        Label titoloCapitolo = new Label(nomeCapitolo);
        titoloCapitolo.getStyleClass().add("titolo-capitolo");

        MappaView vistaMappa = new MappaView(avanzamento, nodoScelto -> gestore.apri(partita, nodoScelto));

        Label messaggioStato = new Label(testoMessaggioStato(partita));

        VBox areaCentrale = new VBox(14, titoloCapitolo, vistaMappa, messaggioStato);
        areaCentrale.setAlignment(Pos.CENTER);
        areaCentrale.setPadding(new Insets(16, 24, 16, 24));
        return areaCentrale;
    }

    private String testoMessaggioStato(Partita partita) {
        if (!partita.isCapitoloConcluso()) 
            return "Seleziona un nodo evidenziato per procedere.";
        
        if (partita.haCapitoloSuccessivo()) 
            return "Capitolo concluso. Procedi al capitolo successivo.";
        
        return "Hai completato tutti i capitoli!";
    }

    //creiamo la barra inferiore con una linea verde divisoria e il pulsante per tornare al menu
    private HBox creaBarraInferiore(GestoreSchermate gestore, Partita partita) {
        HBox barraInferiore = new HBox(20);
        barraInferiore.setAlignment(Pos.CENTER);
        barraInferiore.getStyleClass().add("barra-inferiore");

        if (partita.isCapitoloConcluso() && partita.haCapitoloSuccessivo()) {
            Button bottoneAvanti = new Button("> PROSSIMO CAPITOLO");
            bottoneAvanti.setOnAction(evento -> {
                partita.avanzaCapitolo();
                gestore.mostraHub(partita);
            });
            barraInferiore.getChildren().add(bottoneAvanti);
        }

        Button bottoneMenu = new Button("> TORNA AL MENU");
        bottoneMenu.setOnAction(evento -> gestore.mostraMenu());
        barraInferiore.getChildren().add(bottoneMenu);
        return barraInferiore;
    }
}