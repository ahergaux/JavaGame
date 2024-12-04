import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.Serializable;


public class Chemin implements Serializable{
    
    Map<TuileTrax,Integer> map=new HashMap<>();
    int nbreChemin;
    int couleur;
    PlateauTrax p;

    public Chemin(int couleur, PlateauTrax plateau){
        nbreChemin=1;
        this.couleur=couleur;
        this.p=plateau;
    }
    
    public boolean isGagnant(TuileTrax t, int ligne, int colonne){

        if(map.isEmpty()){
            map.put(t,nbreChemin);
            nbreChemin++;
            return false;
        }
        List<TuileTrax> voisins=p.determineVoisin(ligne,colonne);
        Boolean empty=true;
        int i=0;
        for(i=0;i<4;i++){
            if(voisins.get(i)!=null){
                if(t.getTuile(i)==couleur){
                    int x=map.get(voisins.get(i));
                    map.put(t,x);
                    i++;
                    empty=false;
                    break;
                }
            }
        }
        if(empty){
            map.put(t,nbreChemin);
            nbreChemin++;
            return false;
        }
        for(;i<4;i++){
            if (voisins.get(i)!=null && t.getTuile(i)==couleur) {
                if (map.get(voisins.get(i)).equals(map.get(t))){
                    return true;
                }else{
                    int x = map.get(voisins.get(i));
                    for(Map.Entry<TuileTrax, Integer> entry: map.entrySet()){
                        if(entry.getValue()==x){
                            map.put(entry.getKey(),map.get(t));
                        }
                    }
                }
            }
        }

        return false;
    }



    public boolean eightWay(){
        int hauteur=p.getHeight();
        int longeur=p.getLength();
        for(int j=0;j<longeur;j++){
            TuileTrax current=p.getTuileAt(0,j);
            if (current!=null) {
                for(int h=0;h<longeur;h++){
                    if(Objects.equals(map.get(p.getTuileAt(hauteur-1,h)), map.get(current))){
                        return true;
                    }
                }
            }
        }
        for(int j=0;j<hauteur;j++){
            TuileTrax current=p.getTuileAt(j,0);
            if (current!=null) {
                for(int h=0;h<hauteur;h++){
                    if(Objects.equals(map.get(p.getTuileAt(h,longeur-1)), map.get(current))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void affiche(Chemin args){
            for(Map.Entry<TuileTrax, Integer> entry: args.map.entrySet()){
            System.out.println(entry);
        }
    }
}