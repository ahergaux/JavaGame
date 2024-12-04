import java.io.Serializable;

public class AIDomino extends JoueurDomino implements Serializable{


    public AIDomino(int joueur){
        super("ORDI"+joueur+"");
    }

    public String playORDI(TuileDomino t,PlateauDomino p){
        int maxL=-1;
        int maxC=-1;
        int maxScore=0;
        for(int i=0;i<p.getHeight();i++){
            for(int j=0;j<p.getLength();j++){
                for(int r=0;r<4;r++){
                    t.rotate();
                    if(p.peutEtreVoisin(t,i,j) && p.pointGagné(t, i, j)>maxScore){
                        maxScore=p.pointGagné(t, i, j);
                        maxC=i;
                        maxL=j;
                    }
                }
            }
        }
        if(maxC==-1){
            return this.getNom()+" n'as pas pu posé sa tuile\n";
        }
        p.ajoutTuile(t,maxC,maxL);
        return this.bienPlacé(p.pointGagné(t,maxC,maxL))+"";
    }
}