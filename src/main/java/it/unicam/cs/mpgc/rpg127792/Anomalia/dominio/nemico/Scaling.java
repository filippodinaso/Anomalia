package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;


/**
 * Scaling
 * @param base valore livello 0
 * @param perLivello di quanto aumenta base per ogni livello
 */
public record Scaling(int base, int perLivello) {

    public int valore(int livello) {
        return base + perLivello * livello;
    }
}
