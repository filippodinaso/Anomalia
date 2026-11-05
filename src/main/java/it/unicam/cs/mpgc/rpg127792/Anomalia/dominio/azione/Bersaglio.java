package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;

/*Interfaccia per selezionare il bersaglio 
Usiamo un interfaccia per distingure bersaglio singolo, multiplo e giocatore (per eventuali abilità di difesa) 
scelto può essere null se richiedeScelta() è false
la lista restituita non è mai null
nemici contiene solo i nemici ancora in campo
*/
public interface Bersaglio {

     boolean richiedeScelta();

     List<Combattente> seleziona(Combattente scelto, Combattente esecutore, List<? extends Combattente> nemiciVivi);

     String descrizione();

}
