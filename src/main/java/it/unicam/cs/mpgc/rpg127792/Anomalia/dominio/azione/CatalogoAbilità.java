package it.unicam.cs.mpgc.rpg127792.Anomalia.dominio.azione;

//incapsuliamo un'abilità in vendita e il suo costo in un unico oggetto
public record CatalogoAbilità(Abilità abilità, int costo) {
        
    public CatalogoAbilità {
        if (abilità == null) 
            throw new IllegalArgumentException("L'abilità non può essere nulla");
        
        if (costo < 0) 
            throw new IllegalArgumentException("Il costo non può essere negativo");
    }
}
