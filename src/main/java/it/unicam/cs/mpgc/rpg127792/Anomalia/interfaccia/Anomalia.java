package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import java.io.File;

import it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.capitoli.Capitolo1;
import it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiDiBase;
import it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.persistenza.RepositoryPartiteJson;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controller.ControllerPartita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RepositorioContenuti;
import javafx.application.Application;
import javafx.stage.Stage;

public class Anomalia extends Application {

    @Override
    public void start(Stage finestra) {
        RepositorioContenuti contenuti = new ContenutiDiBase();
        RegistroCapitoli capitoli = new RegistroCapitoli().registra(new Capitolo1());
        RepositoryPartite repository = new RepositoryPartiteJson(new File("salvataggio.json"));

        ControllerPartita controllerPartita = new ControllerPartita(capitoli, contenuti.abilità(), repository);
        GestoreSchermate gestore = new GestoreSchermate(finestra, contenuti, controllerPartita);
        finestra.setTitle("Anomalia");
        finestra.setResizable(false);
        gestore.mostraMenu();
        finestra.centerOnScreen();
        finestra.show();
    }

     public static void main(String[] args) {
        launch(args);
   }
}
