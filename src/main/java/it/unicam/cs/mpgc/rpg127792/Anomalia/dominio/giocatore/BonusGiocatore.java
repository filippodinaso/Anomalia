package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

// Gestisce i bonus passivi del giocatore (rigenerazione integrità e danni extra)
public final class BonusGiocatore {

    private int rigenerazioneIntegrita;
    private int danniExtra;

    public BonusGiocatore() {
        this(0, 0);
    }

    public BonusGiocatore(int rigenerazioneIntegrita, int danniExtra) {
        this.rigenerazioneIntegrita = rigenerazioneIntegrita;
        this.danniExtra = danniExtra;
    }

    public void aumentaRigenerazioneIntegrita(int valore) {
        this.rigenerazioneIntegrita += valore;
    }

    public void aumentaDanniExtra(int valore) {
        this.danniExtra += valore;
    }

    public int getRigenerazioneIntegrita() {
        return rigenerazioneIntegrita;
    }

    public int getDanniExtra() {
        return danniExtra;
    }
}