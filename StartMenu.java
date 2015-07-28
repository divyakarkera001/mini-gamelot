package game;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class StartMenu{
	
	 static int level = 0;
	 static int player = Seed.WHITE.getCode();
     static void createAndShowStartGUI(final Container frame) {
       
    	final JLayeredPane start = new JLayeredPane();
    	//Creating a Start Menu 
    	final JPanel startMenu = new JPanel(new GridLayout(2,1));
    	startMenu.setPreferredSize(new Dimension(700,700));
    	
    	
    	//Creating selection Panel 
    	JPanel selection = new JPanel();
    	selection.setPreferredSize(new Dimension(400,100));
    	GridBagLayout gridBag = new GridBagLayout();
    	selection.setLayout(new GridLayout(1,1));
    	
    	
    	//Player menu
    	JPanel player = new JPanel();
    	player.setPreferredSize(new Dimension(200,100));
    	Border blackline = BorderFactory.createLineBorder(Color.black);
    	TitledBorder playerTitle;
    	playerTitle = BorderFactory.createTitledBorder(blackline, "Player Selection");
    	player.setBorder(playerTitle); 
    	player.setLayout(new BoxLayout(player, BoxLayout.PAGE_AXIS));
    	

    	
    	final RadioBtn white = new RadioBtn(Seed.WHITE.getCode(),"C:/Users/Divya/Desktop/icon/white.png",true);   
    	
        final RadioBtn black = new RadioBtn(Seed.BLACK.getCode(),"C:/Users/Divya/Desktop/icon/black.png");
        ButtonGroup playerGroup = new ButtonGroup();
        playerGroup.add(white.getRadio());
        playerGroup.add(black.getRadio());
        player.add(white);
        player.add(black);
        
        //Difficulty Menu
        JPanel difficultyMenu = new JPanel(new GridLayout(3,1));
        difficultyMenu.setPreferredSize(new Dimension(200,100));
        TitledBorder difficultyTitle;
    	difficultyTitle = BorderFactory.createTitledBorder(blackline, "Difficulty Selection");
    	difficultyMenu.setBorder(difficultyTitle); 
    	
        final LevelSelBtn easy = new LevelSelBtn("EASY",1,true);    	
        final LevelSelBtn intermediate = new LevelSelBtn("INTERMEDIATE",2);
        final LevelSelBtn difficult = new LevelSelBtn("DIFFICULT",3);
        
        ButtonGroup difficultyLevel = new ButtonGroup();
        difficultyLevel.add(easy.getRadio());
        difficultyLevel.add(intermediate.getRadio());
        difficultyLevel.add(difficult.getRadio());
        difficultyMenu.add(easy);
        difficultyMenu.add(intermediate);
        difficultyMenu.add(difficult);
        //Adding in selection Panel 
       // constraints.gridx = 0;
        //constraints.gridy = 0;            
         
        selection.add(player);
       // constraints.gridx = 1;
    	selection.add(difficultyMenu);
        
    	//Creating button Panel 
    	JPanel buttonPanel = new JPanel(new FlowLayout());
    	buttonPanel.setPreferredSize(new Dimension(200,100));
    	JButton quit = new JButton("QUIT");
    	JButton play = new JButton("PLAY");
    	//play.setPreferredSize(new Dimension(50,50));
    	//quit.setPreferredSize(new Dimension(50,50));
    	buttonPanel.add(play);
    	buttonPanel.add(quit);
    	play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	frame.remove(startMenu);
            	int selectedColor = RadioBtn.selected;
            	int selectedLevel = LevelSelBtn.selected;
            	JLayeredPane layeredPane = new CamelotBoard(selectedColor,selectedLevel);
            	layeredPane.setLayout(new FlowLayout());
            	frame.add(layeredPane);
            	
                frame.revalidate();
                ((CamelotBoard) layeredPane).startGame();
            }
        });
    	quit.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
             }
         });
    	//Adding to the Start Menu 
    	startMenu.add(selection);
    	startMenu.add(buttonPanel);
    	frame.add(startMenu);
    
    }
 
   

	
	
	
}