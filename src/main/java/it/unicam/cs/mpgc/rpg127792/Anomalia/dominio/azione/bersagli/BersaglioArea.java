package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Bersaglio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

public class BersaglioArea implements Bersaglio  {

    private int massimo;

    public BersaglioArea(int massimo){
        if(massimo<=0)
            throw new IllegalArgumentException("Il numero dei bersagli deve essere positivo.");
        this.massimo=massimo;
    }

    @Override 
    public boolean richiedeScelta(){
        return false;
    }

    @Override
    public List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<? extends Combattente> nemici) {
         //creiamo una copia per sistemare il tipo prima di stream 
        List<Combattente> copia = new ArrayList<>(nemici);
        return copia.stream().limit(massimo).toList();
    }

    @Override
    public String descrizione(){
        return "TODO";
    }
}
