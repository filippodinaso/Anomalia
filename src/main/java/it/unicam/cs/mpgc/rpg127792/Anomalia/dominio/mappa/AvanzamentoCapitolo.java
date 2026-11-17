package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa;

import java.util.HashSet;
import java.util.Set;

//Gestisce i progressi e la posizione separandoli dalla MappaCapitolo fissa
public final class AvanzamentoCapitolo {

    private final MappaCapitolo mappa;
    private final Set<String> completati = new HashSet<>();
    private Nodo attuale;
    private Nodo respawn;

    public AvanzamentoCapitolo(MappaCapitolo mappa) {
        this.mappa = mappa;
        this.attuale = mappa.getPartenza();
        this.respawn = mappa.getPartenza();
    }

    public boolean isAccessibile(Nodo nodo) {
        if (isRivisitabile(nodo)) {
            return true;
        }
        if (nodo.equals(attuale)) {
            return !isCompletato(nodo);
        }
        return isCompletato(attuale) && mappa.getSuccessivi(attuale).contains(nodo);
    }

    public boolean entra(Nodo nodo) {
        if (!isAccessibile(nodo)) {
            return false;
        }
        if (isRivisitabile(nodo)) {
            return true;
        }
        attuale = nodo;
        if (nodo.isPuntoDiSalvataggio()) {
            respawn = nodo;
        }
        return true;
    }

    private boolean isRivisitabile(Nodo nodo) {
        return nodo.isRipetibile() && isCompletato(nodo);
    }

    public void completa(Nodo nodo) {
        completati.add(nodo.getId());
    }

    public boolean isCompletato(Nodo nodo) {
        return completati.contains(nodo.getId());
    }

    public void eseguiRespawn() {
        mappa.getDiscendenti(respawn).forEach(nodo -> completati.remove(nodo.getId()));
       
        if (!respawn.isPuntoDiSalvataggio()) 
            completati.remove(respawn.getId());
        
        attuale = respawn;
    }

    public boolean isConcluso() {
        return isCompletato(attuale) && mappa.getSuccessivi(attuale).isEmpty();
    }

    public StatoCapitolo getStato() {
        return new StatoCapitolo(mappa.getId(), attuale.getId(), respawn.getId(), completati);
    }

    public void ripristina(StatoCapitolo stato) {
        if (!mappa.getId().equals(stato.idCapitolo())) {
            throw new IllegalArgumentException("Stato di un altro capitolo: " + stato.idCapitolo());
        }
        Nodo nuovoAttuale = nodoNoto(stato.idNodoAttuale());
        Nodo nuovoRespawn = nodoNoto(stato.idNodoRespawn());
        stato.nodiCompletati().forEach(this::nodoNoto);
        completati.clear();
        completati.addAll(stato.nodiCompletati());
        attuale = nuovoAttuale;
        respawn = nuovoRespawn;
    }

    private Nodo nodoNoto(String idNodo) {
        return mappa.trovaNodo(idNodo).orElseThrow(() -> new IllegalArgumentException("Nodo sconosciuto: " + idNodo));
    }

    public MappaCapitolo getMappa() { return mappa; }
    public Nodo getAttuale() { return attuale; }
    public Nodo getRespawn() { return respawn; }
}