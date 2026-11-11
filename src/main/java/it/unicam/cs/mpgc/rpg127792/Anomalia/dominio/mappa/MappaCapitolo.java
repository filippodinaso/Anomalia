package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.mappa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//Rappresenta la struttura della mappa di un capitolo del gioco (grafo orientato)
public final class MappaCapitolo {

    private final String id;
    private final String nome;
    //id del nodo di partenza del capitolo
    private final String idPartenza;
    //mappa che associa id a nodo
    private final Map<String, Nodo> nodi;
    //mappa che associa ad ogni id di un nodo una lista con tutti i nodi successivi collegati (frecce del grafo)
    private final Map<String, List<String>> archi;

    private MappaCapitolo(Costruttore costruttore) {
        this.id = costruttore.id;
        this.nome = costruttore.nome;
        this.idPartenza = costruttore.idPartenza;
        this.nodi = new LinkedHashMap<>(costruttore.nodi);
        this.archi = new LinkedHashMap<>();
        //copiamo le liste degli archi per renderle immutabili
        costruttore.archi.forEach((nodoPartenza, nodiAdiacenti) -> this.archi.put(nodoPartenza, List.copyOf(nodiAdiacenti)));
    }

    public static Costruttore costruisci(String id, String nome) {
        return new Costruttore(id, nome);
    }

    public Nodo getPartenza() {
        return nodi.get(idPartenza);
    }

    public Optional<Nodo> trovaNodo(String idNodo) {
        Nodo trovato = nodi.get(idNodo);
        return trovato == null ? Optional.empty() : Optional.of(trovato);
    }

    //restituisce i nodi direttamente collegati in uscita dal nodo passato
    public List<Nodo> getSuccessivi(Nodo nodo) {
        List<String> idSuccessivi = archi.get(nodo.getId());
        if (idSuccessivi == null) {
            return List.of();
        }

        List<Nodo> successivi = new ArrayList<>();
        for (String idSuccessivo : idSuccessivi) {
            successivi.add(nodi.get(idSuccessivo));
        }
        return successivi;
    }

    //tutti i nodi raggiungibili dalla partenza
    public List<Nodo> getNodi() {
        return new ArrayList<>(esploraDa(List.of(getPartenza())));
    }

    //tutti i nodi che vengono dopo il nodo passato (per resettarli al respawn)
    public Set<Nodo> getDiscendenti(Nodo nodo) {
        Set<Nodo> discendenti = esploraDa(getSuccessivi(nodo));
        discendenti.remove(nodo);
        return discendenti;
    }

    //metodo di supporto per scorrere i nodi raggiungibili senza avere duplicati
    private Set<Nodo> esploraDa(List<Nodo> iniziali) {
        Set<Nodo> visitati = new LinkedHashSet<>(iniziali);
        List<Nodo> daEsplorare = new ArrayList<>(visitati);

        for (int i = 0; i < daEsplorare.size(); i++) {
            Nodo corrente = daEsplorare.get(i);
            for (Nodo successivo : getSuccessivi(corrente)) {
                //se non l'abbiamo già visitato lo aggiungiamo alla lista da controllare
                if (visitati.add(successivo)) {
                    daEsplorare.add(successivo);
                }
            }
        }
        return visitati;
    }

    //getter
    public String getId() { return id; }
    public String getNome() { return nome; }

    //Builder per costruire la mappa passo passo e controllare che sia valida
    public static final class Costruttore {

        private final String id;
        private final String nome;
        private String idPartenza;
        private final Map<String, Nodo> nodi = new LinkedHashMap<>();
        private final Map<String, List<String>> archi = new LinkedHashMap<>();

        private Costruttore(String id, String nome) {
            if (id == null || id.isBlank() || nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("Id e nome del capitolo sono obbligatori");
            }
            this.id = id;
            this.nome = nome;
        }

        public Costruttore nodo(Nodo nodo) {
            if (nodi.containsKey(nodo.getId())) {
                throw new IllegalArgumentException("Nodo duplicato: " + nodo.getId());
            }
            nodi.put(nodo.getId(), nodo);
            return this;
        }

        public Costruttore partenza(String idNodo) {
            this.idPartenza = idNodo;
            return this;
        }

        //collega due nodi (da -> a)
        public Costruttore arco(String da, String a) {
            if (!archi.containsKey(da)) {
                archi.put(da, new ArrayList<>());
            }
            archi.get(da).add(a);
            return this;
        }

        //controlla che il grafo sia valido prima di creare la mappa
        public MappaCapitolo crea() {
            if (idPartenza == null || !nodi.containsKey(idPartenza)) {
                throw new IllegalStateException("Nodo di partenza mancante o sconosciuto");
            }

            archi.forEach((da, destinazioni) -> {
                if (!nodi.containsKey(da) || !nodi.keySet().containsAll(destinazioni)) {
                    throw new IllegalStateException("Arco verso un nodo sconosciuto: " + da + " -> " + destinazioni);
                }
            });

            MappaCapitolo mappa = new MappaCapitolo(this);
            //controlliamo che non ci siano nodi isolati non raggiungibili dalla partenza
            if (mappa.getNodi().size() != nodi.size()) {
                throw new IllegalStateException("Ci sono nodi non raggiungibili dalla partenza");
            }
            return mappa;
        }
    }
}





