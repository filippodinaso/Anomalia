package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.IstantaneaGiocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.StatoCapitolo;

public record StatoPartita(IstantaneaGiocatore giocatore, StatoCapitolo capitolo) {
}