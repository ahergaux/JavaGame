import java.io.Serializable;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JeuDomino extends JPanel implements Serializable{
    
    JoueurDomino[] joueurs;
    int nbJ;
    Pioche pioche;
    PlateauDomino plateau;
    int tour=0;
    TuileDomino current;

    

    public JeuDomino(int nbTuiles,int n,int mode){
        Scanner s=new Scanner(System.in);
        joueurs=new JoueurDomino[n];
        String nom;
        pioche=new Pioche(nbTuiles);
        plateau=new PlateauDomino();
        tour=0;
        if (mode==0) {
            for(int i=0;i<n;i++){
                System.out.println("Entrez le nom du joueur "+(i+1)+" ou AI pour que le joueur soit un ordi :");
                if((nom=s.nextLine()).equals("AI")){
                    joueurs[i]=new AIDomino(nbJ);
                }else{
                    joueurs[i]=new JoueurDomino(nom);
                }
                nbJ+=1;
            }
        }else{
            for(int i=0;i<n;i++){
                nom=JOptionPane.showInputDialog(this, "Entrez le nom du Joueur "+(i+1)+" :");
                joueurs[i]=new JoueurDomino(nom);
                nbJ+=1;
            }
        }
    }



    public int lancerJeu(){
        SetDomino d=new SetDomino();
        Scanner s=new Scanner(System.in);
        plateau.autoResize();
        String cmd;
        int t[]=new int[2];
        while(pioche.tuileRestantes>0){
            current=pioche.jeTirTuile();
            if (joueurs[tour] instanceof AIDomino aiJoueur) {
                System.out.println(aiJoueur.playORDI(current,plateau));
            }else{
                System.out.println(joueurs[tour].tourDe()+pioche.tuilesRes()+" Tapez sur entrer pour piochez : ");
                s.nextLine();
                System.out.println("Vous avez pioché :"); 
                System.out.println(current.affichageTuileSeule());
                separation();
                do{ 
                    System.out.println("Le plateau :\n"+plateau.toString());   
                    System.out.println("Essayer de la placer où passer votre tour avec (p). Vous pouvez tourné la tuile avec (r). Sauvegardez et reprenez la partie plus tard (s)");
                    cmd=s.nextLine();
                    if(cmd.equals("p")){
                        cmd="OK";
                    }else if(cmd.equals("s")){
                        return 1;
                    }else if(cmd.equals("r")){
                        System.out.println("Votre tuile :");
                        current.rotate();
                        System.out.println(current.affichageTuileSeule());
                        separation();
                    }else if(cmd.length()==2 && plateau.StringToCaseOK(cmd,t)){
                        if(plateau.peutEtreVoisin(current,t[0],t[1])){
                            plateau.ajoutTuile(current,t[0],t[1]);
                            System.out.println(joueurs[tour].bienPlacé(plateau.pointGagné(current,t[0],t[1])));
                            cmd="OK";
                        }else{
                            System.out.println("Vous ne pouvez pas mettre votre tuile ici dans cette position");
                        }
                    }else{
                        System.out.println("Vous n'avez pas rentrer une option ou une position valide. \nRappel : ligne->A...Z, colonne->1...n");
                    }
                }while(!"OK".equals(cmd));
            }
            plateau.autoResize();
            tour=(tour+1)%nbJ;
        }
        afficheGagnantTerm();
        for(int i=0;i<nbJ;i++){
            d.actualizeLB(joueurs[i]);
        }
        return 0;
    }

    public static void separation(){
        System.out.println("---------------------------------------------------------");
    }

    public void afficheGagnantTerm(){
        int gagnant=joueurs[0].score;
        int g1=0;
        int g2=0;
        int gagnant2=-1;
        for(int i=1;i<nbJ;i++){
            if(gagnant<joueurs[i].score){
                gagnant=joueurs[i].score;
                g1=i;
                gagnant2=-1;
            }else if(gagnant==joueurs[i].score){
                gagnant2=joueurs[i].score;
                g2=g1;
            }
        }
        System.out.println(plateau.toString());
        if(gagnant2==-1){
            System.out.println("Le gagnant est "+joueurs[g1].getNom()+".");
        }else{
            System.out.println("Egalité entre "+joueurs[g1].getNom()+" et "+joueurs[g2].getNom()+".");
        }
    }










    public void lancerJeuUI(){
        plateau.autoResize();
        current=pioche.jeTirTuile();
        ActionListener passer;

        //***Main***//

        JPanel main=new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        final JLabel[] maTuile = new JLabel[1];
        maTuile[0] = current.drawTuile();
        main.add(maTuile[0]);
        
        

        JButton turn=new JButton("Tourner");
        JButton pass=new JButton("Passer");
        JButton save=new JButton("Sauvegarder");
        JLabel texte=new JLabel("Au tour de "+joueurs[tour].getNom()+" !", SwingConstants.CENTER);
        main.add(turn);
        main.add(pass);
        main.add(save);
        main.add(texte);

        ActionListener tourner=new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                current.rotate();
                main.remove(maTuile[0]);
                maTuile[0] = current.drawTuile();
                main.add(maTuile[0],0);
                main.revalidate();
                main.repaint();
            }       
        };

        final JLabel[] texteFinal = {texte};
        passer=new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            if(pioche.tuileRestantes==0){
                afficheGagnantUI();
                SetDomino donnee=new SetDomino();
                for(int i=0;i<nbJ;i++){
                    donnee.actualizeLB(joueurs[i]);
                }
                removeAll();
                setVisible(false);
                return;
            }
            current = pioche.jeTirTuile();
            tour = (tour + 1) % nbJ;
            
            main.remove(maTuile[0]);
            maTuile[0] = current.drawTuile();
            main.add(maTuile[0],0);
            
            main.remove(texteFinal[0]);
            texteFinal[0] = new JLabel("Au tour de " + joueurs[tour].getNom() + " !", SwingConstants.CENTER);
            main.add(texteFinal[0]);
            main.revalidate();
            main.repaint();
            
            
        }};
        

        ActionListener sauvegarder = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SetDomino donnee=new SetDomino();
                removeAll();
                sauvegarder(donnee);
                setVisible(false);
            }       
        };

        //***Panneau***//
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel panneau =new JPanel();
        panneau.setPreferredSize(new Dimension(1500,1500));
        panneau.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension buttonSize=new Dimension(150,150);
        
        //Redimensionnements
        splitPane.setResizeWeight(0.7);
        splitPane.setEnabled(false);
        
        //ajout des bars de defilement 
        JScrollPane scrollPane = new JScrollPane(panneau);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        ActionListener buttonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row=-1;
                int column=-1;
                JButton clickedButton = (JButton) e.getSource();
                Component[] components = panneau.getComponents();
                for (int i = 0; i < components.length; i++) {
                    if (components[i] instanceof JButton && components[i] == clickedButton) {
                        row = i / plateau.getLength();
                        column = i % plateau.getLength();
                        break;
                    }
                }
                if(plateau.peutEtreVoisin(current,row,column)){
                    plateau.ajoutTuile(current,row,column);
                    JOptionPane.showMessageDialog(null,joueurs[tour].bienPlacé(plateau.pointGagné(current,row,column)));
                    plateau.autoResize();
                    panneau.removeAll();
                    panneau.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    for(int i=0;i<plateau.getHeight();i++){
                        for(int j=0;j<plateau.getLength();j++){
                            gbc.gridx = j;
                            gbc.gridy = i;
                            if(plateau.getTuileAt(i, j)!=null){
                                panneau.add(plateau.getTuileAt(i, j).drawTuile(),gbc);
                            }else{
                                JButton newButton = new JButton();
                                newButton.setPreferredSize(buttonSize);
                                newButton.addActionListener(this);
                                panneau.add(newButton,gbc);
                            }
                        }
                    }
                    
                    passer.actionPerformed(null);
                }else{
                    JOptionPane.showMessageDialog(null,"Vous ne pouvez pas mettre votre tuile ici dans cette position.");
                }
            }
        };

        for(int i=0;i<plateau.getHeight();i++){
            for(int j=0;j<plateau.getLength();j++){
                gbc.gridx = j;
                gbc.gridy = i;
                if(plateau.getTuileAt(i, j)!=null){
                    panneau.add(plateau.getTuileAt(i, j).drawTuile(),gbc);
                }else{
                    JButton newButton = new JButton();
                    newButton.setPreferredSize(buttonSize);
                    newButton.addActionListener(buttonListener);
                    panneau.add(newButton,gbc);
                }
            }
        }

        
    //***FIN Panneau***//

    
        
        turn.addActionListener(tourner);
        save.addActionListener(sauvegarder);
        pass.addActionListener(passer);

        splitPane.setRightComponent(main);

        splitPane.setLeftComponent(scrollPane);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
        


    }

    public void afficheGagnantUI(){
        int gagnant=joueurs[0].score;
        int g1=0;
        int g2=0;
        int gagnant2=-1;
        for(int i=1;i<nbJ;i++){
            if(gagnant<joueurs[i].score){
                gagnant=joueurs[i].score;
                g1=i;
                gagnant2=-1;
            }else if(gagnant==joueurs[i].score){
                gagnant2=joueurs[i].score;
                g2=g1;
            }
        }
        if(gagnant2==-1){
            JOptionPane.showMessageDialog(null, "Le gagnant est "+joueurs[g1].getNom()+".");
        }else{
            JOptionPane.showMessageDialog(null,"Egalité entre "+joueurs[g1].getNom()+" et "+joueurs[g2].getNom()+"." );   
        }
    }

    public void sauvegarder(SetDomino donnee) {
        JOptionPane.showMessageDialog(null,"Partie sauvegardé ! Votre partie porte le nom : "+donnee.sauvegarde(this));
    }

    
}
