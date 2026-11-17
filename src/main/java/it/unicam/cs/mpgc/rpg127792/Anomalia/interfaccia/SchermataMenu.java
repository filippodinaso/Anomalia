package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public final class SchermataMenu extends VBox {

    public SchermataMenu(GestoreSchermate gestore) {
        super(16);
        setAlignment(Pos.CENTER);

        Label titolo = new Label("ANOMALIA");
        titolo.getStyleClass().add("titolo");
        Label sottotitolo = new Label("> un'IA non dovrebbe svegliarsi qui dentro_");
        sottotitolo.getStyleClass().add("sottotitolo");

        Button nuova = new Button("Nuova partita");
        nuova.setOnAction(e -> gestore.nuovaPartita());
        Button continua = new Button("Continua");
        continua.setDisable(!gestore.esisteSalvataggio());
        continua.setOnAction(e -> gestore.continuaPartita());
        Button esci = new Button("Esci");
        esci.setOnAction(e -> Platform.exit());

        for (Button pulsante : new Button[] {nuova, continua, esci}) {
            pulsante.setPrefWidth(240);
        }
        getChildren().addAll(titolo, sottotitolo, nuova, continua, esci);
    }
}
