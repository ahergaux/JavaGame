import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class PlateauTrax extends Plateau<TuileTrax> implements Serializable{

    public PlateauTrax(){
        this.plateau =new ArrayList<>();
        List<TuileTrax> firstCase=new ArrayList<>();
        firstCase.add(null);
        this.plateau.add(firstCase);
    }

    

    public List<List<String[]>> plateauToString(){
        List<List<String[]>> liste=new ArrayList<>();
        for(int i=0;i<this.getHeight();i++){
            List<String[]> ligne=new ArrayList<>();
            for (int j=0;j<this.getLength();j++){
                if(getTuileAt(i,j)==null){
                    ligne.add(TuileTrax.affichageTuileVide(i,j));
                }else{
                    ligne.add(getTuileAt(i,j).affichageTuileTab());
                }
            }
            liste.add(ligne);
        }
        return liste;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String affichage="";
        List<List<String[]>> tab=this.plateauToString();
        for(int h=0;h<tab.size();h++){
            for(int i=0;i<7;i++){
                for(int j=0;j<tab.get(0).size();j++){
                    affichage += tab.get(h).get(j)[i];
                }
                affichage+="\n";
            }
        }
        return affichage;
    }
    
    public boolean isEmpty(){
        return getHeight()==1 && getLength()==1;
    }

    public boolean sizeOK(){
        return getHeight()<=8 && getLength()<=8;
    }

    @Override
    public void autoResize(){
        if (getLength()<8) {
            for(int i=0;i<getHeight();i++){
                if(plateau.get(i).get(0)!=null){
                    ajoutColonne(0);
                    break;
                }
            }
            for(int i=0;i<getHeight();i++){
                if(plateau.get(i).get(getLength()-1)!=null){
                    ajoutColonne(getLength());
                    break;
                }
            }
        }else if (getLength()==8) {
            Boolean bord1=false;
            Boolean bord2=false;
            for(int i=0;i<getHeight();i++){
                if(plateau.get(i).get(0)!=null){
                    bord1=true;
                }
                if(plateau.get(i).get(getLength()-1)!=null){
                    bord2=true;
                }
            }
            if(!bord1 || !bord2){
                for(int i=0;i<getHeight();i++){
                    if(plateau.get(i).get(0)!=null){
                        ajoutColonne(0);
                        break;
                    }
                }
                for(int i=0;i<getHeight();i++){
                    if(plateau.get(i).get(getLength()-1)!=null){
                        ajoutColonne(getLength());
                        break;
                    }
                }
            }           
        }else{
            for(int i=0;i<getHeight();i++){
                if(plateau.get(i).get(0)!=null){
                    removeColonne(getLength()-1);
                    break;
                }
                if(plateau.get(i).get(getLength()-1)!=null){
                    removeColonne(0);
                    break;
                }
            }
        }
        

        if(getHeight()<8){
            for(int i=0;i<getLength();i++){
                if(plateau.get(0).get(i)!=null){
                    ajoutLigne(0);
                    break;
                }
            }
            for(int i=0;i<getLength();i++){
                if(plateau.get(getHeight()-1).get(i)!=null){
                    ajoutLigne(getHeight());
                    break;
                }
            }
        }else if (getHeight()==8) {
            Boolean bord1=false;
            Boolean bord2=false;
            for(int i=0;i<getLength();i++){
                if(plateau.get(0).get(i)!=null){
                    bord1=true;
                }
                if(plateau.get(getHeight()-1).get(i)!=null){
                    bord2=true;
                }
            }
            if(!bord1 || !bord2){
                for(int i=0;i<getLength();i++){
                    if(plateau.get(0).get(i)!=null){
                        ajoutLigne(0);
                        break;
                    }
                }
                for(int i=0;i<getLength();i++){
                    if(plateau.get(getHeight()-1).get(i)!=null){
                        ajoutLigne(getHeight());
                        break;
                    }
                }
            }
        }else{
            for(int i=0;i<getLength();i++){
                if(plateau.get(0).get(i)!=null){
                    removeLigne(getHeight()-1);
                    break;
                }
                if(plateau.get(getHeight()-1).get(i)!=null){
                    removeLigne(0);
                    break;
                }
            }
        }
        
    }

}