package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;


public interface RegistroAbilità {
/** Lista abilità permanenti*/
    List<Abilità> getArsenaleIniziale();

/** Lista abilità acquistabili*/
    List<CatalogoAbilità> getCatalogoNegozio();
    
/** Cerca un abilità tramite il suo ID. */
    Abilità get(String id);
}
