package game;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class CamelotGame {
	
	static JFrame frame;
	private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        StartMenu.createAndShowStartGUI(frame.getContentPane());
       // frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	
}
