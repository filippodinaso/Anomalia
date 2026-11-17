package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;


import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RepositorioContenuti;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//Decide quale schermata mostrare e le collega tra loro.
public final class GestoreSchermate {

    private static final double LARGHEZZA = 1100;
    private static final double ALTEZZA = 700;

    private final Scene scena;
    private final RepositorioContenuti contenuti;
    private final RegistroCapitoli capitoli;
    private final RepositoryPartite repository;


    public GestoreSchermate(Stage finestra, RepositorioContenuti contenuti, RegistroCapitoli capitoli, RepositoryPartite repository) {
        this.contenuti = contenuti;
        this.capitoli = capitoli;
        this.repository = repository;
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
            throw new IllegalStateException("Impossibile caricare la partita.\n" + e.getMessage());
        }
    }

    private void mostra(Parent contenuto) {
        scena.setRoot(contenuto);
    }

    public void mostraHub(Partita nuova) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}