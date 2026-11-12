package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ScudoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

public class ComportamentoBoss implements Comportamento{

    private final int danno;
    private final int scudo;
    private final int turniPerScudo;

    private int turniTrascorsi;

    public ComportamentoBoss(int danno, int scudo, int turniPerScudo) {
        this.danno = danno;
        this.scudo = scudo;
        this.turniPerScudo = turniPerScudo;
    }

    @Override
    public void agisci(Nemico esecutore, Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi) {

        int dannoAttuale = danno;

        //Se scende sotto il 50% l'integrità il boss raddoppia il danno
        if (esecutore.getIntegrita() * 2 <= esecutore.getIntegritaMassima()) {
            dannoAttuale *= 2;
        }

        //Attacca ogni turno
        DannoDiretto.attacco(dannoAttuale).applica(ContestoEffetto.contro(esecutore, giocatore, eventi));

        //Genera scudo in base a turniPerScudo (che passiamo quando creiamo il boss)
        turniTrascorsi++;

        if (turniTrascorsi >= turniPerScudo) {
            new ScudoDiretto(scudo).applica(ContestoEffetto.suSeStesso(esecutore, eventi));

            turniTrascorsi = 0;
        }
    }
}