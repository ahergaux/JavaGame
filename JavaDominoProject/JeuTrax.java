import java.io.Serializable;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JeuTrax extends JPanel implements Serializable{
    
    JoueurTrax[] joueurs;
    int nbJ;
    PlateauTrax plateau;
    int tour;
    Chemin cheminBlanc;
    Chemin cheminNoir;
    TuileTrax tuile;
    SetTrax donnee;
    
    public JeuTrax(int mode){
        donnee=new SetTrax();
        Scanner s=new Scanner(System.in);
        joueurs=new JoueurTrax[2];
        plateau=new PlateauTrax();
        tour=0;
        cheminNoir=new Chemin(1,plateau);
        cheminBlanc=new Chemin(0,plateau);
        if (mode==0) {
            for(int i=0;i<2;i++){
                System.out.println("Entrez le nom du joueur "+(i+1)+" :");
                joueurs[i]=new JoueurTrax(s.nextLine());
                nbJ+=1;
                if(nbJ==1){
                    System.out.println("Vous êtes le joueur blanc");
                }else{
                    System.out.println("Vous êtes le joueur noir");
                }
            }
        }else{
            for(int i=0;i<2;i++){
                joueurs[i]=new JoueurTrax(JOptionPane.showInputDialog(this, "Entrez le nom du Joueur "+(i+1)+" :"));
                nbJ+=1;
                if(nbJ==1){
                    JOptionPane.showMessageDialog(null,"Vous êtes le joueur blanc");
                }else{
                    JOptionPane.showMessageDialog(null,"Vous êtes le joueur rouge");
                }
            }
        }   
    }



    public int lancerJeu(){
        boolean aucunCheminGagnant;
        boolean cheminNoirGagnant;
        boolean cheminBlancGagnant;
        Scanner piocher=new Scanner(System.in);
        plateau.autoResize();
        String cmd;
        int [] position =new int[2];
        do{
            tuile=new TuileTrax();
            System.out.println(joueurs[tour].tourDe()+" Tapez sur entrer pour piochez : ");
            piocher.nextLine();
            System.out.println(tuile.affichageTuileSeule());
            separation();
            System.out.println(plateau.toString());
            System.out.println("Vous pouvez taper sur 'r' pour la tourner, 'f' pour changer de face où 's' pour sauvegardé la partie.");
            do{
                switch (cmd=piocher.nextLine()) {
                    case "r" -> {
                        tuile.rotate();
                        System.out.println(tuile.affichageTuileSeule());
                        separation();
                        System.out.println(plateau.toString());
                        System.out.println("Vous pouvez taper sur 'r' pour la tourner, 'f' pour changer de face où 's' pour sauvegardé la partie.");

                    }
                    case "f" -> {
                        tuile.changeFace();
                        System.out.println(tuile.affichageTuileSeule());
                        separation();
                        System.out.println(plateau.toString());
                        System.out.println("Vous pouvez taper sur 'r' pour la tourner, 'f' pour changer de face où 's' pour sauvegardé la partie.");

                    }
                    case "s" -> {
                        return 1;
                    }
                    default -> {
                        if(cmd.length()==2 && plateau.StringToCaseOK(cmd,position)){                            
                            if(plateau.isEmpty()){
                                plateau.ajoutTuile(tuile,position[0],position[1]);
                                System.out.println(joueurs[tour].bienPlacé(0));
                                cmd="OK";
                            }else if(plateau.peutEtreVoisin(tuile,position[0],position[1])){
                                plateau.ajoutTuile(tuile,position[0],position[1]);
                                System.out.println(joueurs[tour].bienPlacé(0));
                                cmd="OK";
                            }else{
                                System.out.println("Vous ne pouvez pas mettre votre tuile ici dans cette position");
                            }
                        }else{
                            System.out.println("Vous n'avez pas rentrer une option ou une position valide. \nRappel : ligne->A...Z, colonne->1...n");
                        }
                    }
                }

            }while(!"OK".equals(cmd));
            
            tour=(tour+1)%2;
            
            cheminNoirGagnant = cheminNoir.isGagnant(tuile, position[0], position[1]);
            cheminBlancGagnant = cheminBlanc.isGagnant(tuile, position[0], position[1]);
            
            plateau.autoResize();

            cheminBlancGagnant= cheminBlancGagnant || cheminBlanc.eightWay();
            cheminNoirGagnant=cheminNoirGagnant || cheminNoir.eightWay();

            aucunCheminGagnant =  !cheminNoirGagnant && !cheminBlancGagnant;

        }while(aucunCheminGagnant);
        
        
        if (cheminBlancGagnant) {
            System.out.println("Bravo "+ joueurs[0].getNom()+", vous avez gagné !");
            donnee.actualizeLB(joueurs[0],1);
            donnee.actualizeLB(joueurs[1],0);

        }else{
            System.out.println("Bravo "+ joueurs[1].getNom()+", vous avez gagné !");
            donnee.actualizeLB(joueurs[1],1);
            donnee.actualizeLB(joueurs[0],0);
        }
        return 0;
    }
    
    

    public static void separation(){
        System.out.println("---------------------------------------------------------");
    }



    public void lancerJeuUI(){
        plateau.autoResize();
        plateau.autoResize();
        tuile=new TuileTrax();

        //***Main***//

        JPanel main=new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        final JLabel[] maTuile = new JLabel[1];
        maTuile[0] = tuile.drawTuile();
        main.add(maTuile[0]);
        
        

        JButton turn=new JButton("Tourner");
        JButton cdf=new JButton("Changer de face");
        JButton save=new JButton("Sauvegarder");
        JLabel texte=new JLabel("Au tour de "+joueurs[tour].getNom()+" !", SwingConstants.CENTER);
        main.add(turn);
        main.add(cdf);
        main.add(save);
        main.add(texte);

        ActionListener tourner;
        tourner = (ActionEvent e) -> {
            tuile.rotate();
            main.remove(maTuile[0]);
            maTuile[0] = tuile.drawTuile();
            main.add(maTuile[0],0);
            main.revalidate();
            main.repaint();       
        };

        ActionListener changerDeFace= (ActionEvent e) -> {
            tuile.changeFace();
            main.remove(maTuile[0]);
            maTuile[0] = tuile.drawTuile();
            main.add(maTuile[0],0);
            main.revalidate();
            main.repaint();   
        };

        final JLabel[] texteFinal = {texte};
        ActionListener tourSuivant;
        tourSuivant = (var e) -> {
            tuile=new TuileTrax();
            main.remove(maTuile[0]);
            maTuile[0] = tuile.drawTuile();
            main.add(maTuile[0],0);
            tour = (tour + 1) % nbJ;
            main.remove(texteFinal[0]);
            texteFinal[0] = new JLabel("Au tour de " + joueurs[tour].getNom() + " !", SwingConstants.CENTER);
            main.add(texteFinal[0]);
            main.revalidate();
            main.repaint();
        };
        

        ActionListener sauvegarder = (ActionEvent e) -> {
            SetTrax donnee1 = new SetTrax();
            endOfGame();
            sauvegarder(donnee1);       
        };

        //***Panneau***//
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel panneau =new JPanel();
        panneau.setPreferredSize(new Dimension(1500,1500));
        panneau.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension buttonSize=new Dimension(100,100);
        
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
                if(plateau.peutEtreVoisin(tuile,row,column) || plateau.isEmpty()){
                    plateau.ajoutTuile(tuile,row,column);
                    JOptionPane.showMessageDialog(null,joueurs[tour].bienPlacé(tour));
                    if (cheminBlanc.isGagnant(tuile,row,column)) {
                        JOptionPane.showMessageDialog(null,"Bravo "+joueurs[0].getNom()+",vous avez gagné !");
                        endOfGame();
                    }else if (cheminNoir.isGagnant(tuile,row,column)) {
                        JOptionPane.showMessageDialog(null,"Bravo "+joueurs[1].getNom()+",vous avez gagné !");
                        endOfGame();
                    }
                    plateau.autoResize();
                    if (cheminBlanc.eightWay()) {
                        JOptionPane.showMessageDialog(null,"Bravo "+joueurs[0].getNom()+",vous avez gagné !");
                        donnee.actualizeLB(joueurs[0],1);
                        donnee.actualizeLB(joueurs[1],0);
                        endOfGame();
                    }else if (cheminNoir.eightWay()) {
                        JOptionPane.showMessageDialog(null,"Bravo "+joueurs[1].getNom()+",vous avez gagné !");
                        donnee.actualizeLB(joueurs[1],1);
                        donnee.actualizeLB(joueurs[0],0);
                        endOfGame();
                    }
                    
                    
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
                    tourSuivant.actionPerformed(null);
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
        cdf.addActionListener(changerDeFace);

        splitPane.setRightComponent(main);
        splitPane.setLeftComponent(scrollPane);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
        
        setVisible(true);

    }

    private void endOfGame(){
        removeAll();
        setVisible(false);
    }

    
    public void sauvegarder(SetTrax donnee) {
        JOptionPane.showMessageDialog(null,"Partie sauvegardé ! Votre partie porte le nom : "+donnee.sauvegarde(this));
    }

   
}


