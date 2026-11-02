package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

public final class ModificaStatistica implements Effetto {

    private final Statistica statistica;
    private final int valore;

    public ModificaStatistica(Statistica statistica, int valore) {
        if(statistica == null) 
            throw new IllegalArgumentException("Statistica non può essere null.");
        
        this.valore = valore;
        this.statistica= statistica;
    }

    @Override
    public void applica(ContestoEffetto contesto) {
        //Cerchiamo il giocatore e applichiamo la modifica
        for (Combattente bersaglio : contesto.bersagli()) {
            if (bersaglio instanceof Giocatore giocatore) {
                statistica.applica(giocatore, valore);
                break;
            }
        }
    }

    @Override
    public String descrizione() {
        
        return (valore >= 0 ? "+" : "") + valore + " " + statistica.getEtichetta();
    }
}