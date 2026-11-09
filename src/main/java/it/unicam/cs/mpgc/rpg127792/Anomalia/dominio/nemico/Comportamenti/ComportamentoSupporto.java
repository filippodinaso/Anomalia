package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamenti;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.ContestoEffetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.DannoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.effetti.ScudoDiretto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.EventoDiGioco;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.evento.Pubblicatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore.Giocatore;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Comportamento;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

 //Protegge con uno scudo il primo alleato in difficoltà (quindi che ha metà integrità o meno), poi deve ricaricarsi
 //Se non c'è nessuno in difficoltà ma la sua "abilità" è carica, supporta se stesso,quando non può o non serve, attacca.
public final class ComportamentoSupporto implements Comportamento {

    private final int danno;
    private final int scudo;
    private final int ricarica;
    private int ricaricaRimanente;

    public ComportamentoSupporto(int danno, int scudo, int ricarica) {
        this.danno = danno;
        this.scudo = scudo;
        this.ricarica = ricarica;
    }

    private static boolean inDifficoltà(Nemico nemico) {
        //Verifichiamo se un nemico ha la metà o meno dell'integrità massima e non è stato sconfitto per capire se è o meno in difficoltà
        return !nemico.isSconfitto() && nemico.getIntegrita() * 2 <= nemico.getIntegritaMassima();
    }

 @Override
    public void agisci(Nemico esecutore, Giocatore giocatore, List<Nemico> alleati, Pubblicatore<EventoDiGioco> eventi) {
        
        if (ricaricaRimanente > 0) {
            ricaricaRimanente--;
        }

        Nemico inDifficoltà = null;

        if (ricaricaRimanente == 0) {
            //cicliamo cercando un nemico che ritorni true al metodo inDifficoltà
            for (Nemico alleato : alleati) {
                if (ComportamentoSupporto.inDifficoltà(alleato)) {
                    inDifficoltà = alleato;
                    break; // Trovato il primo, interrompiamo la ricerca
                }
            }
        }

        if (inDifficoltà != null) {
            new ScudoDiretto(scudo).applica(ContestoEffetto.suSeStesso(inDifficoltà, eventi));
            ricaricaRimanente = ricarica;
        } else {
            DannoDiretto.attacco(danno).applica(ContestoEffetto.contro(esecutore, giocatore, eventi));
        }
    }

}

