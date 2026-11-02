package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Arsenale;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Cpu;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.CPU_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.DIMENSIONE_DECK;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.INTEGRITA_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.RIGENERAZIONE_CPU_INIZIALE;


//Personaggio controllato dall'utente (daInserireNome)
public class Giocatore extends Combattente {

    //RICORDA CAMBIA NOME !!!!!!!!!!
public static final String NOME="DaInserire";

private final Cpu cpu;
private final Arsenale arsenale;
private Progressione progressione = new Progressione();
private final List<Abilità> arsenaleIniziale;
private final BonusGiocatore bonus;


    public Giocatore(List<Abilità> arsenaleIniziale) {
        super(NOME, INTEGRITA_INIZIALE);
        this.cpu = new Cpu(CPU_INIZIALE, CPU_INIZIALE, RIGENERAZIONE_CPU_INIZIALE);
       
        this.arsenale= new Arsenale(DIMENSIONE_DECK);
        this.arsenaleIniziale=List.copyOf(arsenaleIniziale);
        this.arsenale.inizializza(arsenaleIniziale);

        this.bonus= new BonusGiocatore();
    }

    //rigenerazioni inizio scontro
        public void iniziaScontro() {
        azzeraScudo();
        cpu.ripristina();
        arsenale.azzeraRicariche();
    }

    //rigenerazioni inizio turno
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
    }

    public void aumentaAnomalia() {
    this.progressione.incrementaAnomalia();
    this.aumentaIntegritaMassima(50);
    }
    
    public boolean apprendi(Talento talento) {

    if (progressione.possiedeTalento(talento.id())) {
        return false;
    }
    
    ContestoEffetto contesto = ContestoEffetto.suSeStesso(this, Pubblicatore.nessuno());
    talento.effetti().forEach(effetto -> effetto.applica(contesto));

    return progressione.registraTalento(talento.id());
    }

    public Cpu getCpu() { return cpu; }
    public BonusGiocatore getBonus() { return bonus; }
    public Progressione getProgressione() { return  progressione;   }
}



