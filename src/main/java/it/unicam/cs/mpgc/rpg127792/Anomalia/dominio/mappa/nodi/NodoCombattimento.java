package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;

public class NodoCombattimento extends Nodo {

    private final List<String> idNemici;
    private final List<String> idInnocui;

    public NodoCombattimento(String id, String descrizione, List<String> idNemici, List<String> idInnocui) {
        super(id, descrizione);

        if (idNemici.isEmpty()) {
            throw new IllegalArgumentException("Un nodo di combattimento deve avere almeno un nemico");
        }
        
        this.idNemici = List.copyOf(idNemici);
        this.idInnocui = List.copyOf(idInnocui);
    }

    public List<String> getIdNemici() { return idNemici; }
    public List<String> getIdInnocui() { return idInnocui; }
}