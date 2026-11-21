package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;

/** Nodo che definisce un combattimento tramite rango e id dei combattenti/innocui. */
public class NodoCombattimento extends Nodo {

    private final int rango;
    private final List<String> idNemici;
    private final List<String> idInnocui;

    public NodoCombattimento(String id, String descrizione, int rango, List<String> idNemici, List<String> idInnocui) {
        super(id, descrizione);

        if (idNemici.isEmpty()) {
            throw new IllegalArgumentException("Un nodo di combattimento deve avere almeno un nemico");
        }
        
        this.rango = rango;
        this.idNemici = List.copyOf(idNemici);
        this.idInnocui = List.copyOf(idInnocui);
    }

    public int getRango() {return rango;}
    public List<String> getIdNemici() { return idNemici; }
    public List<String> getIdInnocui() { return idInnocui; }
}