package it.unicam.cs.mpgc.rpg127792.Anomalia.logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Ramo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroTalenti;

/** Gestisce gli acquisti e la scelta dei talenti nel negozio. */
public final class Negozio {

    public static final int COSTO_BASE_ANOMALIA = 100;

    private final Giocatore giocatore;
    private final RegistroAbilità registroAbilità;
    private final RegistroTalenti registroTalenti;
    private final Random random = new Random();

    private List<Talento> talentiProposti = new ArrayList<>();

    public Negozio(Giocatore giocatore, RegistroAbilità registroAbilità, RegistroTalenti registroTalenti) {
        this.giocatore = giocatore;
        this.registroAbilità = registroAbilità;
        this.registroTalenti = registroTalenti;
    }

    //Abilità

    public List<CatalogoAbilità> getCatalogoDisponibile() {
        return registroAbilità.getCatalogoNegozio().stream()
                .filter(voce -> !giocatore.getArsenale().possiede(voce.abilità().id()))
                .toList();
    }

    public boolean compraAbilità(CatalogoAbilità voceCatalogo) {
        if (!getCatalogoDisponibile().contains(voceCatalogo)
                || !giocatore.getProgressione().spendiDati(voceCatalogo.costo())) {
            return false;
        }
        return giocatore.getArsenale().sblocca(voceCatalogo.abilità());
    }

    //Anomalia e Talenti

    public int getCostoProssimaAnomalia() {
        return COSTO_BASE_ANOMALIA * giocatore.getProgressione().getLivelloAnomalia();
    }

/**
 *  Aumenta il livello di Anomalia e prepara le proposte di talento.
 *
 *  @return true se l'acquisto riesce, false se mancano i dati o ci sono già proposte da scegliere
 */
    public boolean compraLivelloAnomalia() {
        if (!talentiProposti.isEmpty()) 
            return false; //impediamo di sovrascrivere la scelta precedente nel caso comprassimo più livelli
        
        if (!giocatore.getProgressione().spendiDati(getCostoProssimaAnomalia())) 
            return false; //non ha abbastanza fondi
        
        giocatore.aumentaAnomalia();
        this.talentiProposti = estraiTalenti(); 
        return true;
    }

    public List<Talento> getTalentiProposti() {
        return List.copyOf(talentiProposti);
    }

/**
 *  Apprende uno dei talenti proposti e rimuove le proposte dopo la scelta.
 *
 *  @param talento talento scelto
 *  @return true se il talento viene appreso
 */
    public boolean scegliTalento(Talento talento) {
    if (!talentiProposti.contains(talento)) 
        return false;
    if (!giocatore.apprendi(talento)) 
        return false;

    talentiProposti.clear();
        return true;
    }

    private List<Talento> estraiTalenti() {
        List<Talento> proposti = new ArrayList<>();
        List<Talento> tuttiITalenti = registroTalenti.getTutti();

        //per ogni ramo prendiamo tutti i possibili talenti (tolti quelli che già possiede il giocatore)
        //poi usiamo random per sceglierne uno per ramo e lo inseriamo in proposti
        for (Ramo ramo : Ramo.values()) {
            List<Talento> candidati = tuttiITalenti.stream()
                    .filter(t -> t.ramo() == ramo)
                    .filter(t -> !giocatore.getProgressione().possiedeTalento(t.id()))
                    .toList();

            if (!candidati.isEmpty()) 
                proposti.add(candidati.get(random.nextInt(candidati.size())));
            
        }
        return proposti;
    }
}