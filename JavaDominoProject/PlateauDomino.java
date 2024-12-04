import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class PlateauDomino extends Plateau<TuileDomino> implements Serializable{

    public PlateauDomino(){
        this.plateau =new ArrayList<>();
        List<TuileDomino> firstCase=new ArrayList<>();
        firstCase.add(new TuileDomino());
        this.plateau.add(firstCase);
    }

    /**
     *
     * @return
     */
    public List<List<String[]>> plateauToString(){
        List<List<String[]>> liste=new ArrayList<>();
        for(int i=0;i<this.plateau.size();i++){
            List<String[]> ligne=new ArrayList<>();
            for (int j=0;j<this.plateau.get(0).size();j++){
                if(getTuileAt(i,j)==null){
                    ligne.add(TuileDomino.affichageTuileVide(i,j));
                }else{
                    ligne.add(getTuileAt(i,j).affichageTuileTab());
                }
            }
            liste.add(ligne);
        }
        return liste;
    }
    
    @Override
    public String toString(){
        String affichage="";
        List<List<String[]>> tab=this.plateauToString();
        for(int h=0;h<tab.size();h++){
            for(int i=0;i<9;i++){
                for(int j=0;j<tab.get(0).size();j++){
                    affichage += tab.get(h).get(j)[i];
                }
                affichage+="\n";
            }
        }
        return affichage;
    }


    public int pointGagné(TuileDomino current,int x, int y){
        List<TuileDomino> t=this.determineVoisin(x,y);
        int points=0;
        for(int i=0;i<4;i++){
            if(t.get(i)!=null){
                points+=current.getValue(i,0)+current.getValue(i,1)+current.getValue(i,2);
            }
        }
        return points;
    }

    public void autoResize(){
        for(int i=0;i<plateau.get(0).size();i++){
            if(plateau.get(0).get(i)!=null){
                ajoutLigne(0);
                break;
            }
        }
        for(int i=0;i<plateau.get(0).size();i++){
            if(plateau.get(plateau.size()-1).get(i)!=null){
                ajoutLigne(plateau.size());
                break;
            }
        }
        for(int i=0;i<plateau.size();i++){
            if(plateau.get(i).get(0)!=null){
                ajoutColonne(0);
                break;
            }
        }
        for(int i=0;i<plateau.size();i++){
            if(plateau.get(i).get(plateau.get(i).size()-1)!=null){
                ajoutColonne(plateau.get(0).size());
                break;
            }
        }
    }

}