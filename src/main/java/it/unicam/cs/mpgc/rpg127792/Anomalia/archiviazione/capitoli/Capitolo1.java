package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.capitoli;


import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.DefinizioneCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.MappaCapitolo;

public final class Capitolo1 implements DefinizioneCapitolo {

    public static final String ID = "CAP_01";

    @Override
    public String getId() {
        return ID;
    }    

    @Override
    public MappaCapitolo crea() {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO
    }
    

}

