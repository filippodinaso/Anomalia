package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa;

import java.util.Set;


//fotografia dell'avanzamento del giocatore
public record StatoCapitolo(String idCapitolo, String idNodoAttuale, String idNodoRespawn, Set<String> nodiCompletati) {

    public StatoCapitolo {
        nodiCompletati = Set.copyOf(nodiCompletati);
    }
}