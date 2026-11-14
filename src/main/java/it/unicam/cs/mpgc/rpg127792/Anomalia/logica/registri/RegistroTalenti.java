package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;

public interface RegistroTalenti {
    
       List<Talento> getTutti();

       Talento get(String id);

}
