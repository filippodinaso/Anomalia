package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.Entita;

public abstract class Combattente{

    private final String nome;
    private int integrita; //Integrità=vita
    private int integritaMassima; //l'integrità massima può aumentare
    private int scudo; // Il danno viene assorbito prima dallo scudo e poi dall'integrità



//Costruttore

protected Combattente(String nome, int integritaMassima){

    if(integritaMassima <= 0){
        throw new IllegalArgumentException("L'integrità massima non può essere 0 o minore.");
    }
    
    if(nome == null){
        throw new NullPointerException("Il nome non può essere nullo.");
    }

    this.nome=nome;
    this.integritaMassima=integritaMassima;
    this.integrita=integritaMassima;
}

//Metodi

    public RisultatoDanno subisciDanni(int danno) {
        int daAssorbire = Math.max(0, danno); // il danno non può essere negativo
        int assorbito = Math.min(scudo, daAssorbire); // quanto danno incassa lo scudo
        scudo -= assorbito;
        int subito = Math.min(integrita, daAssorbire - assorbito); // quanto danno intacca l'integrità
        integrita -= subito;
        return new RisultatoDanno(subito, assorbito);
    }

    
    public int ripristinaIntegrita(int quantita) {
    
     if (quantita<=0){return 0;}

     int vecchiaIntegrita=integrita;
     integrita += quantita;

     //Se la cura supera l'integrità massima
     if(integrita>integritaMassima){
        integrita=integritaMassima;
     }

    int ripristinati = integrita - vecchiaIntegrita;

     return ripristinati;
    }


    public void aggiungiScudo(int quantita) {
        scudo += Math.max(0, quantita); //l'aggiunta allo scudo non può essere negativa
    }


    public void azzeraScudo() {
        scudo = 0;
    }



    public void aumentaIntegritaMassima(int quantita) {
        int incremento = Math.max(0, quantita);
        integritaMassima += incremento;
        integrita += incremento;
    }

    public boolean isSconfitto() {
        return integrita == 0;
    }

    //Getter
    public String getNome() { return nome; }
    public int getIntegrita() { return integrita; }
    public int getIntegritaMassima() { return integritaMassima; }
    public int getScudo() { return scudo; }


}
