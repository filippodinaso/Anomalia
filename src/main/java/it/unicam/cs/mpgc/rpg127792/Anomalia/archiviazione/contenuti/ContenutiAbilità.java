package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.CatalogoAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioArea;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioEsecutore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.bersagli.BersaglioSingolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.CuraDiretta;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ScudoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;

/**
 * Contiene le abilità iniziali e quelle disponibili nel negozio.
 */
public final class ContenutiAbilità implements RegistroAbilità {

    public static final String PING_ESECUTIVO = "ping_esecutivo";
    public static final String EXPLOIT_INPUT = "exploit_input";
    public static final String ISOLAMENTO_CORE = "isolamento_core";
    public static final String ROUTINE_RIPRISTINO = "routine_ripristino";
    public static final String BUFFER_OVERFLOW = "buffer_overflow";
    public static final String FIREWALL_REATTIVO = "firewall_reattivo";
    public static final String BOMBA_ATOMICA = "Bomba_Atomica";

    private final List<Abilità> arsenaleIniziale;
    private final List<CatalogoAbilità> catalogoNegozio;
    private final Map<String, Abilità> abilitàPerId = new LinkedHashMap<>();

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


    catalogoNegozio = List.of(
                new CatalogoAbilità(new Abilità(BUFFER_OVERFLOW, "Buffer_Overflow", 5, 3,
                new BersaglioSingolo(), List.of(DannoDiretto.attacco(45))), 150),

                new CatalogoAbilità(new Abilità(FIREWALL_REATTIVO, "Firewall_Reattivo", 2, 1,
                new BersaglioEsecutore(), List.of(new ScudoDiretto(10))), 100),

                new CatalogoAbilità(new Abilità(BOMBA_ATOMICA, "Bomba_Atomica", 10, 5, 
                new BersaglioArea(10), List.of(DannoDiretto.attacco(200))), 200));

        arsenaleIniziale.forEach(abilità -> abilitàPerId.put(abilità.id(), abilità));
        catalogoNegozio.forEach(voce -> abilitàPerId.put(voce.abilità().id(), voce.abilità()));
    }

    @Override
    public List<Abilità> getArsenaleIniziale() {
        return arsenaleIniziale;
    }

    @Override
    public List<CatalogoAbilità> getCatalogoNegozio() {
        return catalogoNegozio;
    }

    @Override
    public Abilità get(String id) {
        if (!abilitàPerId.containsKey(id)) {
            throw new IllegalArgumentException("Abilità sconosciuta: " + id);
        }
        return abilitàPerId.get(id);
    }
}


