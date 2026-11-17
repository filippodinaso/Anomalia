package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.persistenza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.RepositoryPartite;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.partita.StatoPartita;

// Conserva la partita in un file JSON
public final class RepositoryPartiteJson implements RepositoryPartite {

    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public RepositoryPartiteJson(File file) {
        this.file = file;
    }

    //restituisce true se il percorso punta a un file realmente esistente sul disco
    @Override
    public boolean esiste() {
        return file.isFile();
    }

    @Override
    public void salva(StatoPartita stato) {
        File cartella = file.getParentFile();
        if (cartella != null) 
            cartella.mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(stato, writer);
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile salvare la partita in " + file, e);
        }
    }

    @Override
    public StatoPartita carica() {
        if (!esiste()) 
            throw new IllegalStateException("Non esiste alcun salvataggio in " + file);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StatoPartita stato = gson.fromJson(reader, StatoPartita.class);
            if (stato == null || stato.giocatore() == null || stato.capitolo() == null) {
                throw new IllegalStateException("Il salvataggio in " + file + " è vuoto o incompleto");
            }
            return stato;
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Il salvataggio in " + file + " non è leggibile o non è valido", e);
        }
    }
}