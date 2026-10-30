package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

//Statistiche upgradabili
public enum  Statistica {
    INTEGRITA_MASSIMA("INTEGRITÀ MAX"),
    CPU_MASSIMA("CPU MAX"),
    RIGENERAZIONE_CPU("RIGENERAZIONE CPU"),
    RIGENERAZIONE_INTEGRITA("RIGENERAZIONE INTEGRITÀ"),
    DANNI_EXTRA("DANNI EXTRA");

    private final String etichetta;

    Statistica(String etichetta) {
        this.etichetta = etichetta;
    }

    public String getEtichetta() {
        return etichetta;
    }

}
