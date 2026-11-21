package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.IstantaneaGiocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.AvanzamentoCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;

/** Gestisce lo stato della partita e il suo avanzamento nel capitolo. */
public final class Partita {

    private final Giocatore giocatore;
    private final RegistroCapitoli capitoli;
    private final RepositoryPartite repository;
    private AvanzamentoCapitolo avanzamento;

    private Partita(Giocatore giocatore, AvanzamentoCapitolo avanzamento, RegistroCapitoli capitoli, RepositoryPartite repository) {
        this.giocatore = giocatore;
        this.avanzamento = avanzamento;
        this.capitoli = capitoli;
        this.repository = repository;
    }

    public static Partita nuova(RegistroCapitoli capitoli, RegistroAbilità abilità, RepositoryPartite repository) {
        Giocatore giocatore = new Giocatore(abilità.getArsenaleIniziale());
        return new Partita(giocatore, new AvanzamentoCapitolo(capitoli.creaPrimo()), capitoli, repository);
    }

/**
 *  Ricostruisce la partita usando lo stato salvato e i registri correnti.
 *
 *  @param capitoli registro dei capitoli
 *  @param abilita registro delle abilità
 *  @param repository repository del salvataggio
 *  @return partita ricostruita
 *  @throws IllegalStateException se il salvataggio non è coerente
 */
    public static Partita carica(RegistroCapitoli capitoli, RegistroAbilità abilità, RepositoryPartite repository) {
        try {
            StatoPartita stato = repository.carica();
            IstantaneaGiocatore foto = stato.giocatore();

            //il registro trasforma gli id salvati in abilità che poi vengono passati a ricostruisci in IstantaneaGiocatore
            List<Abilità> zaino = foto.idZaino().stream().map(abilità::get).toList();
            List<Abilità> deck = foto.idDeck().stream().map(abilità::get).toList();
            Giocatore giocatore = foto.ricostruisci(abilità.getArsenaleIniziale(), zaino, deck);

            AvanzamentoCapitolo avanzamento = new AvanzamentoCapitolo(capitoli.crea(stato.capitolo().idCapitolo()));
            avanzamento.ripristina(stato.capitolo());
            return new Partita(giocatore, avanzamento, capitoli, repository);
        } 
         catch (IllegalArgumentException e) {
            throw new IllegalStateException("Il salvataggio non è coerente con il gioco: " + e.getMessage(), e);
        }
    }

/**
 *  Entra nel nodo se è accessibile e applica l'effetto previsto all'ingresso (se è il primo ingresso).
 *
 *  @param nodo nodo in cui entrare
 *  @return true se l'ingresso avviene
 */
    public boolean entra(Nodo nodo) {
    
        boolean primoIngresso = nodo.isPuntoDiSalvataggio() && !avanzamento.isCompletato(nodo);

        if (!avanzamento.entra(nodo)) 
             return false;

        if (!nodo.isPuntoDiSalvataggio() || primoIngresso)
             nodo.allIngresso(giocatore);

        if (nodo.isPuntoDiSalvataggio()) 
            avanzamento.completa(nodo);
        
        return true;
    }

    public void vittoria(Nodo nodo) {
        if (!nodo.equals(avanzamento.getAttuale())) {
            throw new IllegalArgumentException("Si può completare solo il nodo in cui ci si trova: " + nodo.getId());
        }
        avanzamento.completa(nodo);
    }

/** Il giocatore sconfitto viene riportato all'ultimo checkpoint,
 *  perde metà dei FrammentiDati acquisiti fino a quel momento.
 */
    public void sconfitta() {
        int dati = giocatore.getProgressione().getFrammentiDati();
        int perdita = dati / 2 + dati % 2;
        giocatore.getProgressione().modificaDati(-perdita);
        giocatore.riavvia();
        avanzamento.eseguiRespawn();
    }

    public boolean isCapitoloConcluso() {
        return avanzamento.isConcluso();
    }

    public boolean haCapitoloSuccessivo() {
        return capitoli.haSuccessivo(avanzamento.getMappa().getId());
    }

    public void avanzaCapitolo() {
        if (!isCapitoloConcluso() || !haCapitoloSuccessivo()) 
            throw new IllegalStateException("Non si può passare al capitolo successivo");

        String idSuccessivo = capitoli.getIdSuccessivo(avanzamento.getMappa().getId());
        avanzamento = new AvanzamentoCapitolo(capitoli.crea(idSuccessivo));
    }

    public void salva() {
        repository.salva(new StatoPartita(IstantaneaGiocatore.di(giocatore), avanzamento.getStato()));
    }

    public Giocatore getGiocatore() { return giocatore; }
    public AvanzamentoCapitolo getAvanzamento() { return avanzamento; }
}