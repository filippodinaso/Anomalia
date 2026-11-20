package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;


import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCheckpoint;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.Negozio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RepositorioContenuti;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.FabbricaScontri;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//Decide quale schermata mostrare e le collega tra loro.
public final class GestoreSchermate {

    private static final double LARGHEZZA = 1000;
    private static final double ALTEZZA = 650;

    private final FabbricaScontri scontri;
    private final Scene scena;
    private final RepositorioContenuti contenuti;
    private final RegistroCapitoli capitoli;
    private final RepositoryPartite repository;


    public GestoreSchermate(Stage finestra, RepositorioContenuti contenuti, RegistroCapitoli capitoli, RepositoryPartite repository) {
        this.contenuti = contenuti;
        this.capitoli = capitoli;
        this.repository = repository;
        this.scontri = new FabbricaScontri(contenuti.nemici(), contenuti.innocui());
        this.scena = new Scene(new StackPane(), LARGHEZZA, ALTEZZA);
        this.scena.getStylesheets().add(GestoreSchermate.class.getResource("/anomalia.css").toExternalForm());
        finestra.setScene(scena);
    }

    //Menu

    public void mostraMenu() {
        mostra(new SchermataMenu(this));
    }

    public boolean esisteSalvataggio() {
        return repository.esiste();
    }

    public void nuovaPartita() {
        mostraHub(Partita.nuova(capitoli, contenuti.abilità(), repository));
    }

    public void continuaPartita() {
        try {
            mostraHub(Partita.carica(capitoli, contenuti.abilità(), repository));
        } catch (IllegalStateException e) {
            mostraErrore("Impossibile caricare la partita.\n" + e.getMessage());
    }
    }

        public void apri(Partita partita, Nodo nodo) {
        if (!partita.entra(nodo)) {
            return;
        }
        switch (nodo) {
            case NodoCombattimento combattimento -> mostra(new SchermataArena(this, partita, combattimento, scontri));
            case NodoCheckpoint checkpoint -> {
                salva(partita);
                mostra(new SchermataSafeRoom(this, partita,
                    new Negozio(partita.getGiocatore(), contenuti.abilità(), contenuti.talenti())));
        }
            default -> throw new IllegalStateException("Nessuna schermata per il nodo " + nodo.getId());
        }
    }

    public void salva(Partita partita) {
        try {
            partita.salva();
        } catch (IllegalStateException e) {
            mostraErrore("Impossibile salvare la partita.\n" + e.getMessage());
        }
    }

    private void mostra(Parent contenuto) {
        scena.setRoot(contenuto);
    }

    public void mostraHub(Partita partita) {
        salva(partita);
        mostra(new SchermataHub(this, partita));
    }

    private void mostraErrore(String messaggio) {
        Alert avviso = new Alert(Alert.AlertType.ERROR, messaggio);
        avviso.setHeaderText(null);
        avviso.showAndWait();
    }
}    