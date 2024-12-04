import java.io.Serializable;

public abstract class Tuile implements Serializable{


    public abstract void rotate();
    
    public abstract boolean equals(Tuile t,int c);

    public abstract String [] affichageTuileTab();

    public abstract String affichageTuileSeule();
    
} 