package it.unicam.cs.mpgc.rpg127792.Anomalia.archiviazione.contenuti;


import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroAbilità;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroInnocui;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroNemici;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RegistroTalenti;
import it.unicam.cs.mpgc.rpg127792.Anomalia.logica.registri.RepositorioContenuti;

public final class ContenutiDiBase implements RepositorioContenuti {

    private final RegistroAbilità abilità;
    private final RegistroNemici nemici;
    private final RegistroInnocui innocui;
    private final RegistroTalenti talenti;

    //contenuti predefiniti
    public ContenutiDiBase() {
        this(new ContenutiAbilità(), new ContenutiNemici(), new ContenutiInnocui(), new ContenutiTalenti());
    }

    public ContenutiDiBase(RegistroAbilità abilita, RegistroNemici nemici, RegistroInnocui innocui, RegistroTalenti talenti) {
        this.abilità = abilita;
        this.nemici = nemici;
        this.innocui = innocui;
        this.talenti = talenti;
    }

    @Override
    public RegistroAbilità abilità() {
        return abilità;
    }

    @Override
    public RegistroNemici nemici() {
        return nemici;
    }

    @Override
    public RegistroInnocui innocui() {
        return innocui;
    }

    @Override
    public RegistroTalenti talenti() {
        return talenti;
    }
}
