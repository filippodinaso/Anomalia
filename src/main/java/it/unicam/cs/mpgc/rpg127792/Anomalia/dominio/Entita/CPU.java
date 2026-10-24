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

    //salvataggi
    public static Cpu ripristinata(int massima, int corrente, int rigenerazione) {
        return new Cpu(massima, corrente, rigenerazione);
}


    //restituisce true se la CPU bastava ed è stata consumata (per il giocatore)
    public boolean consuma(int costo){
    if (costo<0){
        throw new IllegalArgumentException("il costo non può essere negativo.");
    }

    if(costo>corrente) {
        return false;

    }

    corrente-=costo;
    return true;

}


    //ritorna i punti tolti (contro il giocatore)
    public int brucia(int quantita) {
    if (quantita < 0) {
        quantita = 0;
    }

    if (quantita > corrente) {
        quantita = corrente;
    }
    
    corrente -= quantita;
    return quantita;
}

    public void rigenera() {
    corrente += rigenerazione;
    
    if (corrente > massima) {
        corrente = massima;
    }
}

    public void ripristina() {
    corrente = massima;
 }

     public void aumentaMassima(int quantita) {
        int incremento = Math.max(0, quantita);
        massima += incremento;
        corrente += incremento;
}

    public void aumentaRigenerazione(int quantita) {
        rigenerazione += Math.max(0, quantita);
}

//GETTER
    public int getMassima() { return massima; }
    public int getCorrente() { return corrente; }
    public int getRigenerazione() { return rigenerazione; }

}