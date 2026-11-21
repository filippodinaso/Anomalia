package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Arsenale;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Cpu;

// Dati del giocatore usati per salvarlo e ricostruirlo.
public record IstantaneaGiocatore(
        int integrita, int integritaMassima,
        int cpuMassima, int cpuCorrente, int cpuRigenerazione,
        int rigenerazioneIntegrita, int danniExtra,
        int livelloAnomalia, int frammentiDati, List<String> talenti,
        List<String> idZaino, List<String> idDeck) {

    public IstantaneaGiocatore {
        talenti = List.copyOf(talenti);
        idZaino = List.copyOf(idZaino);
        idDeck = List.copyOf(idDeck);
    }

    //Scatta la fotografia 
    public static IstantaneaGiocatore di(Giocatore giocatore) {
        Cpu cpu = giocatore.getCpu();
        Arsenale arsenale = giocatore.getArsenale();
        return new IstantaneaGiocatore(
                giocatore.getIntegrita(), giocatore.getIntegritaMassima(), //Integrità
                cpu.getMassima(), cpu.getCorrente(), cpu.getRigenerazione(), //Cpu
                giocatore.getBonus().getRigenerazioneIntegrita(), giocatore.getBonus().getDanniExtra(), //Bonus
                giocatore.getProgressione().getLivelloAnomalia(), giocatore.getProgressione().getFrammentiDati(), //progressione
                List.copyOf(giocatore.getProgressione().getTalenti()),  //talenti
                arsenale.getZaino().stream().map(Abilità::id).toList(),  //Abilità sbloccate  
                arsenale.getDeck().stream().map(inUso -> inUso.getDefinizione().id()).toList()); //Abilità equipaggiate
    }

/** Ricostruisce il giocatore usando le abilità risolte dal registro in Partita.
    I talenti vengono registrati senza riapplicarne gli effetti, già inclusi nelle statistiche salvate. */
    public Giocatore ricostruisci(List<Abilità> arsenaleIniziale, List<Abilità> zaino, List<Abilità> deck) {
        controllaCorrispondenza(idZaino, zaino);
        controllaCorrispondenza(idDeck, deck);

        Giocatore giocatore = new Giocatore(
                arsenaleIniziale,
                Cpu.ripristinata(cpuMassima, cpuCorrente, cpuRigenerazione),
                Progressione.ripristinata(livelloAnomalia, frammentiDati, talenti),
                new BonusGiocatore(rigenerazioneIntegrita, danniExtra),
                integritaMassima, integrita);

        Arsenale arsenale = giocatore.getArsenale();
        arsenale.svuota();
        zaino.forEach(arsenale::sblocca);
        for (Abilità abilità : deck) {
            if (!arsenale.equipaggia(abilità.id())) 
                throw new IllegalArgumentException("Deck non valido: " + abilità.id());
        }
        return giocatore;
    }

    private static void controllaCorrispondenza(List<String> idAttesi, List<Abilità> abilità) {
        if (!idAttesi.equals(abilità.stream().map(Abilità::id).toList())) 
            throw new IllegalArgumentException("Le abilità non corrispondono agli id salvati");
    }
}