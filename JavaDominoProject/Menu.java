import java.util.Scanner;
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements Serializable{
    
    private final int mode;
    private JPanel sousPanel;
    private JLabel text;
    private JLabel text2;
    private JTextArea zoneText;
    private JTextField textField;
    private JTextField textField2;

    public Menu(int mode){
        this.mode=mode;
    }

    public static void main(String []  args){
        int mode = 1;
        if (args.length > 0 && args[0].equals("terminal")) {
            mode = 0;
        }
        Menu launch=new Menu(mode);
        launch.menu();
    }
    
    public void menu(){
        if (mode==0) {
            while(true){
                Boolean stay=true;
                Scanner s=new Scanner(System.in);
                separation();
                System.out.println("                     ****Menu****");
                separation();
                System.out.println("Tapez le numéro correspondant :\n\n   (1)Jeu Domino\n   (2)Jeu Trax\n   (3)Quitter le programme\n");
                switch (s.next()) {
                    case "1":
                        separation();
                        SetDomino d=new SetDomino();
                        JeuDomino jeu;
                
                        while(stay){
                            separation();
                            System.out.println("                  ****MenuDomino****");
                            separation();
                            System.out.println("Tapez le numéro correspondant :\n\n   (1)Jouez au jeu\n   (2)Paramétrage du jeu\n   (3)Leaderboard\n   (4)Retour au menu principal");
                            switch (s.next()) {
                                case "1" -> {
                                    System.out.println("Tapez le numéro correspondant :\n\n   (1)Jouer une nouvelle partie\n   (2)Jouer une partie sauvegardé\n   (3)Retour au menu");
                                    switch (s.next()) {
                                        case "1" -> {
                                            jeu=new JeuDomino(d.getNbTuiles(),d.getNbJ(),mode);
                                            if(jeu.lancerJeu()==1){
                                                d.sauvegarde(jeu);
                                            }
                                    }
                                        case "2" -> {
                                            String str=d.getSave();
                                            if(str.equals("")){
                                                System.out.println("Aucune partie sauvegardé");
                            
                                            }else{
                                                System.out.println("Voici les parties sauvegardés :"+ str+"\n"+"Entrez le numéro de la partie que voulez vous chargé :");
                                                jeu=d.load(s.nextInt());
                                                if (jeu.lancerJeu()==1) {
                                                    d.sauvegarde(jeu);
                                                }
                                            }
                                    }
                                        case "3" -> {
                                    }
                                        default -> System.out.println("Vous n'avez pas rentré un numéro correcte");
                                    }                
                                }
                                case "2" -> {
                                    separation();
                                    while(stay){
                                        separation();
                                        System.out.println("                  ****Paramétrage****");
                                        separation();
                                        System.out.println("Tapez le numéro correspondant :\n\n   (1)Paramétrer le nombre de tuiles\n   (2)Paramétrer le nombre de joueurs\n   (3)Retour au menu");
                                        switch (s.next()) {
                                            case "1" -> {
                                                System.out.println("Il y'a "+d.getNbTuiles()+" tuiles."+" Combien de tuiles voulez vous ?");
                                                d.setNbTuiles(s.nextInt());
                                            }
                                            case "2" -> {
                                                System.out.println("Il y'a "+d.getNbJ()+" joueurs."+" Combien de joueurs voulez vous ?");
                                                d.setNbJ(s.nextInt());
                                            }
                                            case "3" -> {
                                                stay=false;
                                                break;
                                            }
                                            default -> System.out.println("Vous n'avez pas rentré un numéro correcte");
                                        }
                                        
                                    }
                                    stay=true;  
                                }
                                case "4" -> {
                                    separation();
                                    separation();
                                    System.out.println("\n");
                                    stay=false;
                                    break;

                                }
                                case "3" -> {
                                    separation();
                                    System.out.println(d.getLD());
                                }
                                default -> System.out.println("Vous n'avez pas rentré un numéro correcte");
                            }
                            
                        }
                        break;
                    case "2":
                        separation();
                        SetTrax setT=new SetTrax();
                        while(stay){
                            separation();
                            System.out.println("                   ****MenuTrax****");
                            separation();
                            System.out.println("Tapez le numéro correspondant :\n\n   (1)Jouez au jeu\n   (2)Leaderboard\n   (3)Retour au menu principal");
                            switch (s.next()) {
                                case "1" -> {
                                    JeuTrax jeuT;
                                    separation();
                                    System.out.println("Tapez le numéro correspondant :\n\n   (1)Jouer une nouvelle partie\n   (2)Jouer une partie sauvegardé\n   (3)Retour au menu");
                                    switch (s.next()) {
                                        case "1" -> {
                                            jeuT=new JeuTrax(0);
                                            if(jeuT.lancerJeu()==1){
                                                setT.sauvegarde(jeuT);
                                            }
                                    }
                                        case "2" -> {
                                            String str=setT.getSave();
                                            if(str.equals("")){
                                                System.out.println("Aucune partie sauvegardé");

                                            }else{
                                                System.out.println("Voici les parties sauvegardés :"+ str+"\n"+"Entrez le numéro de la partie que voulez vous chargé :");
                                                jeuT=setT.load(s.nextInt());
                                                if (jeuT.lancerJeu()==1) {
                                                    setT.sauvegarde(jeuT);
                                                }
                                            }
                                    }
                                        case "3" -> {
                                    }
                                        default -> System.out.println("Vous n'avez pas rentré un numéro correcte");
                                    }
                                }
                                case "3" -> {
                                    separation();
                                    separation();
                                    System.out.println("\n");
                                    stay=false;  
                                    break;                        
                                }
                                case "2" -> {
                                    separation();
                                    System.out.println(setT.getLD());
                                }
                                default -> System.out.println("Vous n'avez pas rentré un numéro correcte");
                            }
                            
                        }
                        stay=true;
                        break;
                    case "3":
                        separation();
                        separation();
                        System.out.println("                      A bientot !");
                        separation();
                        separation();
                        System.exit(0);
                    default:
                        System.out.println("Vous n'avez pas rentré un numéro correcte");
                        break;
                }
                
            }
        }else{
            //JFrame mainFrame = new JFrame("Fenêtre principale");
            setPreferredSize(new Dimension(1000,1000));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

            JPanel dynamicPanel = new JPanel();
          

            setJMenuBar(menuBar(dynamicPanel));
            
            dynamicPanel.setLayout(new BorderLayout());

            splitPane.setBottomComponent(dynamicPanel);
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(splitPane, BorderLayout.CENTER);
            pack();

            setVisible(true); 
            dynamicPanel.setVisible(true);
            
        }
    }
    
    private JMenuBar menuBar(JPanel dynamicPanel){

        JMenuBar jmb=new JMenuBar();
        
        JMenu jeu = new JMenu("Jeu");
        JMenu leadB = new JMenu("Leaderboard");
        JMenu parametre=new JMenu("Paramètres");
        

        //Ajout au Menu
        jmb.add(jeu);
        jmb.add(parametre);
        jmb.add(leadB);
        
        //Menu Jeu
        JMenu playD=new JMenu("Jouer aux Domino");
        JMenu playT=new JMenu("Jouer à Trax");
        JMenuItem quit=new JMenuItem("Quitter le jeu");
        jeu.add(playD);
        jeu.add(playT);
        jeu.add(quit);
        
        JMenuItem newPD=new JMenuItem("Jouer une nouvelle partie");
        JMenuItem partieSD=new JMenuItem("Jouer une partie sauvegardé");
        playD.add(newPD);
        playD.add(partieSD);
        
        
        JMenuItem newPT=new JMenuItem("Jouer une nouvelle partie");
        JMenuItem partieST=new JMenuItem("Jouer une partie sauvegardé");
        playT.add(newPT);
        playT.add(partieST);
       

        //Menu LB
        JMenuItem LBD=new JMenuItem("Afficher le leaderboard Domino");
        JMenuItem LBT=new JMenuItem("Afficher le leaderboard Trax");
        leadB.add(LBD);
        leadB.add(LBT);

        //Menu parametre
        JMenuItem paramD=new JMenuItem("Parametrer Domino");
        parametre.add(paramD);


        LBD.addActionListener((ActionEvent e3)-> {
            SetDomino donnee=new SetDomino();
            sousPanel=new JPanel();
            zoneText=new JTextArea(donnee.getLD());
            dynamicPanel.removeAll();
            sousPanel.add(zoneText);
            dynamicPanel.add(sousPanel);
            dynamicPanel.setVisible(true);
            zoneText.setEditable(false);
            revalidate();
            repaint();

        });

        LBT.addActionListener((ActionEvent e3)-> {
            SetTrax donnee=new SetTrax();
            sousPanel=new JPanel();
            zoneText=new JTextArea(donnee.getLD());
            dynamicPanel.removeAll();
            sousPanel.add(zoneText);
            dynamicPanel.add(sousPanel);
            dynamicPanel.setVisible(true);
            zoneText.setEditable(false);
            revalidate();
            repaint();

        });

        paramD.addActionListener((ActionEvent e) -> {
            SetDomino set=new SetDomino();
            sousPanel=new JPanel();
            sousPanel.setLayout(new BoxLayout(sousPanel, BoxLayout.Y_AXIS));
            text=new JLabel("Le nombre de joueur est paramétré à : "+set.getNbJ()+"\n  Changer à :");
            textField =new JTextField(1);
            textField.setBounds(new Rectangle(14,50));
            JButton boutton1=new JButton("Validate");
            text2=new JLabel("Le nombre de tuiles est paramétré à : "+set.getNbTuiles()+"\n  Changer à :");
            textField2=new JTextField(1);
            JButton boutton2=new JButton("Validate");

            boutton1.addActionListener((ActionEvent e1) -> {
                SetDomino donnee =new SetDomino();
                donnee.setNbJ(Integer.parseInt(textField.getText()));
                JOptionPane.showMessageDialog(null,"Paramètre enregistrée");
               
            });

            boutton2.addActionListener((ActionEvent e2) -> {
                SetDomino donnee =new SetDomino();
                donnee.setNbTuiles(Integer.parseInt(textField2.getText()));
                JOptionPane.showMessageDialog(null,"Paramètre enregistrée");

            });

            sousPanel.add(text);
            sousPanel.add(textField);
            sousPanel.add(boutton1);
            sousPanel.add(text2);
            sousPanel.add(textField2);
            sousPanel.add(boutton2);
            dynamicPanel.removeAll();
            dynamicPanel.add(sousPanel);
            revalidate();
            repaint();
            dynamicPanel.setVisible(true);
        });

        newPD.addActionListener((ActionEvent e) -> {
            
            SetDomino donnee=new SetDomino();
            JeuDomino jeuDomino=new JeuDomino(donnee.getNbTuiles(),donnee.getNbJ(),1);
            
            jeuDomino.lancerJeuUI();
            dynamicPanel.removeAll();
            dynamicPanel.add(jeuDomino,BorderLayout.CENTER);
            revalidate();
            repaint();
            dynamicPanel.setVisible(true);

        });

        partieSD.addActionListener((ActionEvent e) -> {
            SetDomino donnee=new SetDomino();
            sousPanel=new JPanel();
            sousPanel.setLayout(new FlowLayout());
            String sauvegarde=donnee.getSave();
            zoneText=new JTextArea(sauvegarde);
            zoneText.setEditable(false);
            if(sauvegarde.equals("")){
                zoneText=new JTextArea("Aucune partie sauvegardé");
                zoneText.setEditable(false);
                sousPanel.add(zoneText);
            }else{
                sousPanel.add(zoneText);
                JLabel instruct=new JLabel("Entrez le numéro de la partie que vous voulez jouer..");
                sousPanel.add(instruct);
                textField = new JTextField(20);
                JButton validateButton = new JButton("Valider");
                validateButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
    
                        SetDomino donnee=new SetDomino();
                        String text = textField.getText();
                        JeuDomino jeuDomino;
                        jeuDomino=donnee.load(Integer.parseInt(text));
                        jeuDomino.lancerJeuUI();
                        dynamicPanel.removeAll();
                        dynamicPanel.add(jeuDomino,BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    }
                });
                sousPanel.add(textField);
                sousPanel.add(validateButton);
            }
            
            dynamicPanel.removeAll();
            dynamicPanel.add(sousPanel);
            revalidate();
            repaint();
            dynamicPanel.setVisible(true);

        });

        newPT.addActionListener((ActionEvent e) -> {
            
            SetTrax donnee=new SetTrax();
            JeuTrax jeuTrax=new JeuTrax(1);
            
            jeuTrax.lancerJeuUI();
            dynamicPanel.removeAll();
            dynamicPanel.add(jeuTrax,BorderLayout.CENTER);
            revalidate();
            repaint();
            dynamicPanel.setVisible(true);

        });

        partieST.addActionListener((ActionEvent e) -> {
            SetTrax donnee=new SetTrax();
            sousPanel=new JPanel();
            sousPanel.setLayout(new FlowLayout());
            String sauvegarde=donnee.getSave();
            zoneText=new JTextArea(sauvegarde);
            zoneText.setEditable(false);
            if(sauvegarde.equals("")){
                zoneText=new JTextArea("Aucune partie sauvegardé");
                zoneText.setEditable(false);
                sousPanel.add(zoneText);
            }else{
                sousPanel.add(zoneText);
                JLabel instruct=new JLabel("Entrez le numéro de la partie que vous voulez jouer..");
                sousPanel.add(instruct);
                textField = new JTextField(20);
                JButton validateButton = new JButton("Valider");
                validateButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
    
                        SetTrax donnee=new SetTrax();
                        String text = textField.getText();
                        JeuTrax jeuTrax;
                        jeuTrax=donnee.load(Integer.parseInt(text));
                        jeuTrax.lancerJeuUI();
                        dynamicPanel.removeAll();
                        dynamicPanel.add(jeuTrax,BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    }
                });
                sousPanel.add(textField);
                sousPanel.add(validateButton);
            }
            
            dynamicPanel.removeAll();
            dynamicPanel.add(sousPanel);
            revalidate();
            repaint();
            dynamicPanel.setVisible(true);

        });

        quit.addActionListener((ActionEvent e) ->{
            dynamicPanel.setVisible(false);
        });



        return jmb;
    }
    
    public static void separation(){
        System.out.println("---------------------------------------------------------");
    }
}