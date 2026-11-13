package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti.ComportamentoAssalto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti.ComportamentoBoss;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti.ComportamentoSabotaggio;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti.ComportamentoSupporto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.DefinizioneNemico;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Scaling;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroNemici;



// Implementiamo dei nemici
public final class ContenutiNemici implements RegistroNemici {

    public static final String ZOMBIE_PROCESS = "zombie_process";
    public static final String INTERRUPT_REQUEST = "interrupt_request";
    public static final String MUTEX_LOCK = "mutex_lock";
    public static final String ROOT_HYPERVISOR = "root_hypervisor";

    private final Map<String, DefinizioneNemico> nemiciPerId = new LinkedHashMap<>();

    public ContenutiNemici() {
        List.of(

/*              new DefinizioneNemico(id, nome, 
                    new Scaling(hp_base, aumento_hp_per_livello), new Scaling(loot_base, aumento_loot_per_livello), 
                    livello -> new ComportamentoScelto(danno_base + (aumento_danno_per_livello * livello))*/
                   
                new DefinizioneNemico(ZOMBIE_PROCESS, "Zombie_Process", 
                    new Scaling(30, 5), new Scaling(15, 5),
                    livello -> new ComportamentoAssalto(10 + 2 * livello)),
               
                new DefinizioneNemico(INTERRUPT_REQUEST, "Interrupt_Request", 
                    new Scaling(20, 3), new Scaling(20, 5),
                    livello -> new ComportamentoSabotaggio(8 + 2 * livello, 2)),
                
                new DefinizioneNemico(MUTEX_LOCK, "Mutex_Lock", 
                    new Scaling(50, 8), new Scaling(25, 5),
                    livello -> new ComportamentoSupporto(5 + livello, 15, 3)),
               
                new DefinizioneNemico(ROOT_HYPERVISOR, "Root_Hypervisor", 
                    new Scaling(100, 15), new Scaling(100, 20),
                    livello -> new ComportamentoBoss(15 + 3 * livello, 25, 3)))
               
               
                .forEach(nemico -> nemiciPerId.put(nemico.id(), nemico));
    }

    @Override
    public DefinizioneNemico get(String id) {
        if (!nemiciPerId.containsKey(id)) {
            throw new IllegalArgumentException("Nemico sconosciuto: " + id);
        }
        return nemiciPerId.get(id);
    }

    @Override
    public DefinizioneNemico getPunitivoPerDecadimentoInnocuo() {
        return get(ZOMBIE_PROCESS);
    }
}