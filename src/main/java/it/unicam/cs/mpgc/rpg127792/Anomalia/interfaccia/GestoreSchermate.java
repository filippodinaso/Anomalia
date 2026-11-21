package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCheckpoint;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controllori.ControllerPartita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controllori.ControllerSafeRoom;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controllori.ControllerScontro;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RepositorioContenuti;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.FabbricaScontri;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/** Coordina le schermate JavaFX e mantiene la partita corrente. */
public final class GestoreSchermate {

    private static final double LARGHEZZA = 1000;
    private static final double ALTEZZA = 650;

    private final FabbricaScontri scontri;
    private final Scene scena;
    private final RepositorioContenuti contenuti;
    private final ControllerPartita controllerPartita;
    private Partita partitaCorrente;


/**
 *  Prepara la finestra principale e crea gli oggetti condivisi dalle schermate.
 *
 *  @param finestra finestra principale dell'applicazione
 *  @param contenuti contenuti usati per creare gli scontri e la Safe Room
 *  @param controllerPartita controller delle operazioni sulla partita
 */
    public GestoreSchermate(Stage finestra, RepositorioContenuti contenuti, ControllerPartita controllerPartita) {
        this.contenuti = contenuti;
        this.controllerPartita = controllerPartita;
        this.scontri = new FabbricaScontri(contenuti.nemici(), contenuti.innocui());
        this.scena = new Scene(new StackPane(), LARGHEZZA, ALTEZZA);
        this.scena.getStylesheets().add(GestoreSchermate.class.getResource("/anomalia.css").toExternalForm());
        finestra.setScene(scena);
    }

    public void mostraMenu() {
        mostra(new SchermataMenu(this));
    }

    public boolean esisteSalvataggio() {
        return controllerPartita.esisteSalvataggio();
    }

    public void nuovaPartita() {
        mostraHub(controllerPartita.nuovaPartita());
    }

    /** Carica la partita salvata e mostra un errore se il caricamento fallisce. */
    public void continuaPartita() {
        try {
            mostraHub(controllerPartita.continuaPartita());
        } catch (IllegalStateException e) {
            mostraErrore("Impossibile caricare la partita.\n" + e.getMessage());
        }
    }

/**
 *  Apre la schermata prevista per il nodo passato come argomento.
 */
    public void apri(Nodo nodo) {
        if (partitaCorrente == null) {
            return;
        }

        if (!controllerPartita.entra(partitaCorrente, nodo)) {
            return;
        }

        switch (nodo) {
            case NodoCombattimento combattimento -> mostra(new SchermataArena(this, aggiungiAlLog ->
                    new ControllerScontro(partitaCorrente, combattimento, scontri, evento -> aggiungiAlLog.accept(FormattatoreEventi.formatta(evento)))));
            case NodoCheckpoint checkpoint -> {
                salvaPartitaCorrente();
                ControllerSafeRoom controller = new ControllerSafeRoom(partitaCorrente, contenuti.abilità(), contenuti.talenti());
                mostra(new SchermataSafeRoom(this, controller));
            }
            default -> throw new IllegalStateException("Nessuna schermata per il nodo " + nodo.getId());
        }
    }

/** Salva la partita corrente e segnala eventuali errori. */
    public void salvaPartitaCorrente() {
       if (partitaCorrente == null) 
            return;
    
        try {
            controllerPartita.salva(partitaCorrente);
        } catch (IllegalStateException e) {
            mostraErrore("Impossibile salvare la partita.\n" + e.getMessage());
        }
    }
/** Sostituisce la schermata corrente con quella passata come argomento */
    private void mostra(Parent contenuto) {
        scena.setRoot(contenuto);
    }

/** Aggiorna partitaCorrente, poi chiama aggiornaHub  */ 
    public void mostraHub(Partita partita) {
        partitaCorrente = partita;
        aggiornaHub();
    }

/** Se partitaCorrente esiste, salva e ne mostra la schermata hub aggiornata */
    public void aggiornaHub() {
        if (partitaCorrente == null) {
            return;
        }
        salvaPartitaCorrente();
        mostra(new SchermataHub(this, partitaCorrente));
    }

    public void avanzaCapitolo() {
        controllerPartita.avanzaCapitolo(partitaCorrente);
        aggiornaHub();
    }

    private void mostraErrore(String messaggio) {
        Alert avviso = new Alert(Alert.AlertType.ERROR, messaggio);
        avviso.setHeaderText(null);
        avviso.showAndWait();
    }
}