package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.AbilitàInUso;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.AbilitàUsata;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.GiocatoreSconfitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.NemicoSconfitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ProcessoInnocuoDecaduto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScontroIniziato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScontroVinto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.TurnoGiocatoreIniziato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.TurnoNemiciIniziato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.ProcessoInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

/** Gestisce i turni, le azioni e la conclusione di uno scontro. */
public final class Scontro {

    private final Giocatore giocatore;
    private final List<Nemico> nemici;
    private final List<ProcessoInnocuo> innocui;
    private final Pubblicatore<EventoDiGioco> eventi;
    private StatoScontro stato = StatoScontro.TURNO_GIOCATORE;
    private int turno;
    private boolean avviato = false;


    public Scontro(Giocatore giocatore, List<Nemico> nemici, List<ProcessoInnocuo> innocui, Pubblicatore<EventoDiGioco> eventi) {
        this.giocatore = giocatore;
        this.nemici = new ArrayList<>(nemici);
        this.innocui = new ArrayList<>(innocui);
        this.eventi = eventi;
    }

    public void avvia() {
        if (avviato) 
            throw new IllegalStateException("Lo scontro è già stato avviato");
    
        avviato = true;

        giocatore.iniziaScontro();
        eventi.pubblica(new ScontroIniziato(nemici.size()));
        iniziaTurnoGiocatore();
    }

    private void iniziaTurnoGiocatore() {
        turno++;
        giocatore.iniziaTurno(turno);
        eventi.pubblica(new TurnoGiocatoreIniziato(turno));
    }

//Turno giocatore ---------------


/**
 *  Usa un'abilità durante il turno del giocatore
 *
 *  @param abilita 
 *  @param bersaglio se l'abilità lo richiede 
 *  @return true se l'abilità viene usata, false se non può essere eseguita
 */
    public boolean usaAbilità(AbilitàInUso abilità, Nemico bersaglio) {
       
        Abilità definizione = abilità.getDefinizione();
        
        if (stato != StatoScontro.TURNO_GIOCATORE ||
                  !giocatore.getArsenale().getDeck().contains(abilità) ||
                  !abilità.isPronta() ||
                  (definizione.bersaglio().richiedeScelta() && !nemici.contains(bersaglio)) ||
                  !giocatore.getCpu().consuma(definizione.costoCpu())) {
            return false;
        }

        List<Combattente> bersagli = definizione.bersaglio().seleziona(bersaglio, giocatore, nemici);
        eventi.pubblica(new AbilitàUsata(giocatore.getNome(), definizione.nome()));
        ContestoEffetto contesto = new ContestoEffetto(giocatore, bersagli, eventi);
        definizione.effetti().forEach(effetto -> effetto.applica(contesto));
        abilità.avviaRicarica();
        verificaFine();
        return true;
    }

/** Assorbe un processo innocuo. */
    public boolean assorbi(ProcessoInnocuo innocuo) {
        if (!gestire(innocuo)) {
            return false;
        }
        innocuo.assorbi(giocatore, eventi);
        verificaFine();
        return true;
    }

/** Elimina un processo innocuo (ricompensa maggiore ma con un prezzo). */
    public boolean elimina(ProcessoInnocuo innocuo) {
        if (!gestire(innocuo)) {
            return false;
        }
        innocuo.elimina(giocatore, eventi);
        verificaFine();
        return true;
    }

    private boolean gestire(ProcessoInnocuo innocuo) {
        return stato == StatoScontro.TURNO_GIOCATORE && innocui.contains(innocuo) && !innocuo.isGestito();
    }

//Turno dei nemici ----------------

/**
 * Esegue il turno dei nemici e avvia il turno successivo
 */
    public void terminaTurno() {
        if (stato != StatoScontro.TURNO_GIOCATORE) {
            return;
        }
        eventi.pubblica(new TurnoNemiciIniziato());
        for (Nemico nemico : new ArrayList<>(nemici)) {
            if (nemico.isSconfitto()) {
                continue;
            }
            nemico.agisci(giocatore, getNemici(), eventi);
            if (giocatore.isSconfitto()) {
                break;
            }
        }
        if (!giocatore.isSconfitto()) {
            decadimentoInnocui();
        }
        verificaFine();
        if (stato == StatoScontro.TURNO_GIOCATORE) {
            iniziaTurnoGiocatore();
        }
    }

/** I processi ignorati troppo a lungo decadono e al loro posto compare un nemico*/
    private void decadimentoInnocui() {
        List<ProcessoInnocuo> decaduti = new ArrayList<>();

        for (ProcessoInnocuo innocuo : innocui) {
            innocuo.trascorriTurno();
            if (innocuo.isDecaduto()) 
                decaduti.add(innocuo);
        }

        for (ProcessoInnocuo innocuo : decaduti) {
            innocui.remove(innocuo);
            eventi.pubblica(new ProcessoInnocuoDecaduto(innocuo.getNome()));
            nemici.add(innocuo.getNemicoAlDecadimento());
        }
    }

    // --- Fine dello scontro ---

    private void verificaFine() {

        for (Nemico nemico : nemici) {
            if (nemico.isSconfitto()) {
                giocatore.getProgressione().modificaDati(nemico.getFrammentiDati());
                eventi.pubblica(new NemicoSconfitto(nemico.getNome(), nemico.getFrammentiDati()));
            }
        }

        nemici.removeIf(Nemico::isSconfitto);
        innocui.removeIf(ProcessoInnocuo::isGestito);

        if (giocatore.isSconfitto()) {
            stato = StatoScontro.SCONFITTA;
            eventi.pubblica(new GiocatoreSconfitto(giocatore.getNome()));
        } else if (nemici.isEmpty()) {
            stato = StatoScontro.VITTORIA;
            eventi.pubblica(new ScontroVinto());
        }
    }

//getter
    public StatoScontro getStato() { return stato; }
    public int getTurno() { return turno; }
    public List<Nemico> getNemici() { return new ArrayList<>(nemici); }
    public List<ProcessoInnocuo> getInnocui() { return new ArrayList<>(innocui); }
}
