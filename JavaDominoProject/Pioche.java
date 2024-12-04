import java.io.Serializable;

public class Pioche implements Serializable{
    int tuileRestantes;
    TuileDomino [] pioche;

    public TuileDomino jeTirTuile(){
        tuileRestantes-=1;
        return pioche[tuileRestantes];
    }

    public Pioche(int taille){//Constructeur avec une taille défini
        pioche =new TuileDomino[taille];
        for(int i=0;i<taille;i++){
            pioche[i]=new TuileDomino();
        }
        tuileRestantes=taille;
    }

    public Pioche(){//Constructeur de base avec une pioche de 30 tuiles au départ
        pioche =new TuileDomino[30];
        for(int i=0;i<30;i++){
            pioche[i]=new TuileDomino();
        }
        tuileRestantes=30;
    }

    public String tuilesRes(){
        return " Il reste "+tuileRestantes+" tuiles.";
    }
}