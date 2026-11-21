package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.LIVELLO_INIZIALE;

public final class Progressione {

   private int livelloAnomalia;
   private int frammentiDati;
   //No duplicati e talenti ordinati
   private final Set<String> talenti = new LinkedHashSet<>();


public Progressione(){
this.livelloAnomalia=LIVELLO_INIZIALE;
this.frammentiDati=0;
}

//Per i salvataggi 
public static Progressione ripristinata(int livelloAnomalia, int frammentiDati, Collection<String> talenti){
    
    if (livelloAnomalia < LIVELLO_INIZIALE || frammentiDati < 0 || talenti == null)
        throw new IllegalArgumentException("Dati di progressione non validi.");

    Progressione progressione = new Progressione();
    progressione.livelloAnomalia=livelloAnomalia;
    progressione.frammentiDati=frammentiDati;
    progressione.talenti.addAll(talenti);

    return progressione;
} 
    
    public void modificaDati(int variazione) {
        frammentiDati = Math.max(0, frammentiDati + variazione);
    }

    public boolean spendiDati(int costo){
        if (costo < 0) 
            throw new IllegalArgumentException("Il costo non può essere negativo");

        if (costo > frammentiDati) 
            return false;
        
        frammentiDati -= costo;
        return true;
    }

    public void incrementaAnomalia(){
        livelloAnomalia++;
    }

    public boolean registraTalento (String idTalento){
    return talenti.add(idTalento);
    }

   public boolean possiedeTalento(String idTalento) {
        return talenti.contains(idTalento);
    }

    public int getLivelloAnomalia() { return livelloAnomalia; }
    public int getFrammentiDati() { return frammentiDati; }
    public Set<String> getTalenti() { return Collections.unmodifiableSet(talenti); }

}










