package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

public interface RepositoryPartite {

/** Controlla se esiste un salvataggio. */
    boolean esiste();

 /**
 *  Salva lo stato della partita.
 *
 *  @param stato stato da salvare
 */
    void salva(StatoPartita stato);

 /**
 *  Carica lo stato della partita.
 *
 *  @return stato salvato
 *  @throws IllegalStateException se il salvataggio non esiste o non è valido
 */
    StatoPartita carica();
}