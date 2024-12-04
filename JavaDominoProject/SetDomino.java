import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.Serializable;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SetDomino implements Serializable{

    private final String settingDomino="Settings/settingsDomino.csv";
    private final String score="leaderboardD.csv";
    private final String partieSave="Save/partieSaveD.csv";

    
    public void setNbJ(int n){
        try{
            Path p =Paths.get(settingDomino);
            List<String> ligne=Files.readAllLines(p);
            String [] info=ligne.get(5).split(",");
            ligne.set(5,n+","+info[1]);
            Files.write(p, ligne);
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
    }

    public int getNbJ(){
        String [] info=new String[2];
        try{
            Path p =Paths.get(settingDomino);
            List<String> ligne=Files.readAllLines(p);
            info=ligne.get(5).split(",");
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
        return Integer.parseInt(info[0]);
    }

    public void setNbTuiles(int n){
        try{
            Path p =Paths.get(settingDomino);
            List<String> ligne=Files.readAllLines(p);
            String [] info=ligne.get(5).split(",");
            ligne.set(5,info[0]+","+n);
            Files.write(p, ligne);
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
    }


    public int getNbTuiles(){
        String [] info=new String[2];
        try{
            Path p =Paths.get(settingDomino);
            List<String> ligne=Files.readAllLines(p);
            info=ligne.get(5).split(",");
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
        return Integer.parseInt(info[1]);
    }

    public String getLD(){
        String s="";
        try{
            Path p =Paths.get(score);
            List<String> ligne=Files.readAllLines(p);
            String [][] info=new String[ligne.size()][3];
            for(int i=5;i<ligne.size();i++){
                info[i-5]=ligne.get(i).split(",");
            }
            for(int i=0;i<ligne.size()-5;i++){
                s+="#"+(i+1)+"  Nom :"+info[i][0]+", Nombre de parties :"+info[i][1]+", Nombres de points :"+info[i][2]+"\n";
            }
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
        return s;
    }
    
    public void trieLD(){
        try{
            Path p =Paths.get(score);
            List<String> ligne=Files.readAllLines(p);
            String [][] info=new String[ligne.size()-5][3];
            for(int i=5;i<ligne.size();i++){
                info[i-5]=ligne.get(i).split(",");
            }
            Boolean isSort=false;
            String[] temp=new String[3];
            while(!isSort){
                isSort=true;
                for(int i=0;i<ligne.size()-6;i++){
                    if(Integer.parseInt(info[i][2])<Integer.parseInt(info[i+1][2])){
                        isSort=false;
                        for(int j=0;j<3;j++){
                            temp[j]=info[i][j];
                            info[i][j]=info[i+1][j];
                            info[i+1][j]=temp[j];
                        }
                    }
                }
            }
            for(int i=0;i<ligne.size()-5;i++){
                ligne.set(i+5,info[i][0]+","+info[i][1]+","+info[i][2]);
            }
            Files.write(p,ligne);
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
    }   


    public void actualizeLB(JoueurDomino j){
        try{
            Path p =Paths.get(score);
            List<String> ligne=Files.readAllLines(p);
            String [][] info=new String[ligne.size()][3];
            for(int i=5;i<ligne.size();i++){
                info[i-5]=ligne.get(i).split(",");
            }
            for(int i=0;i<ligne.size()-1;i++){
                if(j.getNom().equals(info[i][0])){
                    if(Integer.parseInt(info[i][2])<j.score){
                        ligne.set(5+i,info[i][0]+","+(Integer.parseInt(info[i][1])+1)+","+j.score);
                    }else{
                        ligne.set(5+i,info[i][0]+","+(Integer.parseInt(info[i][1])+1)+","+info[i][2]);
                    }
                    Files.write(p, ligne);
                    this.trieLD();
                    return;
                }
            }
            ligne.add(j.getNom()+","+1+","+j.score+"");
            Files.write(p, ligne);
            this.trieLD();
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
    }

    

    public String sauvegarde(JeuDomino jeu){
        String nom=setSave();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nom+".ser"))) {
            oos.writeObject(jeu);
            System.out.println("Partie sauvegardé ! Votre partie porte le nom :"+nom);
            return nom;
        } catch (Exception e) {
            System.out.println("File not found");
        }
        return "Erreur, partie non sauvegardé.";
    }

    public JeuDomino load(int i){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("partieDomino"+i+".ser"))) {
            JeuDomino jeu= (JeuDomino) ois.readObject();
            System.out.println("Partie chargé !");
            this.remSave("partieDomino"+i);
            return jeu;
        } catch (Exception e) {
            System.out.println("Fichier corrompu.");
        }
        return null;
    }

    public String getSave(){
        String s="";
        try{
            Path p =Paths.get(partieSave);
            List<String> ligne=Files.readAllLines(p);
            String [][] info=new String[ligne.size()][3];
            for(int i=5;i<ligne.size();i++){
                info[i-5]=ligne.get(i).split(",");
            }
            for(int i=0;i<ligne.size()-5;i++){
                s+="#"+(i)+"  Nom :"+info[i][0]+"\t\n";
            }
        }catch(IOException e){
            System.out.println("Error : Save file not found");
        }

        return s;
    }

    public String setSave(){
        try{
            Path p =Paths.get(partieSave);
            List<String> ligne=Files.readAllLines(p);
            ligne.add("partieDomino"+(ligne.size()-5));
            Files.write(p, ligne);
            return "partieDomino"+(ligne.size()-6);
        }catch(IOException e){
            System.out.println("Error : Save file not found");
        }
        return "";
    }

    public void remSave(String s){
        try{
            Path p =Paths.get(partieSave);
            List<String> ligne=Files.readAllLines(p);
            for(int i =5;i<ligne.size();i++){
                if(ligne.get(i).equals(s)){
                    ligne.remove(i);
                    break;
                }
            }
            Files.write(p, ligne);
        }catch(IOException e){
            System.out.println("Error : Save file not found");
        }
    }
}