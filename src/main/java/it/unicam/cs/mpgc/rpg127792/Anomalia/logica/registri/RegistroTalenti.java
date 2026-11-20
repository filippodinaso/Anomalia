package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;

public interface RegistroTalenti {

/** Restituisce tutti i talenti disponibili. */
    List<Talento> getTutti();

/** Cerca un talento tramite il suo ID. */
    Talento get(String id);

}
