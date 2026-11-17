package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.innocuo;
import java.util.List;

import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione.Effetto;
import it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico.Nemico;

//definiamo le proprietà di base di un processo innocuo

public record DefinizioneInnocuo(String id, String nome, int turniAlDecadimento, String idNemicoDecaduto, List<Effetto> effettiAssorbi, List<Effetto> effettiElimina) {

    public DefinizioneInnocuo {
      
        if (id == null || id.isBlank() || nome == null || nome.isBlank()) 
            throw new IllegalArgumentException("Id e nome sono obbligatori");

        if (idNemicoDecaduto == null || idNemicoDecaduto.isBlank())
            throw new IllegalArgumentException("Il nemico generato al decadimento è obbligatorio");

        if(turniAlDecadimento<1)
            throw new IllegalArgumentException("L'entità deve esistere almeno per un turno.");
        
        if (effettiAssorbi == null || effettiAssorbi.isEmpty() || effettiElimina == null || effettiElimina.isEmpty())
            throw new IllegalArgumentException("Le scelte devono avere almeno un effetto.");

        effettiAssorbi = List.copyOf(effettiAssorbi);
        effettiElimina = List.copyOf(effettiElimina);
    }


    public ProcessoInnocuo crea(Nemico nemicoAlDecadimento) {
        return new ProcessoInnocuo(nome, turniAlDecadimento, nemicoAlDecadimento, effettiAssorbi, effettiElimina);
    }

}
