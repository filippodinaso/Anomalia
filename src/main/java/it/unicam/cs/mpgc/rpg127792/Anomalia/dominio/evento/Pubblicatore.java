package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento;

//Notifichiamo gli eventi del gioco
//Separazione logica di gioco dalla visualizzazione
@FunctionalInterface
public interface Pubblicatore<E> {

    void pubblica(E evento);

    //implementazione null object del pubblicatore
    static <E> Pubblicatore<E> nessuno() {
        return evento -> { };
    }

}