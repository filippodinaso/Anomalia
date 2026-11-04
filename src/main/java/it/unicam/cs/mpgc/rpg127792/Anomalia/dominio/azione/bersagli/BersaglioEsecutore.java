package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Bersaglio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

public class BersaglioEsecutore implements Bersaglio  {

    @Override 
    public boolean richiedeScelta(){
        return false;
    }

    @Override
    public List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<? extends Combattente> nemici) {
        return List.of(esecutore);
    }

    @Override
    public String descrizione() {
        return "TODO";
    }
}


