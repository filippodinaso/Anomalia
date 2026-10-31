package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.nemico;

public record DefinizioneNemico(String id, String nome, Scaling integrita, Scaling frammentiDati, FabbricaComportamento comportamento) {

    public DefinizioneNemico {
        if (id == null || id.isBlank() || nome == null || nome.isBlank()) 
            throw new IllegalArgumentException("Id e nome sono obbligatori");
        
        if (integrita == null || frammentiDati == null || comportamento == null) 
            throw new NullPointerException("L'integrità, i frammentiDati e il comportamento non possono essere null.");
    }    

    //creiamo un'istanza nel nemico tenendo conto del livello del giocatore e del rango del nemico
    //il rango sarà il livello base del nemico (un livello più alto sarà popolato da nemici di rango più alto)
    public Nemico crea(int livelloAnomalia, int rango) {
        int livello = rango + livelloAnomalia;
        return new Nemico(nome, livello, integrita.valore(livello), frammentiDati.valore(livello), comportamento.crea(livello));
    }
}

