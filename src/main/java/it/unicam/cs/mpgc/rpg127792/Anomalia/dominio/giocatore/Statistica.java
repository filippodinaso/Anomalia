package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.function.BiConsumer;

//Statistiche upgradabili
public enum  Statistica {
    INTEGRITA_MASSIMA("INTEGRITÀ MAX", Giocatore::aumentaIntegritaMassima),
    CPU_MASSIMA("CPU MAX", (g, v) -> g.getCpu().aumentaMassima(v)),
    RIGENERAZIONE_CPU("RIGENERAZIONE CPU", (g, v) -> g.getCpu().aumentaRigenerazione(v)),
    RIGENERAZIONE_INTEGRITA("RIGENERAZIONE INTEGRITÀ", (g, v) -> g.getBonus().aumentaRigenerazioneIntegrita(v)),
    DANNI_EXTRA("DANNI EXTRA", (g, v) -> g.getBonus().aumentaDanniExtra(v));

    private final String etichetta;
    private final BiConsumer<Giocatore, Integer> modifica;

    Statistica(String etichetta, BiConsumer<Giocatore, Integer> modifica) {
        this.etichetta = etichetta;
        this.modifica = modifica;
    }

    public String getEtichetta() {
        return etichetta;
    }

    public void applica(Giocatore giocatore, int valore) {
        modifica.accept(giocatore, valore);
    }

}
