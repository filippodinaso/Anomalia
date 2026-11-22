package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Arsenale;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controller.ControllerSafeRoom;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//checkpoint del capitolo: si spendono i Frammenti Dati per Anomalia, talenti e nuove abilità, e si gestisce il deck
public final class SchermataSafeRoom extends BorderPane {

    private static final double LARGHEZZA_DESCRIZIONE = 680;

    private final GestoreSchermate gestore;
    private final ControllerSafeRoom controller;
    private final Giocatore giocatore;

    private final Label risorse = new Label();
    private final Label messaggio = new Label();
    private final VBox schedaAnomalia = new VBox(12);
    private final VBox schedaAbilità = new VBox(12);
    private final VBox schedaDeck = new VBox(10);
    private final Button esci = new Button("Torna alla mappa");

    public SchermataSafeRoom(GestoreSchermate gestore, ControllerSafeRoom controller) {
        this.gestore = gestore;
        this.controller = controller;
        this.giocatore = controller.getGiocatore();

        Label titolo = new Label("SAFE ROOM");
        titolo.getStyleClass().add("titolo");
        VBox alto = new VBox(6, titolo, risorse, messaggio);
        alto.setPadding(new Insets(16));

        TabPane schede = new TabPane(
                creaScheda("Anomalia", schedaAnomalia),
                creaScheda("Abilità", schedaAbilità),
                creaScheda("Deck", schedaDeck));
        esci.setOnAction(e -> gestore.aggiornaHub());
        HBox basso = new HBox(esci);
        basso.setAlignment(Pos.CENTER_RIGHT);
        basso.setPadding(new Insets(16));

        setTop(alto);
        setCenter(schede);
        setBottom(basso);
        aggiorna();
    }

    private Tab creaScheda(String titolo, VBox contenuto) {
        contenuto.setPadding(new Insets(16));
        Tab tab = new Tab(titolo, contenuto);
        tab.setClosable(false);
        return tab;
    }

    private void aggiorna() {
        //finché c'è un talento da scegliere blocchiamo il resto per non far uscire a metà acquisto
        boolean sceltaPendente = !controller.getTalentiProposti().isEmpty();
        risorse.setText("INTEGRITÀ: " + giocatore.getIntegrita() + "/" + giocatore.getIntegritaMassima()
                + "    DATI: " + giocatore.getProgressione().getFrammentiDati()
                + "    ANOMALIA: Lvl " + giocatore.getProgressione().getLivelloAnomalia());
        ricostruisciAnomalia(sceltaPendente);
        ricostruisciAbilità(sceltaPendente);
        ricostruisciDeck(sceltaPendente);
        esci.setDisable(sceltaPendente);
    }

    private void ricostruisciAnomalia(boolean sceltaPendente) {
        schedaAnomalia.getChildren().clear();
        if (sceltaPendente) {
            schedaAnomalia.getChildren().add(new Label("Scegli un talento:"));
            for (Talento talento : controller.getTalentiProposti()) {
                Button scelta = new Button(talento.nome() + "  -  " + talento.descrizione());
                scelta.setMaxWidth(Double.MAX_VALUE);
                scelta.setOnAction(e -> {
                    if (controller.scegliTalento(talento)) {
                        messaggio.setText("Talento appreso: " + talento.nome());
                        gestore.salvaPartitaCorrente();
                    }
                    aggiorna();
                });
                schedaAnomalia.getChildren().add(scelta);
            }
            return;
        }
        int costo = controller.getCostoProssimaAnomalia();
        Button sali = new Button("Sali di Anomalia (" + costo + " DATI)");
        sali.setDisable(giocatore.getProgressione().getFrammentiDati() < costo);
        sali.setOnAction(e -> {
            if (controller.compraLivelloAnomalia()) {
                messaggio.setText("Livello di Anomalia aumentato.");
                if (controller.getTalentiProposti().isEmpty()) {
                    gestore.salvaPartitaCorrente();
                }
            }
            aggiorna();
        });
        schedaAnomalia.getChildren().addAll(
                new Label("Salendo di Anomalia scegli un talento, ma anche i nemici diventano più forti."), sali);
    }

    private void ricostruisciAbilità(boolean sceltaPendente) {
        schedaAbilità.getChildren().clear();
        List<CatalogoAbilità> catalogo = controller.getCatalogoDisponibile();
        if (catalogo.isEmpty()) {
            schedaAbilità.getChildren().add(new Label("Hai già tutte le abilità disponibili."));
            return;
        }
        for (CatalogoAbilità voce : catalogo) {
            Abilità abilità = voce.abilità();
            Label testo = new Label(abilità.nome() + "   [CPU " + abilità.costoCpu() + ", ricarica " + abilità.ricarica()
                    + "]\n" + abilità.descrizione());
            testo.setPrefWidth(LARGHEZZA_DESCRIZIONE);
            testo.setWrapText(true);

            Button compra = new Button("Acquista (" + voce.costo() + " DATI)");
            compra.setDisable(sceltaPendente || giocatore.getProgressione().getFrammentiDati() < voce.costo());
            compra.setOnAction(e -> {
                if (controller.compraAbilità(voce)) {
                    messaggio.setText("Abilità acquistata: " + abilità.nome());
                    gestore.salvaPartitaCorrente();
                }
                aggiorna();
            });
            HBox riga = new HBox(12, testo, compra);
            riga.setAlignment(Pos.CENTER_LEFT);
            schedaAbilità.getChildren().add(riga);
        }
    }

    private void ricostruisciDeck(boolean sceltaPendente) {
        Arsenale arsenale = giocatore.getArsenale();
        int inDeck = arsenale.getDeck().size();
        schedaDeck.getChildren().clear();
        schedaDeck.getChildren().add(new Label("Deck: " + inDeck + "/" + arsenale.getDimensioneDeck()
                + "  (le abilità fuori dal deck non si possono usare in combattimento)"));
        for (Abilità abilità : controller.getAbilitàSbloccate()) {
            boolean equipaggiata = controller.isEquipaggiata(abilità.id());
            Label testo = new Label(abilità.nome() + "   " + abilità.descrizione());
            testo.setPrefWidth(LARGHEZZA_DESCRIZIONE);
            testo.setWrapText(true);

            Button cambia = new Button(equipaggiata ? "Rimuovi" : "Equipaggia");
            //il deck non può restare vuoto né superare la dimensione massima
            cambia.setDisable(sceltaPendente
                    || (equipaggiata && inDeck <= 1)
                    || (!equipaggiata && inDeck >= arsenale.getDimensioneDeck()));
            cambia.setOnAction(e -> {
                controller.cambiaEquipaggiamento(abilità.id());
                gestore.salvaPartitaCorrente();
                aggiorna();
            });
            HBox riga = new HBox(12, testo, cambia);
            riga.setAlignment(Pos.CENTER_LEFT);
            schedaDeck.getChildren().add(riga);
        }
    }
}
