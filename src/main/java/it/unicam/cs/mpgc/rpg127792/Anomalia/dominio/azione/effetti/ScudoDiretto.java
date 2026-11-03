package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScudoApplicato;


public final class ScudoDiretto implements Effetto {

    private final int quantita;

    public ScudoDiretto(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("Lo scudo non può essere negativo");
        }
        this.quantita = quantita;
    }

    @Override
    public void applica(ContestoEffetto contesto) {
        for (Combattente bersaglio : contesto.bersagli()) {
            bersaglio.aggiungiScudo(quantita);
            contesto.eventi().pubblica(new ScudoApplicato(bersaglio.getNome(), quantita));
        }
    }

    @Override
    public String descrizione() {
        return "+" + quantita + " SCUDO";
    }
}
