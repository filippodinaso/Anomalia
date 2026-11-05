package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo;

import java.util.List;
import java.util.stream.Collectors;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco.ProcessoInnocuoGestito;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;

//Un processo innocuo sarà un processo che non attaccherà il giocatore ma che lo metterà di fronte ad una scelta (Assorbi o Elimina)
//tale scelta definirà quali delle due liste effetti sarà applicata sul player
//L'entità avrà una scadenza (turniRImanenti), dopodichè decadrà 
public final class ProcessoInnocuo {

    private final String nome;
    private int turniRimanenti;
    private boolean gestito;
    private final List<Effetto> effettiAssorbi;
    private final List<Effetto> effettiElimina;


    public ProcessoInnocuo(String nome, int turniAlDecadimento, List<Effetto> effettiAssorbi, List<Effetto> effettiElimina) {
        if (turniAlDecadimento <= 0) {
            throw new IllegalArgumentException("Il processo deve durare almeno un turno");
        }
        this.nome = nome;
        this.turniRimanenti = turniAlDecadimento;
        this.effettiAssorbi = List.copyOf(effettiAssorbi);
        this.effettiElimina = List.copyOf(effettiElimina);
    }


    private void gestisci(List<Effetto> effetti, boolean assorbito, Giocatore giocatore, Pubblicatore<EventoDiGioco> eventi) {
        if (gestito) {
            throw new IllegalStateException("Il processo è già stato gestito");
        }
        gestito = true;
        ContestoEffetto contesto = ContestoEffetto.suSeStesso(giocatore, eventi);
        effetti.forEach(effetto -> effetto.applica(contesto));
        eventi.pubblica(new ProcessoInnocuoGestito(nome, assorbito, descrivi(effetti)));
    }


    public void assorbi(Giocatore giocatore, Pubblicatore<EventoDiGioco> eventi) {
        gestisci(effettiAssorbi, true, giocatore, eventi);
    }

    public void elimina(Giocatore giocatore, Pubblicatore<EventoDiGioco> eventi) {
        gestisci(effettiElimina, false, giocatore, eventi);
    }

    public void trascorriTurno() {
        if (turniRimanenti > 0)
        turniRimanenti--;
    }

    public boolean isDecaduto() {
        return !gestito && turniRimanenti == 0;
    }

    private static String descrivi(List<Effetto> effetti) {
        return effetti.stream().map(Effetto::descrizione).collect(Collectors.joining(", "));
    }

    public String getNome() { return nome; }
    public int getTurniRimanenti() { return turniRimanenti; }
    public boolean isGestito() { return gestito; }
}



