package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.controller;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.Partita;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;

/** Gestisce le operazioni di creazione, caricamento e avanzamento della partita. */
public final class ControllerPartita {
    private final RegistroCapitoli capitoli;
    private final RegistroAbilità abilità;
    private final RepositoryPartite repository;

/** Inizializza il controller con i registri e il repository usati dalla partita.*/
    public ControllerPartita(RegistroCapitoli capitoli, RegistroAbilità abilità, RepositoryPartite repository) {
        this.capitoli = capitoli;
        this.abilità = abilità;
        this.repository = repository;
    }

    public boolean esisteSalvataggio() {
        return repository.esiste();
    }

    public Partita nuovaPartita() {
        return Partita.nuova(capitoli, abilità, repository);
    }

    public Partita continuaPartita() {
        return Partita.carica(capitoli, abilità, repository);
    }

    public boolean entra(Partita partita, Nodo nodo) {
        return partita.entra(nodo);
    }

    public void avanzaCapitolo(Partita partita) {
        partita.avanzaCapitolo();
    }

    public void salva(Partita partita) {
        partita.salva();
    }
}
