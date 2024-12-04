import java.io.Serializable;

public class JoueurTrax extends Joueur implements Serializable{

    public JoueurTrax(String nom){
        this.nom=nom;
    }
    
    @Override
    public String bienPlacé(int t){
        return "Bien placé "+ nom+" !";
    }
}