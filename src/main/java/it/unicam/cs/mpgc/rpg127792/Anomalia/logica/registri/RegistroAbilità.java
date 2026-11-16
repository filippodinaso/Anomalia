package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;

public interface RegistroAbilità {

    List<Abilità> getArsenaleIniziale();
    
    List<CatalogoAbilità> getCatalogoNegozio();
    
    Abilità get(String id);
}
