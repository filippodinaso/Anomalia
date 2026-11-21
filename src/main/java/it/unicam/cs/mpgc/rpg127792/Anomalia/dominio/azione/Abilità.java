package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;


import java.util.ArrayList;
import java.util.List;
/**
 * @param id final reference
 * @param nome 
 * @param costoCpu costo stamina dell'abilità
 * @param ricarica turni di ricarica necessari
 * @param bersaglio regola che decide chi viene colpito
 * @param effetti lista effetti applicati dall'abilità
 */
public record Abilità(String id, String nome, int costoCpu, int ricarica, Bersaglio bersaglio, List<Effetto> effetti) {
   
public Abilità{
    
    if(id == null || id.isBlank() || nome==null || nome.isBlank())
        throw new IllegalArgumentException("Id e nome sono obbligatori");
    if(costoCpu < 0 || ricarica < 0)
        throw new IllegalArgumentException("Costo e ricarica non possono essere negativi");
    if(bersaglio == null) 
        throw new IllegalArgumentException("Il bersaglio è obbligatorio");
    if(effetti== null || effetti.isEmpty())
        throw new IllegalArgumentException("L'abilità deve avere almeno un effetto.");

    //Copiamo lista effetti per averne una immutabile garantendo il contesto
    effetti=List.copyOf(effetti);
 }
 
 
public String descrizione() {

    List<String> descrizioneAbilità = new ArrayList<>();

    for (Effetto effetto : effetti) {
        descrizioneAbilità.add(effetto.descrizione());
    }
    return String.join(", ", descrizioneAbilità) + " ," + bersaglio.descrizione() + ".";
}

}    

