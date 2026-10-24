package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita.Combattente;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita.Cpu;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.CPU_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.INTEGRITA_INIZIALE;
import static it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.StatisticaIniziale.RIGENERAZIONE_CPU_INIZIALE;


//Personaggio controllato dall'utente (daInserireNome)

public class Giocatore extends Combattente {

    //RICORDA CAMBIA NOME !!!!!!!!!!
public static final String NOME="DaInserire";    

private final Cpu cpu;
private int rigenerazioneIntegrita;

    public Giocatore() {
        super(NOME, INTEGRITA_INIZIALE);
        this.cpu = new Cpu(CPU_INIZIALE, CPU_INIZIALE, RIGENERAZIONE_CPU_INIZIALE);
    }

    //rigenerazioni inizio scontro
        public void iniziaScontro() {
        azzeraScudo();
        cpu.ripristina();
    }

    //rigenerazioni inizio turno
        public void iniziaTurno() {
        ripristinaIntegrita(rigenerazioneIntegrita);
        cpu.rigenera();
    }



    //Aumenta statistiche

        public void modifica(Statistica statistica, int valore) {
        switch (statistica) {
            case INTEGRITA_MASSIMA -> aumentaIntegritaMassima(valore);
            case CPU_MASSIMA -> cpu.aumentaMassima(valore);
            case RIGENERAZIONE_CPU -> cpu.aumentaRigenerazione(valore);
            case RIGENERAZIONE_INTEGRITA -> rigenerazioneIntegrita += valore;
        }
    }


        public Cpu getCpu() { return cpu; }
        public int getRigenerazioneIntegrita() { return rigenerazioneIntegrita; }
}



