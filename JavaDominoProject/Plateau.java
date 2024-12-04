import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Arrays;

public abstract class Plateau <T extends Tuile>implements Serializable{
    
    protected List<List<T>> plateau;

    public List<List<T>> getPlateau() {
        return plateau;
    }

    public int getHeight(){
        return plateau.size();
    }
    
    public int getLength(){
        return plateau.get(0).size();
    }
    
    public T getTuileAt(int i,int j){
        return plateau.get(i).get(j);
    }
    
    
    @Override
    public abstract String toString();

    public boolean peutEtreVoisin(T c, int x, int y) {
        List<T> voisins = determineVoisin(x, y);
       
        int compteur = 0;
        for (T t : voisins) {
            if (t != null) {
                compteur += 1;
            }
        }
        if (compteur == 0) {
            return false;
        }
        
        for (int i=0;i<4;i++){
            if(!c.equals(voisins.get(i),i)){
                return false;
            }
        }
        return true;
    }

    public void removeLigne(int index){
        plateau.remove(index);    
    }

    public void removeColonne(int index){
        int hauteur=getHeight();
        for(int i=0;i<hauteur;i++){
            plateau.get(i).remove(index);
        }
    }
    
    public void ajoutLigne(int index){
        List<T> newLine= new ArrayList<>();
        for (T get : plateau.get(0)) {
            newLine.add(null);
        }    
        plateau.add(index,newLine);
    }

    public void ajoutColonne(int index){
        for(List<T> l : this.plateau){
            l.add(index, null);
        }
    }
    

    
    public List<T>  determineVoisin(int ligne,int colonne){

        List<T> voisins = new ArrayList<>(Arrays.asList(null, null, null, null));
        
        if(ligne>0){
            voisins.set(0,getTuileAt(ligne-1,colonne));

        }
        if(colonne<getLength()-1){
            voisins.set(1,getTuileAt(ligne,colonne+1));
        }
        if(ligne<getHeight()-1){
            voisins.set(2,getTuileAt(ligne+1,colonne));
        }
        if(colonne>0){
            voisins.set(3,getTuileAt(ligne,colonne-1));
        }

        return voisins;
    }
    
    

    public void ajoutTuile(T t,int ligne,int colonne){
        this.plateau.get(ligne).set(colonne,t);
    }
    

    public boolean StringToCaseOK(String c,int[] t){
        t[0]=(int)c.charAt(0)-65;
        t[1]=(int)c.charAt(1)-48;
        return t[0]>=0 && t[0]<getHeight() && t[1]>=0 && t[1]<getLength();
    }

    public abstract void autoResize();
}