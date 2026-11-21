package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controllori;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.Negozio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroTalenti;

/** Coordina gli acquisti e la gestione del deck nella Safe Room. */
public final class ControllerSafeRoom {
    private final Giocatore giocatore;
    private final Negozio negozio;

    public ControllerSafeRoom(Partita partita, RegistroAbilità registroAbilità, RegistroTalenti registroTalenti) {
        this.giocatore = partita.getGiocatore();
        this.negozio = new Negozio(giocatore, registroAbilità, registroTalenti);
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public List<Talento> getTalentiProposti() {
        return negozio.getTalentiProposti();
    }

    public int getCostoProssimaAnomalia() {
        return negozio.getCostoProssimaAnomalia();
    }

    public List<CatalogoAbilità> getCatalogoDisponibile() {
        return negozio.getCatalogoDisponibile();
    }

    public List<Abilità> getAbilitàSbloccate() {
        return giocatore.getArsenale().getZaino();
    }

    public boolean isEquipaggiata(String id) {
        return giocatore.getArsenale().isEquipaggiata(id);
    }

    public boolean compraLivelloAnomalia() {
        return negozio.compraLivelloAnomalia();
    }

    public boolean scegliTalento(Talento talento) {
        return negozio.scegliTalento(talento);
    }

    public boolean compraAbilità(CatalogoAbilità voce) {
        return negozio.compraAbilità(voce);
    }

    public boolean cambiaEquipaggiamento(String id) {
        if (isEquipaggiata(id)) 
            return giocatore.getArsenale().rimuoviDalDeck(id);
        
        return giocatore.getArsenale().equipaggia(id);
    }
}
