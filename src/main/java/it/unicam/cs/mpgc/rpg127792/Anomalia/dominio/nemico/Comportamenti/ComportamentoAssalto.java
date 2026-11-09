package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

//semplice attacco diretto al giocatore
public final class ComportamentoAssalto implements Comportamento {

    private final int danno;

    public ComportamentoAssalto(int danno) {
        this.danno = danno;
    }

    @Override
    public void agisci(Nemico esecutore, Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi) {
        DannoDiretto.attacco(danno).applica(ContestoEffetto.contro(esecutore, giocatore, eventi));
    }
}
