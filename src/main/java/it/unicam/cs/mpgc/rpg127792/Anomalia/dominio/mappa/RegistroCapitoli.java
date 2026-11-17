package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Mantiene l'elenco ordinato dei capitoli del gioco
public final class RegistroCapitoli {

    //usiamo LinkedHashMap per mantenere l'ordine in cui registriamo i capitoli della storia
    private final Map<String, DefinizioneCapitolo> definizioni = new LinkedHashMap<>();

    public RegistroCapitoli registra(DefinizioneCapitolo definizione) {

        if (definizioni.containsKey(definizione.getId())) 
            throw new IllegalArgumentException("Capitolo già registrato: " + definizione.getId());
        
        definizioni.put(definizione.getId(), definizione);
        return this;
    }

    //costruisce la mappa del capitolo corrispondente all'id passato
    public MappaCapitolo crea(String idCapitolo) {
        DefinizioneCapitolo definizione = definizioni.get(idCapitolo);

        if (definizione == null) 
            throw new IllegalArgumentException("Capitolo sconosciuto: " + idCapitolo);
        
        return definizione.crea();
    }

    public boolean haSuccessivo(String idCapitolo) {
        List<String> ids = new ArrayList<>(definizioni.keySet());
        int posizione = ids.indexOf(idCapitolo);
        return posizione >= 0 && posizione < ids.size() - 1;
    }

    public String getIdSuccessivo(String idCapitolo) {
        
        if (!haSuccessivo(idCapitolo)) 
            throw new IllegalArgumentException("Nessun capitolo dopo: " + idCapitolo);
        
        List<String> ids = new ArrayList<>(definizioni.keySet());
        return ids.get(ids.indexOf(idCapitolo) + 1);
    }

    //costruisce la mappa del primo capitolo registrato
    public MappaCapitolo creaPrimo() {
        
        if (definizioni.isEmpty()) 
            throw new IllegalStateException("Nessun capitolo registrato");
        
        List<DefinizioneCapitolo> lista = new ArrayList<>(definizioni.values());
        return lista.get(0).crea();
    }
}