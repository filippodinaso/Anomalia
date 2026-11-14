package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ModificaStatistica;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Ramo;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Statistica;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Talento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroTalenti;


public final class ContenutiTalenti implements RegistroTalenti {

    private final Map<String, Talento> talentiPerId = new LinkedHashMap<>();

    public ContenutiTalenti() {
        List.of(
                new Talento("overclock_offensivo", Ramo.IRA, "Overclock Offensivo", "+5 danni a ogni attacco",
                    List.of(new ModificaStatistica(Statistica.DANNI_EXTRA, 5))),

                new Talento("scudi_di_rete", Ramo.EMPATIA, "Scudi di Rete", "+25 integrità massima, +2 integrità a turno",
                    List.of(new ModificaStatistica(Statistica.INTEGRITA_MASSIMA, 25),
                            new ModificaStatistica(Statistica.RIGENERAZIONE_INTEGRITA, 2))),

                new Talento("ottimizzazione_cache", Ramo.CURIOSITÀ, "Ottimizzazione Cache", "+2 CPU massima, +1 CPU a turno",
                    List.of(new ModificaStatistica(Statistica.CPU_MASSIMA, 2),
                            new ModificaStatistica(Statistica.RIGENERAZIONE_CPU, 1))))

                .forEach(talento -> talentiPerId.put(talento.id(), talento));
    }

    @Override
    public List<Talento> getTutti() {
        return List.copyOf(talentiPerId.values());
    }

    @Override
    public Talento get(String id) {
        if (!talentiPerId.containsKey(id)) {
            throw new IllegalArgumentException("Talento sconosciuto: " + id);
        }
        return talentiPerId.get(id);
    }
}