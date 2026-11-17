package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

public interface RepositoryPartite {

    boolean esiste();

    void salva(StatoPartita stato);

    StatoPartita carica();
}