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

public class SetTrax implements Serializable{

    private final String score="leaderboardT.csv";
    private final String partieSave="Save/partieSaveT.csv";
    
    public void actualizeLB(JoueurTrax j,int g){
        try{
            Path p =Paths.get(score);
            List<String> ligne=Files.readAllLines(p);
            String [][] info=new String[ligne.size()][3];
            for(int i=5;i<ligne.size();i++){
                info[i-5]=ligne.get(i).split(",");
            }
            for(int i=0;i<ligne.size();i++){
                if(j.getNom().equals(info[i][0])){
                    if(g==1){
                        ligne.set(5+i,info[i][0]+","+(Integer.parseInt(info[i][1])+1)+","+(Integer.parseInt(info[i][2])+g));
                        Files.write(p, ligne);
                    }else{
                        ligne.set(5+i,info[i][0]+","+(Integer.parseInt(info[i][1])+1)+","+info[i][2]);
                        Files.write(p, ligne);
                    }
                    this.trieLD();
                    return;
                }
            ligne.add(j.getNom()+","+1+","+g+"");
            Files.write(p,ligne);
            this.trieLD();
            return;
            }
        }catch(IOException e){
            System.out.println("Error : Setting file not found");
        }
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
                s+="#"+(i+1)+"  Nom :"+info[i][0]+", Nombre de parties :"+info[i][1]+", Nombres de parties gagné :"+info[i][2]+"\n";
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


    public String sauvegarde(JeuTrax jeu){
        String nom=setSave();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nom+".ser"))) {
            oos.writeObject(jeu);
            System.out.println("Partie sauvegardé ! Votre partie porte le nom :"+nom);
            return nom;
        } catch (Exception e) {
            System.out.println("Fichier corrompu.");

        }
        return "Erreur, partie non sauvegardé.";
    }

    public JeuTrax load(int i){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("partieTrax"+i+".ser"))) {
            JeuTrax jeu= (JeuTrax) ois.readObject();
            System.out.println("Partie chargé !");
            this.remSave("partieTrax"+i);
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
            ligne.add("partieTrax"+(ligne.size()-5));
            Files.write(p, ligne);
            return "partieTrax"+(ligne.size()-6);
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