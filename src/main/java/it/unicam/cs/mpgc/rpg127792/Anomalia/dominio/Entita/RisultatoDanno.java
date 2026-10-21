package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita;

//Esito di un colpo subito: quanto è stato assorbito dallo scudo e quanto ha intaccato l'integrità

public record RisultatoDanno(int integritaPersa, int scudoAssorbito) {

    public int totale() {
        return integritaPersa + scudoAssorbito;
    }
}

