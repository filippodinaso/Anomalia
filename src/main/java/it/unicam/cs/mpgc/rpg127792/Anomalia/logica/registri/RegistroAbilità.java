package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;

public interface RegistroAbilità {

    List<Abilità> getArsenaleIniziale();
    
    Abilità get(String id);
}
