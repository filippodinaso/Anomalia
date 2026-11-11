package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa;

public abstract class Nodo {

    private final String id;
    private final String descrizione;

    protected Nodo(String id, String descrizione) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Il nodo deve avere un id");
        }
        this.id = id;
        this.descrizione = descrizione == null ? "" : descrizione;
    }

    // True se entrare nel nodo registra un punto di respawn. 
    public boolean isPuntoDiSalvataggio() {
        return false;
    }

    // True se il nodo può essere rivisitato anche dopo averlo completato.
    public boolean isRipetibile() {
        return false;
    }

    public String getId() { return id; }
    public String getDescrizione() { return descrizione; }
}
