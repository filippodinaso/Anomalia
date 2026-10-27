package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;

//Contesto per applicare effetto, contiene esecutore, chi sono i bersagli e il pubblicatore per inviare le notifiche.

public record ContestoEffetto(Combattente esecutore, List<Combattente> bersagli, Pubblicatore<EventoDiGioco> eventi){

public ContestoEffetto{

    if (esecutore==null) 
        throw new NullPointerException("L'esecutore non può essere nullo.");
    

    if (eventi == null)
        throw new NullPointerException("Il pubblicatore non può essere nullo.");


    //Copiamo lista bersagli per averne una immutabile garantendo il contesto
    bersagli = List.copyOf(bersagli);
}

//Colpire un bersaglio (per attacchi)
public static ContestoEffetto contro(Combattente esecutore, Combattente bersaglio, Pubblicatore<EventoDiGioco> eventi) {
    return new ContestoEffetto(esecutore, List.of(bersaglio), eventi);
}

//Colpire se stessi (per difesa)
public static ContestoEffetto suSeStesso(Combattente esecutore, Pubblicatore<EventoDiGioco> eventi) {
    return new ContestoEffetto(esecutore, List.of(esecutore), eventi);
}


}

