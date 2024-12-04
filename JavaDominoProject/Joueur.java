import java.io.Serializable;

public abstract class Joueur implements Serializable{
    String nom;

    public String getNom() {
        return nom;
    }

    
    public abstract String bienPlacé(int t);

    public String tourDe(){
        return "Au tour de "+nom+".";
    }
    
}