package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controller;

import java.util.List;
import java.util.Optional;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.AbilitàInUso;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.ProcessoInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.FabbricaScontri;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.Scontro;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro.StatoScontro;

/** Coordina le azioni dello scontro e aggiorna la partita quando termina. */
public final class ControllerScontro {
    private final Partita partita;
    private final NodoCombattimento nodo;
    private final Scontro scontro;

    public ControllerScontro(Partita partita, NodoCombattimento nodo, FabbricaScontri fabbrica, Pubblicatore<EventoDiGioco> eventi) {
        this.partita = partita;
        this.nodo = nodo;
        this.scontro = fabbrica.crea(partita.getGiocatore(), nodo, eventi);
        this.scontro.avvia();
    }

    public Giocatore getGiocatore() {
        return partita.getGiocatore();
    }

    public StatoScontro getStato() {
        return scontro.getStato();
    }

    public int getTurno() {
        return scontro.getTurno();
    }

    public List<Nemico> getNemici() {
        return scontro.getNemici();
    }

    public List<ProcessoInnocuo> getInnocui() {
        return scontro.getInnocui();
    }

    public boolean usaAbilità(AbilitàInUso abilità, Optional<Nemico> bersaglio) {
        return scontro.usaAbilità(abilità, bersaglio.orElse(null));
    }

    public boolean assorbi(ProcessoInnocuo innocuo) {
        return scontro.assorbi(innocuo);
    }

    public boolean elimina(ProcessoInnocuo innocuo) {
        return scontro.elimina(innocuo);
    }

    public void terminaTurno() {
        scontro.terminaTurno();
    }

    public void confermaVittoria() {
        if (scontro.getStato() != StatoScontro.VITTORIA) 
            throw new IllegalStateException("Lo scontro non è stato vinto");
        
        partita.vittoria(nodo);
    }

    public void ripartiDalCheckpoint() {
        if (scontro.getStato() != StatoScontro.SCONFITTA) 
            throw new IllegalStateException("Il giocatore non è stato sconfitto");
        
        partita.sconfitta();
    }
}
