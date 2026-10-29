package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

//L'abilità è una combinazione di effetti su un dato bersaglio, costa al player cpu (stamina) ed ha un tempo di ricarica
//usiamo sia id che nome, in modo da poter cambiare il nome dell'abilità ma avere sempre una final reference a quest'ultima tramite id

import java.util.ArrayList;
import java.util.List;


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

