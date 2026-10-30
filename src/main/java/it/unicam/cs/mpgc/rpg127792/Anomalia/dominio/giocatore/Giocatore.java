package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Abilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Arsenale;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.entita.Cpu;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.CPU_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.DIMENSIONE_DECK;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.INTEGRITA_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.RIGENERAZIONE_CPU_INIZIALE;


//Personaggio controllato dall'utente (daInserireNome)

public class Giocatore extends Combattente {

    //RICORDA CAMBIA NOME !!!!!!!!!!
public static final String NOME="DaInserire";    

private final Cpu cpu;
private int rigenerazioneIntegrita;
private int danniExtra;
private final Arsenale arsenale;
private final List<Abilità> arsenaleIniziale;


    public Giocatore(List<Abilità> arsenaleIniziale) {
        super(NOME, INTEGRITA_INIZIALE);
        this.cpu = new Cpu(CPU_INIZIALE, CPU_INIZIALE, RIGENERAZIONE_CPU_INIZIALE);
       
        this.arsenale= new Arsenale(DIMENSIONE_DECK);
        this.arsenaleIniziale=List.copyOf(arsenaleIniziale);
        this.arsenale.inizializza(arsenaleIniziale);
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
     
        ripristinaIntegrita(rigenerazioneIntegrita);
        cpu.rigenera();
    }

    public void riavvia() {
        ripristinaIntegrita(getIntegritaMassima());
        azzeraScudo();
        cpu.ripristina();
    }



     @Override
    public int bonusDanni() {
        return danniExtra;
    }

    //Aumenta statistiche

        public void modifica(Statistica statistica, int valore) {
        switch (statistica) {
            case INTEGRITA_MASSIMA -> aumentaIntegritaMassima(valore);
            case CPU_MASSIMA -> cpu.aumentaMassima(valore);
            case RIGENERAZIONE_CPU -> cpu.aumentaRigenerazione(valore);
            case RIGENERAZIONE_INTEGRITA -> rigenerazioneIntegrita += valore;
            case DANNI_EXTRA -> danniExtra += valore;
        }
    }


        public Cpu getCpu() { return cpu; }
        public int getRigenerazioneIntegrita() { return rigenerazioneIntegrita; }
}



