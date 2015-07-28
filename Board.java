package game;

import java.util.ArrayList;

// Class to handle board representation in array format
public class Board {

	protected int [][] boardLayout;
	private int removedPosX = -1;
	private int removedPosY = -1;
	private boolean removed = false;
	static final int WHITE = Seed.WHITE.getCode();
	static final int BLACK = Seed.BLACK.getCode();
	static final int EMPTY = Seed.EMPTY.getCode();
	static final int INVALID = Seed.INVALID.getCode();
	protected ArrayList<Prawn> whiteColl;
	protected ArrayList<Prawn> blackColl;
	
	
	public Board(){
		resetBoard();				
	}
	
	public int[][] getBoard(){
		return boardLayout;
	}
	public void displayBoard(){
		
		for(int i = 0; i<8 ;i++){
			for(int j = 0;j<14; j++ ){
				System.out.print(boardLayout[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	//Resetting the board to the Start state
	public void resetBoard(){
		
		whiteColl = new ArrayList<Prawn>(6);
		blackColl = new ArrayList<Prawn>(6);
		boardLayout = new int[8][14];
		int white = Seed.WHITE.getCode();
		int black = Seed.BLACK.getCode();
		int invalid = Seed.INVALID.getCode();
		for(int i = 0; i<3 ;i++){
			for(int j = 2-i,k = 0;j>=0 && k<=3; j--,k++ ){
				boardLayout[i][j] = invalid;
				boardLayout[i][13-k] = invalid;
				
			}
			for(int j = i,k = 0;j>=0 && k<=3; j--,k++ ){
				
				boardLayout[i+5][j] = invalid;
				boardLayout[i+5][13-k] = invalid;
			}
			
		}
		whiteColl.add(new Prawn(2,4,WHITE));
		whiteColl.add(new Prawn(3,4,WHITE));
		whiteColl.add(new Prawn(4,4,WHITE));
		whiteColl.add(new Prawn(5,4,WHITE));
		whiteColl.add(new Prawn(3,5,WHITE));
		whiteColl.add(new Prawn(4,5,WHITE));
		boardLayout[2][4] = white;
		boardLayout[3][4] = white;
		boardLayout[4][4] = white;
		boardLayout[5][4] = white;
		boardLayout[3][5] = white;
		boardLayout[4][5] = white;
		
		blackColl.add(new Prawn(3,8,BLACK));
		blackColl.add(new Prawn(4,8,BLACK));
		blackColl.add(new Prawn(2,9,BLACK));
		blackColl.add(new Prawn(3,9,BLACK));
		blackColl.add(new Prawn(4,9,BLACK));
		blackColl.add(new Prawn(5,9,BLACK));
		boardLayout[3][8] = black;
		boardLayout[4][8] = black;
		boardLayout[2][9] = black;
		boardLayout[3][9] = black;
		boardLayout[4][9] = black;
		boardLayout[5][9] = black;
	}
	
	
	public int getRemovedX(){
		return removedPosX;
	}
	public int getRemovedY(){
		return removedPosY;
	}
}
