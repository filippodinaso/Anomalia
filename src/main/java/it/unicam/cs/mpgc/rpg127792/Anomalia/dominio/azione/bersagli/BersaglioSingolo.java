package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Bersaglio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

public class BersaglioSingolo implements Bersaglio {


    @Override
     public boolean richiedeScelta(){
     return true;
    }

    @Override
    public List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<? extends Combattente> nemici) {
        if (scelto == null) 
            throw new IllegalArgumentException("Serve un bersaglio.");

        return List.of(scelto);
    }

    @Override
    public String descrizione() {
        return "TODO";
    }
}
