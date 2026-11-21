package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.AbilitàInUso;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.ProcessoInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controllori.ControllerScontro;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.StatoScontro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/** Schermata del combattimento: stato scontro e azioni disponibili. */ 
public final class SchermataArena extends BorderPane {

    private final GestoreSchermate gestore;
    private final ControllerScontro controller;

    private final HBox barraSuperiore = new HBox(35);
    private final TextArea logRete = new TextArea();
    private final VBox listaNemici = new VBox(10);
    private final VBox listaInnocui = new VBox(10);
    private final HBox barraBottoniComandi = new HBox(10);
    private Nemico selezionato;

/**
 *  Crea la schermata e collega gli eventi dello scontro al log.
 *
 *  @param gestore gestore delle schermate (per tornare all'hub)
 *  @param creaController funzione che crea il controller collegandolo al log
 */
    public SchermataArena(GestoreSchermate gestore, Function<Consumer<String>, ControllerScontro> creaController) {
        this.gestore = gestore;

        Consumer<String> aggiungiAlLog = testo -> logRete.appendText(testo + "\n");

        this.controller = creaController.apply(aggiungiAlLog);
        this.selezionato = controller.getNemici().getFirst();

        setTop(creaBarraSuperiore());
        setCenter(creaSezioneLog());
        setRight(creaPannelloProcessi());
        setBottom(creaConsoleComandi());

        aggiorna();
    }

//  Prepara il contenitore della barra superiore
    private HBox creaBarraSuperiore() {
        barraSuperiore.setAlignment(Pos.CENTER_LEFT);
        barraSuperiore.getStyleClass().add("barra-statistiche");
        return barraSuperiore;
    }

//  Area centrale con il titolo e il riquadro del terminale
    private VBox creaSezioneLog() {
    Label titoloLog = new Label("> LOG DI RETE");
    titoloLog.getStyleClass().add("titolo-sezione");

    logRete.setEditable(false);
    logRete.setWrapText(true);
    logRete.getStyleClass().add("log-rete");
    logRete.setMinHeight(0);
    logRete.setPrefHeight(0);
    VBox.setVgrow(logRete, Priority.ALWAYS);

    VBox sezioneLog = new VBox(10, titoloLog, logRete);
    sezioneLog.setPadding(new Insets(14, 10, 14, 16));
    return sezioneLog;
}

//  Pannello verde a destra con scorrimento interno per nemici e processi innocui
    private VBox creaPannelloProcessi() {
        Label titoloProcessi = new Label("> PROCESSI RILEVATI");
        titoloProcessi.getStyleClass().add("titolo-processi");

        VBox contenutoScorrevole = new VBox(10, listaNemici, listaInnocui);
        contenutoScorrevole.setPadding(new Insets(2, 4, 2, 0));

        ScrollPane scroll = new ScrollPane(contenutoScorrevole);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setMinHeight(0);
        scroll.setPrefHeight(0);
        scroll.getStyleClass().add("scroll-processi");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox contenitoreVerde = new VBox(12, titoloProcessi, scroll);
        contenitoreVerde.setPrefWidth(305);
        contenitoreVerde.setMinHeight(0);
        contenitoreVerde.setPrefHeight(0);
        contenitoreVerde.getStyleClass().add("pannello-processi");
        VBox.setVgrow(contenitoreVerde, Priority.ALWAYS);

        VBox wrapperDestro = new VBox(contenitoreVerde);
        wrapperDestro.setMinHeight(0);
        wrapperDestro.setPrefHeight(0);
        wrapperDestro.setPadding(new Insets(14, 16, 14, 8));
        return wrapperDestro;
    }

//  Barra inferiore con la linea verde sopra, il titolo e i pulsanti delle abilità affiancati
    private VBox creaConsoleComandi() {
        Label titoloConsole = new Label("> CONSOLE DEI COMANDI");
        titoloConsole.getStyleClass().add("titolo-sezione");

        barraBottoniComandi.setAlignment(Pos.CENTER_LEFT);

        VBox console = new VBox(10, titoloConsole, barraBottoniComandi);
        console.getStyleClass().add("barra-inferiore");
        return console;
    }

//  Aggiorna tutti i pannelli dopo ogni azione o fine turno
    private void aggiorna() {
        boolean turnoGiocatore = controller.getStato() == StatoScontro.TURNO_GIOCATORE;
        aggiornaBarraSuperiore();
        aggiornaNemici();
        aggiornaInnocui(turnoGiocatore);
        aggiornaComandi(turnoGiocatore);
    }

/**
 *  Restituisce il nemico selezionato o il primo rimasto se quello selezionato non è più presente
 *
 *  @return bersaglio disponibile o vuoto se non ci sono nemici
 */
    private Optional<Nemico> bersaglio() {
        List<Nemico> nemici = controller.getNemici();
        if (nemici.isEmpty()) {
            return Optional.empty();
        }
        if (nemici.contains(selezionato)) {
            return Optional.of(selezionato);
        }
        return Optional.of(nemici.getFirst());
    }

    private void aggiornaBarraSuperiore() {
        Giocatore giocatore = controller.getGiocatore();

        Label nome = new Label("[ " + giocatore.getNome().toUpperCase() + " ]");
        nome.getStyleClass().add("nome-giocatore");

        Label integrita = new Label("INTEGRITÀ: " + giocatore.getIntegrita() + "/" + giocatore.getIntegritaMassima());

        Label cpu = new Label("CPU: " + giocatore.getCpu().getCorrente() + "/" + giocatore.getCpu().getMassima());
        cpu.getStyleClass().add("stat-cpu");

        Label turno = new Label("TURNO: " + controller.getTurno());
        turno.getStyleClass().add("dettagli-combattimento");

        barraSuperiore.getChildren().setAll(nome, integrita, cpu, turno);
    }

/** Ricrea le schede dei nemici e permette di selezionare il bersaglio. */
    private void aggiornaNemici() {
        listaNemici.getChildren().clear();
        List<Nemico> nemici = controller.getNemici();
        if (nemici.isEmpty()) {
            return;
        }
        Nemico scelto = bersaglio().orElse(null);
        for (Nemico nemico : nemici) {
            Label nomeNemico = new Label(nemico.getNome() + "  LV " + nemico.getLivello());
            nomeNemico.getStyleClass().add("nome-nemico");
            nomeNemico.setWrapText(true);

            double percentuale = nemico.getIntegritaMassima() == 0
                    ? 0
                    : (double) nemico.getIntegrita() / nemico.getIntegritaMassima();
            ProgressBar barraHp = new ProgressBar(percentuale);
            barraHp.setMaxWidth(Double.MAX_VALUE);
            barraHp.setMinHeight(16);
            barraHp.setPrefHeight(16);

            String testoHp = "HP " + nemico.getIntegrita() + "/" + nemico.getIntegritaMassima()
                    + (nemico.getScudo() > 0 ? "   SCUDO " + nemico.getScudo() : "");
            Label labelHp = new Label(testoHp);
            labelHp.getStyleClass().add("dettagli-combattimento");

            VBox scheda = new VBox(6, nomeNemico, barraHp, labelHp);
            scheda.getStyleClass().add("scheda-nemico");
            if (nemico == scelto) {
                scheda.getStyleClass().add("selezionato");
            }
            scheda.setOnMouseClicked(e -> {
                selezionato = nemico;
                aggiorna();
            });
            listaNemici.getChildren().add(scheda);
        }
    }

/** Ricrea le schede dei processi non gestiti e abilita le azioni solo nel turno del giocatore.*/    
    private void aggiornaInnocui(boolean turnoGiocatore) {
        listaInnocui.getChildren().clear();
        for (ProcessoInnocuo processo : controller.getInnocui()) {
            if (processo.isGestito()) {
                continue;
            }
            Label titolo = new Label(processo.getNome() + " (decade tra " + processo.getTurniRimanenti() + ")");
            titolo.getStyleClass().add("nome-innocuo");
            titolo.setWrapText(true);
            titolo.setMinHeight(Region.USE_PREF_SIZE);

            Button assorbi = new Button("ASSORBI  " + processo.getAnteprimaAssorbi());
            assorbi.setMaxWidth(Double.MAX_VALUE);
            assorbi.setWrapText(true);
            assorbi.setMinHeight(Region.USE_PREF_SIZE);
            assorbi.setTextAlignment(TextAlignment.CENTER);
            assorbi.setDisable(!turnoGiocatore);
            assorbi.setOnAction(e -> {
                controller.assorbi(processo);
                aggiorna();
            });

            Button elimina = new Button("ELIMINA  " + processo.getAnteprimaElimina());
            elimina.setMaxWidth(Double.MAX_VALUE);
            elimina.setWrapText(true);
            elimina.setMinHeight(Region.USE_PREF_SIZE);
            elimina.setTextAlignment(TextAlignment.CENTER);
            elimina.setDisable(!turnoGiocatore);
            elimina.setOnAction(e -> {
                controller.elimina(processo);
                aggiorna();
            });

            VBox schedaInnocuo = new VBox(8, titolo, assorbi, elimina);
            schedaInnocuo.getStyleClass().add("scheda-innocuo");
            listaInnocui.getChildren().add(schedaInnocuo);
        }
    }

/** Ricreiamo i comandi in base alla fase dello scontro.*/
    private void aggiornaComandi(boolean turnoGiocatore) {
        barraBottoniComandi.getChildren().clear();

        Giocatore giocatore = controller.getGiocatore();
        switch (controller.getStato()) {
            case TURNO_GIOCATORE -> {
                for (AbilitàInUso abilità : giocatore.getArsenale().getDeck()) {
                    Button pulsante = creaPulsanteAbilità(abilità, giocatore, turnoGiocatore);
                    HBox.setHgrow(pulsante, Priority.ALWAYS);
                    barraBottoniComandi.getChildren().add(pulsante);
                }
                Button passaTurno = new Button("PASSA TURNO");
                passaTurno.getStyleClass().add("bottone-passa");
                passaTurno.setMinWidth(135);
                passaTurno.setPrefHeight(50);
                passaTurno.setOnAction(e -> {
                    controller.terminaTurno();
                    aggiorna();
                });
                barraBottoniComandi.getChildren().add(passaTurno);
            }
            case VITTORIA -> {
                Button continua = new Button("> CONTINUA");
                continua.setOnAction(e -> {
                    controller.confermaVittoria();
                    gestore.aggiornaHub();
                });
                barraBottoniComandi.getChildren().addAll(new Label("SCONTRO SUPERATO"), continua);
            }
            case SCONFITTA -> {
                Button riparti = new Button("> RIPARTI DAL CHECKPOINT");
                riparti.setOnAction(e -> {
                    controller.ripartiDalCheckpoint();
                    gestore.aggiornaHub();
                });
                barraBottoniComandi.getChildren().addAll(new Label("SISTEMA COMPROMESSO"), riparti);
            }
        }
    }

/** Crea il pulsante di un'abilità e lo disabilita se non è utilizzabile */
    private Button creaPulsanteAbilità(AbilitàInUso abilità, Giocatore giocatore, boolean turnoGiocatore) {
        Abilità def = abilità.getDefinizione();
        String statoRicarica = abilità.isPronta() ? "PRONTA" : "Ricarica: " + abilità.getRicaricaRimanente();
        String testo = def.nome() + "\nCPU " + def.costoCpu() + " | " + statoRicarica;

        Button pulsante = new Button(testo);
        pulsante.setMaxWidth(Double.MAX_VALUE);
        pulsante.setPrefHeight(50);
        pulsante.setTextAlignment(TextAlignment.CENTER);
        pulsante.getStyleClass().add("bottone-abilita");
        pulsante.setDisable(!turnoGiocatore || !abilità.isPronta() || giocatore.getCpu().getCorrente() < def.costoCpu());
        pulsante.setOnAction(e -> {
            controller.usaAbilità(abilità, bersaglio());
            aggiorna();
        });
        return pulsante;
    }
}
