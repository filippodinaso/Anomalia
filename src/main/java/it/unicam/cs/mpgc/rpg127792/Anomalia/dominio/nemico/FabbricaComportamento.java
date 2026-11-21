package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;


// Crea il comportamento del nemico in base al suo livello
@FunctionalInterface
public interface FabbricaComportamento {
    Comportamento crea(int livello);
}