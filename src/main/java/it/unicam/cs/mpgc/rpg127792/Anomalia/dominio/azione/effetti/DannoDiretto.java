package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.RisultatoDanno;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.DannoInflitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;


public final class DannoDiretto implements Effetto {

    private final int danno;
    private final boolean conBonus;

    private DannoDiretto(int danno, boolean conBonus) {
        if (danno < 0) 
            throw new IllegalArgumentException("Il danno non può essere negativo.");
        
        this.danno = danno;
        this.conBonus = conBonus;
    }

    // al danno base si somma il bonus dell'esecutore.
    public static DannoDiretto attacco(int danno) {
        return new DannoDiretto(danno, true);
    }

    @Override
    public void applica(ContestoEffetto contesto) {
        int bonus = 0;

        if (conBonus && contesto.esecutore() instanceof Giocatore giocatore) {
            bonus = giocatore.getBonus().getDanniExtra();
        }

        int totale = danno + bonus;

        for (Combattente bersaglio : contesto.bersagli()) {
            RisultatoDanno risultato = bersaglio.subisciDanni(totale);
            contesto.eventi().pubblica(new DannoInflitto(
                                       contesto.esecutore().getNome(),  
                                       bersaglio.getNome(), 
                                       risultato.integritaPersa(), 
                                       risultato.scudoAssorbito()));
        }
    }

    @Override
    public String descrizione() {
        return conBonus ? "Danno " + danno : "-" + danno + " INTEGRITÀ";
    }
}
