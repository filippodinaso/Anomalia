package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento;

//Definiamo gli eventi che possono accadere durante uno scontro.
public sealed interface EventoDiGioco {

    //Flusso
    record ScontroIniziato(int minacce) implements EventoDiGioco { }

    record TurnoGiocatoreIniziato(int numero) implements EventoDiGioco { }

    record TurnoNemiciIniziato() implements EventoDiGioco { }

    record ScontroVinto() implements EventoDiGioco { }
    
    //Combattente.subisciDanni
    record DannoInflitto(String sorgente, String bersaglio, int integritaPersa, int scudoAssorbito)
            implements EventoDiGioco { }

    //Combattente.ripristinaIntegrità
    record IntegritaRipristinata(String bersaglio, int quantita) implements EventoDiGioco { }

    //Combattente.aggiungiScudo
    record ScudoApplicato(String bersaglio, int quantita) implements EventoDiGioco { }

    //Cpu.brucia
    record CpuBruciata(String bersaglio, int quantita) implements EventoDiGioco { }

    // ---Stato Entità---
    record NemicoSconfitto(String nemico) implements EventoDiGioco { }
    record GiocatoreSconfitto(String nome) implements EventoDiGioco { }    

   
}