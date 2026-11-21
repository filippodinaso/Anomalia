package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.IstantaneaGiocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.StatoCapitolo;

/** Composto dalla fotografia del giocatore e da quella del capitolo per riprendere la partita */
public record StatoPartita(IstantaneaGiocatore giocatore, StatoCapitolo capitolo) {}