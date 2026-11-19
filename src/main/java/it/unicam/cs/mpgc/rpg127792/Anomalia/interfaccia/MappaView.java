package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.AvanzamentoCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.MappaCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoBoss;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCheckpoint;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

//pannello grafico che disegna i nodi e i collegamenti della mappa del capitolo
public final class MappaView extends Pane {

    private static final double LARGHEZZA_MAPPA = 920;
    private static final double ALTEZZA_MAPPA = 400;
    private static final double MARGINE_BORDO = 90;
    private static final double RAGGIO_NODO = 26;
    private static final double LARGHEZZA_ETICHETTA = 140;

    private final Map<Nodo, Double> coordinataX = new HashMap<>();
    private final Map<Nodo, Double> coordinataY = new HashMap<>();

    //costruttore
    public MappaView(AvanzamentoCapitolo avanzamento, Consumer<Nodo> azioneClick) {
        setPrefSize(LARGHEZZA_MAPPA, ALTEZZA_MAPPA);
        setMaxSize(LARGHEZZA_MAPPA, ALTEZZA_MAPPA);
        getStyleClass().add("riquadro-mappa");

        MappaCapitolo mappa = avanzamento.getMappa();
        calcolaCoordinateNodi(mappa);
        disegnaCollegamenti(mappa);
        disegnaNodi(mappa, avanzamento, azioneClick);
    }

    //disegna le linee di collegamento tra un nodo e i suoi nodi successivi
    private void disegnaCollegamenti(MappaCapitolo mappa) {
        for (Nodo nodoOrigine : mappa.getNodi()) {
            for (Nodo nodoDestinazione : mappa.getSuccessivi(nodoOrigine)) {
                double xPartenza = coordinataX.get(nodoOrigine);
                double yPartenza = coordinataY.get(nodoOrigine);
                double xArrivo = coordinataX.get(nodoDestinazione);
                double yArrivo = coordinataY.get(nodoDestinazione);

                Line lineaCollegamento = new Line(xPartenza, yPartenza, xArrivo, yArrivo);
                lineaCollegamento.getStyleClass().add("arco");
                getChildren().add(lineaCollegamento);
            }
        }
    }

    //disegna tutti i nodi sopra le linee di collegamento
    private void disegnaNodi(MappaCapitolo mappa, AvanzamentoCapitolo avanzamento, Consumer<Nodo> azioneClick) {
        for (Nodo nodo : mappa.getNodi()) {
            double centroX = coordinataX.get(nodo);
            double centroY = coordinataY.get(nodo);

            boolean accessibile = avanzamento.isAccessibile(nodo);
            boolean completato = avanzamento.isCompletato(nodo);

            Circle cerchioNodo = new Circle(centroX, centroY, RAGGIO_NODO);
            cerchioNodo.getStyleClass().add("nodo");
            cerchioNodo.getStyleClass().add(classeCssPerTipo(nodo));

        if (completato) {
            cerchioNodo.getStyleClass().add("nodo-completato");
        } else if (accessibile) {
            cerchioNodo.getStyleClass().add("nodo-accessibile");
        } else {
            cerchioNodo.getStyleClass().add("nodo-spento");
        }
        if (accessibile) {
            cerchioNodo.setCursor(Cursor.HAND);
            cerchioNodo.setOnMouseClicked(evento -> azioneClick.accept(nodo));
        }

            //etichetta sotto al cerchio (serve mantenere la differenza tra nodi "spenti" ed "accesi")
            Label nomeNodo = new Label(nodo.getId().replace('_', ' '));
            
            if (accessibile || completato) {
                nomeNodo.getStyleClass().add("etichetta-nodo-attivo");
            } else {
                nomeNodo.getStyleClass().add("etichetta-nodo");
                nomeNodo.getStyleClass().add("nodo-spento");
            }

            nomeNodo.setPrefWidth(LARGHEZZA_ETICHETTA);
            nomeNodo.setAlignment(Pos.CENTER);
            nomeNodo.setLayoutX(centroX - (LARGHEZZA_ETICHETTA / 2));
            nomeNodo.setLayoutY(centroY + RAGGIO_NODO + 6);

            getChildren().addAll(cerchioNodo, nomeNodo);
        }
    }

    //cambiamo stile CSS in base al tipo di nodo 
    private String classeCssPerTipo(Nodo nodo) {
        return switch (nodo) {
            case NodoBoss _ -> "nodoboss";
            case NodoCombattimento _ -> "nodocombattimento";
            case NodoCheckpoint _ -> "nodocheckpoint";
            default -> throw new IllegalStateException("Tipo nodo non previsto: " + (nodo));
        };
    }

    //assegna a ciascun nodo una posizione X e Y esplorando la mappa dalla partenza
    private void calcolaCoordinateNodi(MappaCapitolo mappa) {
        Map<Nodo, Integer> colonnaDelNodo = new HashMap<>();
        List<Nodo> nodiDaEsplorare = new ArrayList<>();

        Nodo partenza = mappa.getPartenza();
        colonnaDelNodo.put(partenza, 0);
        nodiDaEsplorare.add(partenza);
        int ultimaColonna = 0;

        //esploriamo i nodi
        for (int i = 0; i < nodiDaEsplorare.size(); i++) {
            Nodo nodoCorrente = nodiDaEsplorare.get(i);
            int colonnaSuccessiva = colonnaDelNodo.get(nodoCorrente) + 1;

            for (Nodo nodoSuccessivo : mappa.getSuccessivi(nodoCorrente)) {
                if (!colonnaDelNodo.containsKey(nodoSuccessivo)) {
                    colonnaDelNodo.put(nodoSuccessivo, colonnaSuccessiva);
                    nodiDaEsplorare.add(nodoSuccessivo);
                    if (colonnaSuccessiva > ultimaColonna) {
                        ultimaColonna = colonnaSuccessiva;
                    }
                }
            }
        }

        //creiamo una lista per ogni colonna e ci mettiamo dentro i suoi nodi
        List<List<Nodo>> listaColonne = new ArrayList<>();
        for (int indiceColonna = 0; indiceColonna <= ultimaColonna; indiceColonna++) {
            listaColonne.add(new ArrayList<>());
        }
        for (Nodo nodo : nodiDaEsplorare) {
            int indiceColonna = colonnaDelNodo.get(nodo);
            listaColonne.get(indiceColonna).add(nodo);
        }

        //calcoliamo le coordinate X e Y in pixel per ogni nodo di ogni colonna
        double larghezzaUtile = LARGHEZZA_MAPPA - (2 * MARGINE_BORDO);

        for (int indiceColonna = 0; indiceColonna <= ultimaColonna; indiceColonna++) {
            double posizioneX = LARGHEZZA_MAPPA / 2;
            if (ultimaColonna > 0) {
                double distanzaTraColonne = larghezzaUtile / ultimaColonna;
                posizioneX = MARGINE_BORDO + (indiceColonna * distanzaTraColonne);
            }

            List<Nodo> nodiNellaColonna = listaColonne.get(indiceColonna);
            double spazioVerticale = ALTEZZA_MAPPA / (nodiNellaColonna.size() + 1);

            for (int indiceRiga = 0; indiceRiga < nodiNellaColonna.size(); indiceRiga++) {
                double posizioneY = spazioVerticale * (indiceRiga + 1);
                Nodo nodo = nodiNellaColonna.get(indiceRiga);
                coordinataX.put(nodo, posizioneX);
                coordinataY.put(nodo, posizioneY);
            }
        }
    }
}