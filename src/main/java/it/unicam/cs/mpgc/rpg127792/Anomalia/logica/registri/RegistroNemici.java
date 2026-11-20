package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

//tipi di nemico del gioco

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.DefinizioneNemico;

public interface RegistroNemici {
/** Cerca un nemico tramite il suo ID. */
    DefinizioneNemico get(String id);

}