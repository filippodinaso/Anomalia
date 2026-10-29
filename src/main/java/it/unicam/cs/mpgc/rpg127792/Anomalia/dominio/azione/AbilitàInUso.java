package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;


public final class AbilitàInUso {

   private final Abilità definizione;
   private int ricaricaRimanente;


   public AbilitàInUso(Abilità definizione) {
        if(definizione==null) throw new IllegalArgumentException("L'abilità non è definita.");
        this.definizione=definizione;
   }

//metodi
   public boolean isPronta(){
    return ricaricaRimanente==0;
   }

   public void avviaRicarica(){
    ricaricaRimanente = definizione.ricarica();
   }

   public void avanzaTurno(){
    ricaricaRimanente--;
   }

   public void azzera(){
    ricaricaRimanente=0;
   }

//getter
   public Abilità getDefinizione(){ return definizione; }
   public int getRicaricaRimanente(){ return ricaricaRimanente; }
}
