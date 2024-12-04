import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class TuileTrax extends Tuile implements Serializable{
    //On prendra comme valeur pour noir=1 et pour blanc=0
    //On codera la face de la tuile par un numero soit 0 soit 1
    //La face 0 sera :
    /*      |
            |
      -------------
            |
            |
     */
    //Et la face 1 sera
    /*
            |
           /    _
        _ /   / 
            /
           |
    
    */
    private int [][] tuile;
    private int face;
    private int orientation;


    public TuileTrax(){
        face=0;
        orientation=0;
        this.tuile=new int[2][4];
        for(int i=0;i<4;i++){
            this.tuile[0][i]=i%2;
            this.tuile[1][i]=i/2;
        }
    }

    public int getTuile(int side) {
        return tuile[face][side];
    }

    public int getFace() {
        return face;
    }

    public int getOrientation() {
        return orientation;
    }

    
    
    public void changeFace(){
        face=(face+1)%2;
    }

    

    /**
     *Permit to rotate tuile
     */
    @Override
    public void rotate(){
        int i0=tuile[1][3];
        int i1;
        for(int i=0;i<4;i++){
            this.tuile[0][i]=(this.tuile[0][i]+1)%2;
            i1=tuile[1][i];
            tuile[1][i]=i0;
            i0=i1;
        }
        orientation=(orientation+1)%4;
    }
    
    @Override
    public boolean equals(Tuile o,int c){//la composante c ici représnente le coté qu'on veut comparer par rapport à this.tuile
        if(o==null){
            return true;
        } 
        if(o.getClass()!=getClass()){
            return false;
        }
        TuileTrax t=(TuileTrax)o;
        return this.tuile[face][c]==t.tuile[t.face][((c+2)%4)];
    }
    
    @Override
    public String affichageTuileSeule(){
        String aff ="";
        if(face==0){
            if(tuile[0][0]==1){
                aff ="""
                     
                             \u2b1b        
                             \u2b1b        
                             \u2b1b        
                     \u2b1c\u2b1c\u2b1c\u2b1c\u2b1b\u2b1c\u2b1c\u2b1c\u2b1c
                             \u2b1b.       
                             \u2b1b        
                             \u2b1b        
                     """;
            }
            if(tuile[0][0]==0){
                aff ="""
                     
                             \u2b1c        
                             \u2b1c        
                             \u2b1c        
                     \u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b
                             \u2b1c        
                             \u2b1c        
                             \u2b1c        
                     """;
            }
        }else{
            switch (orientation) {
                case 0 -> aff ="""
                               
                                       \u2b1c      
                                       \u2b1c      
                                         \u2b1c    
                               \u2b1b\u2b1b\u2b1b      \u2b1c\u2b1c\u2b1c
                                     \u2b1b        
                                       \u2b1b      
                                       \u2b1b      
                               """;
                case 3 -> aff ="""
                               
                                       \u2b1c      
                                       \u2b1c      
                                     \u2b1c        
                               \u2b1c\u2b1c\u2b1c      \u2b1b\u2b1b\u2b1b
                                         \u2b1b    
                                       \u2b1b      
                                       \u2b1b      
                               """;
                case 2 -> aff ="""
                               
                                       \u2b1b      
                                       \u2b1b      
                                         \u2b1b    
                               \u2b1c\u2b1c\u2b1c      \u2b1b\u2b1b\u2b1b
                                     \u2b1c        
                                       \u2b1c      
                                       \u2b1c      
                               """;
                case 1 -> aff ="""
                               
                                       \u2b1b      
                                       \u2b1b      
                                     \u2b1b        
                               \u2b1b\u2b1b\u2b1b      \u2b1c\u2b1c\u2b1c
                                         \u2b1c    
                                       \u2b1c      
                                       \u2b1c      
                               """;
                default -> {
                }
            }
        }
        
        return aff;
    }

    @Override
    public String [] affichageTuileTab(){
        String [] aff =new String[7];
        if(face==0){
            if(tuile[0][0]==1){
                
            aff[0]=            "        ⬛        ";
            aff[1]=            "        ⬛        ";
            aff[2]=            "        ⬛        ";
            aff[3]=            "⬜⬜⬜⬜⬛⬜⬜⬜⬜";
            aff[4]=            "        ⬛        ";
            aff[5]=            "        ⬛        ";
            aff[6]=            "        ⬛        ";
            }
            if(tuile[0][0]==0){
            aff[0]=            "        ⬜        ";
            aff[1]=            "        ⬜        ";
            aff[2]=            "        ⬜        ";
            aff[3]=            "⬛⬛⬛⬛⬛⬛⬛⬛⬛";
            aff[4]=            "        ⬜        ";
            aff[5]=            "        ⬜        ";
            aff[6]=            "        ⬜        ";
            }
        }else{
            switch (orientation) {
                case 0 -> {
                    aff[0]=            "        ⬜        ";
                    aff[1]=            "        ⬜        ";
                    aff[2]=            "          ⬜      ";
                    aff[3]=            "⬛⬛⬛      ⬜⬜⬜";
                    aff[4]=            "      ⬛          ";
                    aff[5]=            "        ⬛        ";
                    aff[6]=            "        ⬛        ";
                }
                case 3 -> {
                    aff[0]=            "        ⬜        ";
                    aff[1]=            "        ⬜        ";
                    aff[2]=            "      ⬜          ";
                    aff[3]=            "⬜⬜⬜      ⬛⬛⬛";
                    aff[4]=            "          ⬛      ";
                    aff[5]=            "        ⬛        ";
                    aff[6]=            "        ⬛        ";
                }
                case 2 -> {
                    aff[0]=            "        ⬛        ";
                    aff[1]=            "        ⬛        ";
                    aff[2]=            "          ⬛      ";
                    aff[3]=            "⬜⬜⬜      ⬛⬛⬛";
                    aff[4]=            "      ⬜          ";
                    aff[5]=            "        ⬜        ";
                    aff[6]=            "        ⬜        ";
                }
                case 1 -> {
                    aff[0]=            "        ⬛        ";
                    aff[1]=            "        ⬛        ";
                    aff[2]=            "      ⬛          ";
                    aff[3]=            "⬛⬛⬛      ⬜⬜⬜";
                    aff[4]=            "          ⬜      ";
                    aff[5]=            "        ⬜        ";
                    aff[6]=            "        ⬜        ";
                }
                default -> {
                    System.out.println("Orientation error");
                }
            }
        }
        
        return aff;
    }

    public static String [] affichageTuileVide(int ligne,int colonne){
        String []aff = new String[7];
        aff[0]="                  ";
        aff[1]="                  ";
        aff[2]="                  ";
        aff[3]= "        "+(char)('A'+ligne)+(colonne)+"        ";
        aff[4]="                  ";
        aff[5]="                  ";
        aff[6]="                  ";

        return aff;
        
    }

    public JLabel drawTuile(){
        try {
            BufferedImage originalImage = ImageIO.read(new File("TuileTrax/tuile"+face+".png"));
            double angle= Math.PI/2.0*(orientation-1);
            AffineTransform tx = AffineTransform.getRotateInstance(angle, originalImage.getWidth() / 2.0, originalImage.getHeight() / 2.0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage rotatedImage = op.filter(originalImage, null);
            ImageIcon icon = new ImageIcon(rotatedImage);
            return new JLabel(icon); 
        }catch(IOException e){
            System.out.println("file not found");
        }
        return null;
    }
}