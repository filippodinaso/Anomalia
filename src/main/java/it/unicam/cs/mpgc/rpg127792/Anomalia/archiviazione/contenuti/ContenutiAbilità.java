package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioArea;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioEsecutore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioSingolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.CuraDiretta;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ScudoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;

//implementiamo le prime abilità 
public final class ContenutiAbilità implements RegistroAbilità {

    public static final String PING_ESECUTIVO = "ping_esecutivo";
    public static final String EXPLOIT_INPUT = "exploit_input";
    public static final String ISOLAMENTO_CORE = "isolamento_core";
    public static final String ROUTINE_RIPRISTINO = "routine_ripristino";
    public static final String BUFFER_OVERFLOW = "buffer_overflow";
    public static final String FIREWALL_REATTIVO = "firewall_reattivo";

    private final List<Abilità> arsenaleIniziale;
    private final Map<String, Abilità> perId = new LinkedHashMap<>();

    public ContenutiAbilità() {

        arsenaleIniziale = List.of(

                new Abilità(PING_ESECUTIVO, "Ping_Esecutivo", 2, 0, 
                new BersaglioSingolo(), List.of(DannoDiretto.attacco(10))),

                new Abilità(EXPLOIT_INPUT, "Exploit_Input", 4, 2, 
                new BersaglioArea(3), List.of(DannoDiretto.attacco(25))),

                new Abilità(ISOLAMENTO_CORE, "IsolamentoCore", 3, 2, 
                new BersaglioEsecutore(), List.of(new ScudoDiretto(15))),

                new Abilità(ROUTINE_RIPRISTINO, "RoutineRipristino", 4, 3, 
                new BersaglioEsecutore(), List.of(new CuraDiretta(30))));


        arsenaleIniziale.forEach(a -> perId.put(a.id(), a));
    }

    @Override
    public List<Abilità> getArsenaleIniziale() {
        return arsenaleIniziale;
    }
}


