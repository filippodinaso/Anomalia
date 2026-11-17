package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;

public class NodoCheckpoint extends Nodo {

    public NodoCheckpoint(String id, String descrizione) {
        super(id, descrizione);
    }

    @Override
    public boolean isPuntoDiSalvataggio() {
        return true;
    }

    @Override 
    public void allIngresso(Giocatore giocatore){
        giocatore.ripristinaIntegrita(giocatore.getIntegritaMassima());
    }

    @Override
    public boolean isRipetibile() {
        return true;
    }
}