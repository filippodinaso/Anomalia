package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.giocatore;

import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;

//potenziamenti permanenti del giocatore
public record Talento(String id, Ramo ramo, String nome, String descrizione, List<Effetto> effetti) {

    public Talento {
        if (id == null || id.isBlank() || nome == null || nome.isBlank()) 
            throw new IllegalArgumentException("Id e nome sono obbligatori");
        
        if (ramo == null) 
            throw new IllegalArgumentException("Il ramo non può essere nullo");
        
        if (descrizione == null) 
            throw new IllegalArgumentException("La descrizione non può essere nulla");
        
        if (effetti == null || effetti.isEmpty()) 
            throw new IllegalArgumentException("Un talento deve avere almeno un effetto");
        
        effetti = List.copyOf(effetti);
    }
}