package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Arsenale {

    private final int dimensioneDeck;
    private final List<Abilità> zaino = new ArrayList<>();
    private final List<AbilitàInUso> deck = new ArrayList<>();

    public Arsenale(int dimensioneDeck) {
        if (dimensioneDeck <= 0) {
            throw new IllegalArgumentException("Il deck deve poter contenere almeno un'abilità");
        }
        this.dimensioneDeck = dimensioneDeck;
    }


    public boolean possiede(String idAbilità){

        //usiamo uno straem per trovare almeno un match nello zaino con l'abilità che abbiamo passato al metodo
        //se non la trova ritorna false
        return zaino.stream().anyMatch(a->a.id().equals(idAbilità));
    }

    public boolean isEquipaggiata(String idAbilità){

        //stessa cosa ma passiamo per deck (quindi per AbilitàInUso),
        //quindi usiamo getDefinizione per recuperare la definizione della classe Abilità
        return deck.stream().anyMatch(a->a.getDefinizione().id().equals(idAbilità));
    }

    public boolean equipaggia(String idAbilità){

        //Se è già equipaggiata o il deck è pieno ritorniamo false
        if(isEquipaggiata(idAbilità) || deck.size() >= dimensioneDeck)
            return false;


        //usiamo uno stream per cercare l'id dell'abilità nello zaino, se lo troviamo ne creiamo un'istanza (AbilitàInUso)
        //e la aggiungiamo al deck, altrimenti ritorniamo false.
        return zaino.stream()
                    .filter(a->a.id()
                    .equals(idAbilità)).
                    findFirst()
                    .map(a-> deck.add(new AbilitàInUso(a)))
                    .orElse(false);
    
    }


    public boolean rimuoviDalDeck(String idAbilita) {
        return deck.removeIf(a -> a.getDefinizione().id().equals(idAbilita));
    }

    public void avanzaRicariche() {
        deck.forEach(AbilitàInUso::avanzaTurno);
    }

    public void svuota() {
        deck.clear();
        zaino.clear();
    }

    public int getDimensioneDeck() { return dimensioneDeck; }
    public List<Abilità> getZaino() { return Collections.unmodifiableList(zaino); }
    public List<AbilitàInUso> getDeck() { return Collections.unmodifiableList(deck); }
}




