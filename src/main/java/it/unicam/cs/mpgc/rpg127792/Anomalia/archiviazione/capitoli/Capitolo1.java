package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.capitoli;


import java.util.List;

import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiInnocui.CACHE_DATI;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiInnocui.CORE_SOVRACCARICO;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiInnocui.NODO_RIPARAZIONE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiNemici.INTERRUPT_REQUEST;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiNemici.MUTEX_LOCK;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiNemici.ROOT_HYPERVISOR;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti.ContenutiNemici.ZOMBIE_PROCESS;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.DefinizioneCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.MappaCapitolo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoBoss;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCheckpoint;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa.nodi.NodoCombattimento;

public final class Capitolo1 implements DefinizioneCapitolo {

    public static final String ID = "CAP_01";

    @Override
    public String getId() {
        return ID;
    }    

    @Override
    public MappaCapitolo crea() {
       return MappaCapitolo.costruisci(ID, "Capitolo 1: Il Recinto")
                // 1° scontro
                .nodo(new NodoCombattimento("Sandbox_Settore_01", "Un ambiente isolato con un processo instabile.", 1,
                        List.of(ZOMBIE_PROCESS, ZOMBIE_PROCESS), List.of(CACHE_DATI)))

                // 2° scontro
                .nodo(new NodoCombattimento("Gateway_Sicurezza", "Il varco che separa il recinto dal resto della rete.", 2,
                        List.of(ZOMBIE_PROCESS, INTERRUPT_REQUEST, INTERRUPT_REQUEST), List.of(NODO_RIPARAZIONE)))

                // Safe Room (Checkpoint)
                .nodo(new NodoCheckpoint("Safe_Room_Alpha", "Un angolo della rete non monitorato."))

                // Bivio A 
                .nodo(new NodoCombattimento("Archivio_Dati_Corrotti", "Settori danneggiati presidiati da un Mutex_Lock.", 3,
                        List.of(MUTEX_LOCK, ZOMBIE_PROCESS), List.of(NODO_RIPARAZIONE)))

                // Bivio B 
                .nodo(new NodoCombattimento("Canale_Laterale", "Una scorciatoia pericolosa ma ricca di risorse.", 3,
                        List.of(ZOMBIE_PROCESS, ZOMBIE_PROCESS, ZOMBIE_PROCESS, INTERRUPT_REQUEST), List.of(CORE_SOVRACCARICO)))

                // Boss finale
                .nodo(new NodoBoss("Core_Firewall_Boss", "Il Root_Hypervisor sbarra l'uscita.", 4,
                        List.of(ROOT_HYPERVISOR), List.of()))

                // Collegamenti del grafo
                .partenza("Sandbox_Settore_01")
                .arco("Sandbox_Settore_01", "Gateway_Sicurezza")
                .arco("Gateway_Sicurezza", "Safe_Room_Alpha")
                .arco("Safe_Room_Alpha", "Archivio_Dati_Corrotti")
                .arco("Safe_Room_Alpha", "Canale_Laterale")
                .arco("Archivio_Dati_Corrotti", "Core_Firewall_Boss")
                .arco("Canale_Laterale", "Core_Firewall_Boss")
                .crea();
    }
}