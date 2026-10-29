package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;

public final class Nemico extends Combattente {
    
     //i nemici avranno un sistema di scaling per mantere equilibrata la difficoltà
    private final int livello;
    
     //il drop dei nemici (la moneta del gioco)
    private final int frammentiDati;
    
    /*ogni tipologia di nemico implementerà una strategy che ne descriverà il comportamento
    oppure una stessa tipologia di nemico potrebbe usare più strategy */
    private final Comportamento comportamento;


    public Nemico(String nome, int livello, int integritaMassima, int frammentiDati, Comportamento comportamento){
        super (nome, integritaMassima);
       
        if(frammentiDati<0)
            throw new IllegalArgumentException("Il drop non può essere negativo.");
        if(comportamento==null)
            throw new IllegalArgumentException("Il comportamento non può essere nullo.");
        
        this.livello=livello;
        this.frammentiDati=frammentiDati;
        this.comportamento=comportamento;
    }


    //Eseguire Turno Nemico
    public void agisci(Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi){
        comportamento.agisci(this, giocatore, alleati, eventi);
    }

    //getter
    public int getLivello() {return livello;}
    public int getFrammentiDati() { return frammentiDati;}
}
