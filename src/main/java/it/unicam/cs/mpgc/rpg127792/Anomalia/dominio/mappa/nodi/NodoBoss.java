package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi;

import java.util.List;

//Nodo per eventuale Boss (magari da inserire a fine capitolo)
//TODO
public class NodoBoss extends NodoCombattimento {

    public NodoBoss(String id, String descrizione, List<String> idNemici, List<String> idInnocui) {
        super(id, descrizione, idNemici, idInnocui);
    }
}