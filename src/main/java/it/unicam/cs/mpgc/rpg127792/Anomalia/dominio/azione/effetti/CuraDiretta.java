package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.IntegritaRipristinata;


public final class CuraDiretta implements Effetto {

    private final int quantita;

    public CuraDiretta(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("La cura non può essere negativa");
        }
        this.quantita = quantita;
    }

    @Override
    public void applica(ContestoEffetto contesto) {
        for (Combattente bersaglio : contesto.bersagli()) {
            int ripristinata = bersaglio.ripristinaIntegrita(quantita);
            if (ripristinata > 0) {
                contesto.eventi().pubblica(new IntegritaRipristinata(bersaglio.getNome(), ripristinata));
            }
        }
    }

    @Override
    public String descrizione() {
        return "+" + quantita + " INTEGRITÀ";
    }
}
