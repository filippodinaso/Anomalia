package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.BruciaCpu;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.CuraDiretta;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ModificaDati;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ModificaStatistica;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Statistica;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo.DefinizioneInnocuo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroInnocui;



//implementiamo esempi di processi innocui 
public final class ContenutiInnocui implements RegistroInnocui {

    public static final String CACHE_DATI = "cache_dati";
    public static final String NODO_RIPARAZIONE = "nodo_riparazione";
    public static final String CORE_SOVRACCARICO = "core_sovraccarico";

    private final Map<String, DefinizioneInnocuo> innocuoPerId = new LinkedHashMap<>();

    public ContenutiInnocui() {
        
        List.of(
                new DefinizioneInnocuo(CACHE_DATI, "Cache_Dati", 3,
                List.of(new ModificaDati(20)),  
                List.of(new ModificaDati(50), DannoDiretto.fisso(10))), //Usiamo danno senza bonus
               
                new DefinizioneInnocuo(NODO_RIPARAZIONE, "Nodo_Riparazione", 3,
                List.of(new CuraDiretta(30)),
                List.of(new ModificaStatistica(Statistica.CPU_MASSIMA, 15), DannoDiretto.fisso(15))),

                new DefinizioneInnocuo(CORE_SOVRACCARICO, "Core_Sovraccarico", 2,
                List.of(new ModificaStatistica(Statistica.CPU_MASSIMA, 5)),
                List.of(new ModificaDati(100), 
                        new CuraDiretta(50), 
                        new ModificaDati(-20), 
                        new BruciaCpu(4))))

                .forEach(innocuo -> innocuoPerId.put(innocuo.id(), innocuo));
    }

    @Override
    public DefinizioneInnocuo get(String id) {
        //se non lo troviamo lanciamo un'illegalargumentexception
        if (!innocuoPerId.containsKey(id)) 
            throw new IllegalArgumentException("Processo innocuo sconosciuto: " + id);
        
        return innocuoPerId.get(id);
    }
}