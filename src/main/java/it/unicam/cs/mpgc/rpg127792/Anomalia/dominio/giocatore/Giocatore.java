package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Arsenale;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Cpu;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.AUMENTA_INTEGRITA_PERLIVELLO;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.CPU_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.DIMENSIONE_DECK;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.INTEGRITA_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.RIGENERAZIONE_CPU_INIZIALE;



public class Giocatore extends Combattente {


public static final String NOME="Soggetto_Zero";

private final Cpu cpu;
private final Arsenale arsenale;
private final Progressione progressione;
private final List<Abilità> arsenaleIniziale;
private final BonusGiocatore bonus;


public Giocatore(List<Abilità> arsenaleIniziale) {
        this(arsenaleIniziale, new Cpu(CPU_INIZIALE, RIGENERAZIONE_CPU_INIZIALE), new Progressione(), new BonusGiocatore(), INTEGRITA_INIZIALE, INTEGRITA_INIZIALE);
}

    /**
     * Ricostruisce il giocatore con i valori ripristinati dal salvataggio
     * 
     * @param arsenaleIniziale
     * @param cpu
     * @param progressione
     * @param bonus
     * @param integritaMassima
     * @param integrita
     */
    Giocatore(List<Abilità> arsenaleIniziale, Cpu cpu, Progressione progressione, BonusGiocatore bonus, int integritaMassima, int integrita) {
        super(NOME, integritaMassima);
        ripristinaStato(integritaMassima, integrita);
        this.cpu = cpu;
        this.progressione = progressione;
        this.bonus = bonus;
        this.arsenaleIniziale = List.copyOf(arsenaleIniziale);
        this.arsenale = new Arsenale(DIMENSIONE_DECK);
        this.arsenale.inizializza(arsenaleIniziale);
    }


//  Ripristina la CPU, le ricariche delle abilità e azzera lo scudo a inizio scontro.
    public void iniziaScontro() {
        azzeraScudo();
        cpu.ripristina();
        arsenale.azzeraRicariche();
    }

//  Rigenera integrità e CPU all'inizio del turno mentre dal secondo turno aggiorna le ricariche.
    public void iniziaTurno(int numeroTurno) {
        if(numeroTurno>1)
             arsenale.avanzaRicariche();
     
        ripristinaIntegrita(bonus.getRigenerazioneIntegrita());
        cpu.rigenera();
    }

    public void riavvia() {
        ripristinaIntegrita(getIntegritaMassima());
        azzeraScudo();
        cpu.ripristina();
        arsenale.svuota();   //le abilità dovranno essere riacquistate, ciò che farà aumentare di livello il giocatore sono i talenti e l'anomalia
        arsenale.inizializza(arsenaleIniziale); //Solo le abilità iniziali sono permanenti
    }

    public void aumentaAnomalia() {
    this.progressione.incrementaAnomalia();
    this.aumentaIntegritaMassima(AUMENTA_INTEGRITA_PERLIVELLO);
    }
    
    public boolean apprendi(Talento talento) {

    if (progressione.possiedeTalento(talento.id())) {
        return false;
    }
    
    ContestoEffetto contesto = ContestoEffetto.suSeStesso(this, Pubblicatore.nessuno());
    talento.effetti().forEach(effetto -> effetto.applica(contesto));

    return progressione.registraTalento(talento.id());
    }

    @Override
    public int getDanniExtra() { return bonus.getDanniExtra(); }
    public Arsenale getArsenale() { return arsenale; }
    public Cpu getCpu() { return cpu; }
    public BonusGiocatore getBonus() { return bonus; }
    public Progressione getProgressione() { return  progressione;   }
}



