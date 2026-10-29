package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

//Interfaccia per selezionare il bersaglio 
//Usiamo un interfaccia per distingure bersaglio singolo, multiplo e giocatore (per eventuali abilità di difesa) 
public interface Bersaglio {

     List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<Combattente> nemici);

     String descrizione();

}
