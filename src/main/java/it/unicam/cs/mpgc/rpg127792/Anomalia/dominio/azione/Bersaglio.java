package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;


/**
*Interfaccia per selezionare il bersaglio 
*Usiamo un interfaccia per distinguere bersaglio singolo, multiplo e giocatore stesso.
*/
public interface Bersaglio {

     boolean richiedeScelta();
     
//   scelto può essere null se richiedeScelta() è false, la lista != null, 
     List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<? extends Combattente> nemiciVivi);

     String descrizione();

}
