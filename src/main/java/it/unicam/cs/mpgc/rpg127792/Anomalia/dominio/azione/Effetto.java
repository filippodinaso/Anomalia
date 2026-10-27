package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

//Interfaccia per creare gli effetti 

public interface Effetto {

    void applica(ContestoEffetto contesto);

    //Cosa fa l'effetto
    String descrizione();

}
