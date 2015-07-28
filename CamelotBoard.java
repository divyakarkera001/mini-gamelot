package game;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

//This class handles the GUI of the board
public class CamelotBoard extends JLayeredPane implements MouseListener, MouseMotionListener{

	JPanel camelotBoard;
	Cell [][]camelotSquare;
	JPanel stats;
	JLabel maxDepthLbl;
	JLabel nodeLbl;
	JLabel prunMax;
	JLabel prunMin;
	JLabel camelotPiece;
	JLabel player;
	int xAdjustment;
	int yAdjustment;
	int start_x;
	int start_y;
	Container oldParent;
	Board board;
	static int turn;
	static int AIturn;
	static int level;
	int maxDepth;
	
	//setting up CamelotBoard GUI
	public CamelotBoard(int turn, int level){
		
		this.turn =turn;
		this.AIturn = GameEngine.opponent(turn);
		this.level = level;
		
		//Max Depth is calculated on basis of selection of level that is Easy.intermediate, hard
		this.maxDepth = calculateDepth(level);
		board = new Board();
		//Setting Dimension JLayeredPane
		Dimension boardSize = new Dimension(980, 450);
		setPreferredSize(boardSize);
		
		//Adding mouse Listener to the board
		addMouseListener(this);
		addMouseMotionListener(this);
	 
		//creating camelot Board
		camelotBoard = new JPanel();
		
		//Setting the size of the board
		camelotBoard.setPreferredSize( new Dimension(820, 400));
		
		//Creating a Statistics Panel for the board game
		stats = new JPanel();
		stats.setPreferredSize( new Dimension(150, 400));
		maxDepthLbl = new JLabel();
		nodeLbl = new JLabel();
		prunMax = new JLabel();
		prunMin = new JLabel();
		player = new JLabel();
		stats.add(maxDepthLbl);
		stats.add(nodeLbl);
		stats.add(prunMax);
		stats.add(prunMin);
		stats.add(player);
		
		//Adding the camelot and statistic panel to the board game 
		add(camelotBoard, JLayeredPane.DEFAULT_LAYER);
		add(stats, JLayeredPane.DEFAULT_LAYER);
		
		//Setting a 8 X 14 grid structure for the camelot board 
		camelotBoard.setLayout( new GridLayout(8,14) );
		camelotBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		
		//Array to store each camelot panel
		camelotSquare = new Cell[8][14];
		
		for (int i = 0; i <8; i++) {
			for (int j = 0; j <14; j++) {
				
				Cell square = new Cell(i,j);
				camelotSquare[i][j] = square;
				camelotBoard.add(square);
				
				int row = i % 2;
				if (row == 0)
				square.setBackground( j % 2 == 0 ? Color.gray : Color.white );
				else
				square.setBackground( j % 2 != 0 ? Color.gray : Color.white );
				  
			}
		}
		
		//Setting few jpanel to opaque to get the get the shape of camelot board
		for(int i = 0; i<3 ;i++){
			for(int j = 2-i,k = 0;j>=0 && k<=3; j--,k++ ){
					camelotSquare[i][j].setOpaque(false);
					camelotSquare[i][13-k].setOpaque(false);
					
			}
			for(int j = i,k = 0;j>=0 && k<=3; j--,k++ ){
					
					camelotSquare[i+5][j].setOpaque(false);
					camelotSquare[i+5][13-k].setOpaque(false);
			}
				
		}
		addCamelotPieces();
		
	}
	
	//Method lets human or computer play on basis of the color selection white goes first
	public void startGame(){
		if(turn==Seed.WHITE.getCode()){
			
			player.setText("Your Chance");
		}
		else{
			player.setText("Computer Playing");
			playAI();
		}
	}
	
	//Resetting the camelot board
	public void resetBoard(){
		
		 for (int i = 0; i <8; i++) {
			  
			  for (int j = 0; j <14; j++) {
				  
				  camelotSquare[i][j].removeAll();
				  camelotSquare[i][j].validate();
				  camelotSquare[i][j].repaint();
			  }
		 }
		 addCamelotPieces();
	}
		 
	//adding player pieces to the Board
	private void addCamelotPieces(){
		
		JLabel blackPiece;
		JLabel whitePiece;
		
		for(int j=2;j<=5;j++){
				 
			whitePiece = new JLabel( new ImageIcon("C:/Users/Divya/Desktop/icon/white.png") );
			blackPiece = new JLabel( new ImageIcon("C:/Users/Divya/Desktop/icon/black.png") );
			camelotSquare[j][4].add(whitePiece);
			camelotSquare[j][9].add(blackPiece);
		}
		
		for(int j=3;j<=4;j++){
			 
			whitePiece = new JLabel( new ImageIcon("C:/Users/Divya/Desktop/icon/white.png") );
			blackPiece = new JLabel( new ImageIcon("C:/Users/Divya/Desktop/icon/black.png") );
			camelotSquare[j][5].add(whitePiece);
			camelotSquare[j][8].add(blackPiece);
		}
		
	}
	
	//When the pawn is dragged on the board
	@Override
	public void mouseDragged(MouseEvent me) {
		if (camelotPiece == null) return;
		camelotPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//Select the pawn  on the board on mouse press
	@Override
	public void mousePressed(MouseEvent e) {
		camelotPiece = null;
		JLabel c =  (JLabel) camelotBoard.findComponentAt(e.getX(), e.getY());
		
		oldParent = (Container)c.getParent();
		for(int i = 0;i <8 ;i++){
			for(int j = 0; j<14; j++){
				if(camelotSquare[i][j]==oldParent){
					System.out.println(i+" , "+j);
					start_x = i;
					start_y = j;
					break;
				}
			}
		}
		
		
		// Adjusting the motion of the mouse
	
		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		camelotPiece = (JLabel)c;
		camelotPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		camelotPiece.setSize(camelotPiece.getWidth(), camelotPiece.getHeight());
		add(camelotPiece, JLayeredPane.DRAG_LAYER);
		
	}
	
	//Placing the pawn in destination cell if the move is legal and passed the turn to AI Player
	@Override
	public void mouseReleased(MouseEvent e) {
		
		// TODO Auto-generated method stub
		if(camelotPiece == null) return;
		int end_x = -1, end_y = -1;
		camelotPiece.setVisible(false);
		Cell c =  (Cell) camelotBoard.findComponentAt(e.getX(), e.getY());
		Container parent = (Container)c;
		for(int i = 0;i <8 ;i++){
			for(int j = 0; j<14; j++){
				if(camelotSquare[i][j]==parent){
					end_x = i;
					end_y = j;
					System.out.println(i+" , "+j);
					break;
				}
			}
		}
		
		//Checking if the motion make is legal and applying the move to the the board
		if(Move.IsMoveLegal(board.boardLayout,start_x,start_y,end_x,end_y,turn)==Move.LEGALMOVE){
			Move.ApplyMove(board.boardLayout,start_x,start_y, end_x,end_y, Move.jumpI, Move.jumpJ);
			if(Move.jumpI>-1 && Move.jumpJ>-1){
				System.out.println("Jumped: "+Move.jumpI+","+Move.jumpJ);
				camelotSquare[Move.jumpI][Move.jumpJ].removeAll();
				camelotSquare[Move.jumpI][Move.jumpJ].removeAll();
				camelotSquare[Move.jumpI][Move.jumpJ].revalidate();
				camelotSquare[Move.jumpI][Move.jumpJ].repaint();
			}
			try{
				//Placing the pawn in the destination location
				parent.add(camelotPiece);
				parent.revalidate();
				camelotPiece.setVisible(true);
				
				
			}
			finally{
				//Checking the goal state
				if (Move.checkWinState(board.boardLayout,turn))
				{
					player.setText("You Win");
				}
				else{
					player.setText("Computer Playing");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Passing the turn to AIPlayer
					playAI();
				}
			}
			
		}
		else{
			//camelotPiece.setLocation(e.getX(), e.getY());
			oldParent.add( camelotPiece );
			//parent.setBackground(Color.cyan);
			oldParent.revalidate();
			camelotPiece.setVisible(true);
			
		}
		//board.displayBoard();
		oldParent = null;
		
	}
	public void playAI(){
		
		DisplayParameter.maxDepthLbl = 0;
		DisplayParameter.nodeLbl = 0;
		DisplayParameter.prunMax = 0;
		DisplayParameter.prunMin = 0;
		
		
		int score;
		int[] result = new int[4];
		int[] cMove;
		int[] counter = new int[1];
		score = GameEngine.MiniMax(board.boardLayout,0,maxDepth,result,2,counter);

		if (result[0] == 0 && result[1] == 0)
			System.out.println("No moves");
		else
		{
			//Move.move_board(board.boardLayout, result);
			System.out.println("Before the move board");
			board.displayBoard();
			System.out.println(result[0]+","+result[1]+","+result[2]+","+result[3]);
			
				//Applying the selected move to the board
			if(Move.IsMoveLegal(board.boardLayout,result[0],result[1],result[2],result[3],2)==Move.LEGALMOVE){
				Move.ApplyMove(board.boardLayout,result[0],result[1],result[2],result[3], Move.jumpI, Move.jumpJ);
				if(Move.jumpI>-1 && Move.jumpJ>-1){
					removePiece(Move.jumpI,Move.jumpJ);
				}
				removePiece(result[0],result[1]);
				addPiece(result[2],result[3]);
				System.out.println("");
			}
			
			
		}
		System.out.println("***********************");
		System.out.println("After the move board");
		board.displayBoard();
		// Displaying the parameters
		maxDepthLbl.setText("Max Depth: "+DisplayParameter.maxDepthLbl);
		nodeLbl.setText("Number of nodes: "+DisplayParameter.nodeLbl);
		prunMax.setText("Max Purn: "+ DisplayParameter.prunMax);
		prunMin.setText("Min Purn: "+ DisplayParameter.prunMin);
		if (Move.checkWinState(board.boardLayout,AIturn))
		{
			player.setText("Computer Win");
		}
		else{
			player.setText("Your Chance");
		}		
	}
	
	//GUI remove and repaint
	public void removePiece(int i, int j){
		
		camelotSquare[i][j].removeAll();
		camelotSquare[i][j].revalidate();
		camelotSquare[i][j].repaint();
	}
	
	//GUI adding the piece
	public void addPiece(int i, int j){
		
		JLabel blackPiece = new JLabel( new ImageIcon("C:/Users/Divya/Desktop/icon/black.png") );
		camelotSquare[i][j].add(blackPiece);
	}
	
	//Depth calculating function
	private int calculateDepth(int level){
		
		switch(level){
		
		case 1:
			return 5;
		case 2:
			return 10;
		case 3:
			return 15;
		}
		return 5;
	}
}
