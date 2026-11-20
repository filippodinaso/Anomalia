package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.DefinizioneInnocuo;

public interface RegistroInnocui {
/** Cerca un processo innocuo tramite il suo ID. */
    DefinizioneInnocuo get(String id);
}