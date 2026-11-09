package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.CpuBruciata;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;

public final class BruciaCpu implements Effetto {

    private final int quantita;

    public BruciaCpu(int quantita) {
        if (quantita < 0) {
            throw new IllegalArgumentException("La quantità non può essere negativa");
        }
        this.quantita = quantita;
    }

    @Override
    public void applica(ContestoEffetto contesto) {
        for (Combattente bersaglio : contesto.bersagli()) {
            //ricaviamo il giocatore nella lista bersagli del contesto
            if (bersaglio instanceof Giocatore giocatore) {
                int bruciata = giocatore.getCpu().brucia(quantita);
                if (bruciata > 0) {
                    contesto.eventi().pubblica(new CpuBruciata(giocatore.getNome(), bruciata));
                }
            }
        }
    }

    @Override
    public String descrizione() {
        return "-" + quantita + " CPU";
    }
}