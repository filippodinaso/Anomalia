package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.comportamenti;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.BruciaCpu;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

// Attacca il giocatore e gli brucia anche una parte della CPU.
public final class ComportamentoSabotaggio implements Comportamento {

    private final int danno;
    private final int cpuBruciata;

    public ComportamentoSabotaggio(int danno, int cpuBruciata) {
        if (danno < 0 || cpuBruciata < 0) 
             throw new IllegalArgumentException("Danno e CPU bruciata non possono essere negativi.");

        this.danno = danno;
        this.cpuBruciata = cpuBruciata;
    }

    @Override
    public void agisci(Nemico esecutore, Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi) {
        ContestoEffetto contesto = ContestoEffetto.contro(esecutore, giocatore, eventi);
        DannoDiretto.attacco(danno).applica(contesto);
        new BruciaCpu(cpuBruciata).applica(contesto);
    }
}