package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

//tipi di nemico del gioco

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.DefinizioneNemico;

public interface RegistroNemici {

    DefinizioneNemico get(String id);

    // Il nemico generato quando un processo innocuo decade. 
    DefinizioneNemico getPunitivoPerDecadimentoInnocuo();
}