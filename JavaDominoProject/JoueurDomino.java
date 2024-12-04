import java.io.Serializable;

public class JoueurDomino extends Joueur implements Serializable{
    
    public int score;

    public JoueurDomino(String nom){
        this.nom=nom;
        this.score=0;
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public String bienPlacé(int t){
        score+=t;
        return "Bravo "+nom+", vous avez marqué "+t+" points. Vous avez maintenant "+ score+" points.";
    }
}