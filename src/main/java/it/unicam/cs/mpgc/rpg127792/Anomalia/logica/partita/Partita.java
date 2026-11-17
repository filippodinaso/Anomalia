package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.IstantaneaGiocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.AvanzamentoCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.Nodo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.RegistroCapitoli;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;

//stats giocatore e il suo avanzamento nel capitolo 
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

    //Ricostruisce la partita salvata
    public static Partita carica(RegistroCapitoli capitoli, RegistroAbilità abilità, RepositoryPartite repository) {
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

    //Sposta il giocatore nel nodo, se accessibile, e fa scattare l'effetto d'ingresso del nodo.
    public boolean entra(Nodo nodo) {
        if (!avanzamento.entra(nodo)) {
            return false;
        }
        nodo.allIngresso(giocatore);
        if (nodo.isPuntoDiSalvataggio()) {
            avanzamento.completa(nodo);
        }
        return true;
    }

    public void vittoria(Nodo nodo) {
        if (!nodo.equals(avanzamento.getAttuale())) {
            throw new IllegalArgumentException("Si può completare solo il nodo in cui ci si trova: " + nodo.getId());
        }
        avanzamento.completa(nodo);
    }

    //Il giocatore è stato sconfitto e torna all'ultimo checkpoint
    public void sconfitta() {
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