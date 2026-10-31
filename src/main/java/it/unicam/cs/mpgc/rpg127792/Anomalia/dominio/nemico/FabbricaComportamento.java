package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;


//Calcoliamo il danno delle strategy dei mob una sola volta prima dello scontro basandoci sul sistema di scaling (livelli del nemico)
@FunctionalInterface
public interface FabbricaComportamento {
    Comportamento crea();
}