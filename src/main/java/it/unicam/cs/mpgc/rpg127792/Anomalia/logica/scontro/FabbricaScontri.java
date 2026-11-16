package it.unicam.cs.mpgc.rpg127792.Anomalia.logica.scontro;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.DefinizioneInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.ProcessoInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroInnocui;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroNemici;

public final class FabbricaScontri {

    private final RegistroNemici registroNemici;
    private final RegistroInnocui registroInnocui;

    public FabbricaScontri(RegistroNemici registroNemici, RegistroInnocui registroInnocui) {
        this.registroNemici = registroNemici;
        this.registroInnocui = registroInnocui;
    }

    public Scontro crea(Giocatore giocatore, NodoCombattimento nodo, Pubblicatore<EventoDiGioco> eventi) {
        int livelloAnomalia = giocatore.getProgressione().getLivelloAnomalia();
        int rango = nodo.getRango();

        List<Nemico> nemici = nodo.getIdNemici().stream()
                                                .map(id -> registroNemici.get(id).crea(livelloAnomalia, rango))
                                                .toList();

        List<ProcessoInnocuo> innocui = nodo.getIdInnocui().stream()
            .map(id -> {DefinizioneInnocuo defInnocuo = registroInnocui.get(id);
                        Nemico nemicoAlDecadimento = registroNemici.get(defInnocuo.idNemicoDecaduto()).crea(livelloAnomalia, rango);
                return defInnocuo.crea(nemicoAlDecadimento);
            })
            .toList();

        return new Scontro(giocatore, nemici, innocui, eventi);
    }
}