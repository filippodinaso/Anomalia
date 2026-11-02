package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;

public final class ModificaDati implements Effetto {

    private final int variazione;

    public ModificaDati(int variazione){
        this.variazione=variazione;
    }

    @Override 
    public void applica(ContestoEffetto contesto){
        for(Combattente bersaglio : contesto.bersagli()){
            if(bersaglio instanceof Giocatore giocatore){
                giocatore.getProgressione().modificaDati(variazione);
                break;          
            }
        }
    }

    @Override
    public String descrizione() {
        return (variazione >= 0 ? "+" : "") + variazione + " DATI";
    }

    
}
