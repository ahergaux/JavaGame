import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.*;


public class TuileDomino extends Tuile implements Serializable{
    private int [][] tuile;//Architechture de case hhh ddd bbb ggg
    
    public TuileDomino(){//Constructeur de base
        this.tuile =new int[4][3];
        Random r=new Random();
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                this.tuile[i][j]=Math.abs(r.nextInt()%4);
            }
        }
    }

    public int getValue(int side,int n) {
        return tuile[side][n];
    }
 
    
    

    @Override
    public boolean equals(Tuile o,int c){//la composante c ici représnente le coté qu'on veut comparer par rapport à this.tuile
        if(o==null){
            return true;
        } 
        if(o.getClass()!=getClass()){
            return false;
        }
        TuileDomino t=(TuileDomino)o;
        
        

        for(int i=0;i<3;i++){
            if(this.tuile[c][i]!=t.tuile[(c+2)%4][2-i]){
                return false;
            }
        }

        return true;
    }
    
    /**
     *
     */
    @Override
    public void rotate(){
        int [] tempon1= new int[3];
        int [] tempon2= new int[3];
        System.arraycopy(tuile[0], 0, tempon1, 0, 3);
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                tempon2[j]=tuile[(i+1)%4][j];
                this.tuile[(i+1)%4][j]=tempon1[j];
                tempon1[j]=tempon2[j];
            }
        }
    }
    


    public static String [] affichageTuileVide(int ligne,int colonne){
        String []aff = new String[9];
        aff[0]="                 ";
        aff[1]="                 ";
        aff[2]="                 ";
        aff[4]= "        "+(char)('A'+ligne)+(colonne)+"       ";
        aff[3]="                 ";
        aff[5]="                 ";
        aff[6]="                 ";
        aff[7]="                 ";
        aff[8]="                 ";
        return aff;
        
    }

    /**
     *
     * @return
     */
    @Override
    public  String [] affichageTuileTab(){
        String []aff = new String[9];
        aff[0]=                  "-----------------";
        aff[1]=                  "|  |"+this.tuile[0][0]+"| |"+this.tuile[0][1]+"| |"+this.tuile[0][2]+"|  |";
        aff[2]=                  "-----------------";
        aff[3]=                  "|"+this.tuile[3][2]+"|           |"+this.tuile[1][0]+"|";
        aff[4]=                  "|"+this.tuile[3][1]+"|           |"+this.tuile[1][1]+"|";
        aff[5]=                  "|"+this.tuile[3][0]+"|           |"+this.tuile[1][2]+"|";
        aff[6]=                  "-----------------";
        aff[7]=                  "|  |"+this.tuile[2][2]+"| |"+this.tuile[2][1]+"| |"+this.tuile[2][0]+"|  |";
        aff[8]=                  "-----------------";
        return aff;
        
    }

    /**
     *
     * @return
     */
    @Override
    public String affichageTuileSeule(){
        String aff ="-----------------\n"
        +                  "|  |"+this.tuile[0][0]+"| |"+this.tuile[0][1]+"| |"+this.tuile[0][2]+"|  |\n"
        +                  "-----------------\n"
        +                  "|"+this.tuile[3][2]+"|           |"+this.tuile[1][0]+"|\n"
        +                  "|"+this.tuile[3][1]+"|           |"+this.tuile[1][1]+"|\n"
        +                  "|"+this.tuile[3][0]+"|           |"+this.tuile[1][2]+"|\n"
        +                  "-----------------\n"
        +                  "|  |"+this.tuile[2][2]+"| |"+this.tuile[2][1]+"| |"+this.tuile[2][0]+"|  |\n"
        +                  "-----------------\n";
        return aff;
        
    }

    public JLabel drawTuile(){
        try {
            BufferedImage image = ImageIO.read(new File("TuileDomino/tuile.png"));
            Graphics2D g2d = image.createGraphics();
            Font font = new Font("Arial",Font.CENTER_BASELINE, 20);
            g2d.setFont(font);
            for(int i=0;i<3;i++){
                drawNumber(g2d, 41+i*26, 30,tuile[0][i]);
            }
            for(int i=0;i<3;i++){
                drawNumber(g2d, 125, 57+i*24,tuile[1][i]);
            }
            for(int i=0;i<3;i++){
                drawNumber(g2d, 93-i*26,144,tuile[2][i]);
            }
            for(int i=0;i<3;i++){
                drawNumber(g2d, 17, 105-i*24,tuile[3][i]);
            }
            ImageIcon icon = new ImageIcon(image);
            return new JLabel(icon); 
        }catch(IOException e){
            System.out.println("File 'tuile.png' not found ");
        }
        return null;
    }
    private static void drawNumber(Graphics2D g2d, int x, int y, int number) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(number), x, y);
    }
}