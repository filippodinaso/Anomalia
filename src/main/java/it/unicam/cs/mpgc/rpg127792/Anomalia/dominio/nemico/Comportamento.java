package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;

//Stategy implementata dal nemico

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;

/*esecutore: nemico che agisce
giocatore: l'utente
alleati: nemici dell'utente in campo*/

public interface Comportamento {

    void agisci(Nemico esecutore, Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi);
    
    
}
