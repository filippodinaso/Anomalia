package it.unicam.cs.mpgc.rpg127792.Anomalia.interfaccia;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.AbilitàUsata;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.Annuncio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.CpuBruciata;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.DannoInflitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.GiocatoreSconfitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.IntegritaRipristinata;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.NemicoSconfitto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ProcessoInnocuoDecaduto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ProcessoInnocuoGestito;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScontroIniziato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScontroVinto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ScudoApplicato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.TurnoGiocatoreIniziato;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.TurnoNemiciIniziato;

//Trasforma gli eventi di gioco in righe di testo 
public final class FormattatoreEventi {

    private FormattatoreEventi() {
    }

    public static String formatta(EventoDiGioco evento) {
        return switch (evento) {
            //Flusso
            case ScontroIniziato e -> "Scontro iniziato: " + e.minacce() + " minacce rilevate.";
            case TurnoGiocatoreIniziato e -> "Turno " + e.numero() + " ---";
            case AbilitàUsata e -> e.giocatore() + " esegue " + e.abilità() + ".";
            case TurnoNemiciIniziato e -> "Turno dei nemici.";
            case ScontroVinto e -> "Scontro superato.";
            case Annuncio e -> e.fonte() + ": " + e.testo();

            case DannoInflitto e -> e.sorgente() + " colpisce " + e.bersaglio() + ": -" + e.integritaPersa()
                 + " integrità" + (e.scudoAssorbito() > 0 ? " (" + e.scudoAssorbito() + " assorbiti dallo scudo)" : "") + ".";
            case IntegritaRipristinata e -> e.bersaglio() + " recupera " + e.quantita() + " integrità.";
            case ScudoApplicato e -> e.bersaglio() + " guadagna " + e.quantita() + " scudo.";
            case CpuBruciata e -> e.bersaglio() + " perde " + e.quantita() + " CPU.";
            
            //Stato entità
            case NemicoSconfitto e -> e.nemico() + " è stato eliminato (+" + e.frammentiDati() + " dati).";
            case GiocatoreSconfitto e -> e.nome() + " è stato sconfitto.";
            case ProcessoInnocuoGestito e -> e.processo() + (e.assorbito() ? " assorbito: " : " eliminato: ") + e.riepilogo() + ".";
            case ProcessoInnocuoDecaduto e -> e.processo() + " è decaduto: compare un nuovo nemico.";
        };
    }
}