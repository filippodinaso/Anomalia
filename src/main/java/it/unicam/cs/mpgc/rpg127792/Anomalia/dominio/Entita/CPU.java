package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita;


//CPU==stamina, risorsa consumata dall'attivazione delle abilità del giocatore

public final class Cpu {

    private int massima;           //cpu massima
    private int corrente;          
    private int rigenerazione;     //ogni turno ripristina una parte della cpu

    public Cpu(int massima, int corrente, int rigenerazione) {
      
     if(massima<=0 || rigenerazione < 0){
        throw new IllegalArgumentException("Valori non validi."); 
     }

    this.massima=massima;
    this.rigenerazione=rigenerazione;
    this.corrente=massima;
}

}