package game;

import java.util.Vector;

public class GameEngine {

	final static int INFINITY = Integer.MAX_VALUE;
	static long startTime; 
	static long endTime;
	final static int CAMEL0T = 100; //one camelot worth 100  
	
	final static int RANDOM_WEIGHT=10; // weight factor
	static int opponent(int turn){
		return turn==Board.BLACK ? Board.WHITE : Board.BLACK;
	 }
	//Cutoff function	
	public static int Evaluation(int[][] position,int turn){
		int score=0;
	    int opponent = opponent(turn);
	    if(Move.checkWinState(position,turn)){
	    	//System.out.println("score win: "+ 150000+" turn: "+ turn);
	    	return 150000;
	    }
	   
	    for (int i=0; i<8; i++)
	      for (int j=0; j<14; j++)
		  {
		    if (position[i][j] == turn)
		      {
				score += CAMEL0T;
				//checking if any of the piece might killed in the next move
				if (Move.canBeCapture(position,i,j)) {
					score -= 100 ;
				}
				
				//Awarding the point depending on their placement from the goal region 
				if(position[i][j] == Board.WHITE){
					switch(j){
						
						case 8:
							score +=20;
							break;
						case 9:
							score +=30;
							break;
						case 10:
							score +=40;
							break;
						case 11:
							score +=50;
							break;
						case 12:
							score +=80;
							break;
						case 13:
							score +=100;
							break;
					}
				
				}
				else if(turn == Board.BLACK){
						switch(j){
						
						case 5:
							score +=20;
							break;
						case 4:
							score +=30;
							break;
						case 3:
							score +=40;
							break;
						case 2:
							score +=50;
							break;
						case 1:
							score +=60;
							break;
						case 0:
							score +=70;
							break;
					}
				}	
			}
		    //Checking how many coins of opponent are present
		    else if(position[i][j] == opponent){
		    	score -=100;
		    	int oppPos = j-8;
		    	if(oppPos<=0 && opponent == Board.BLACK){
		    		score -= Math.abs(oppPos)*10;
		    	}
		    	else if(oppPos>0 && opponent == Board.WHITE){
		    		
			    		score -= Math.abs(oppPos)*10;
			    	
		    	}
		    	/*if(opponent == Board.BLACK){
			    	switch(j){
				    	case 5:
							score +=20;
							break;
						case 4:
							score +=30;
							break;
						case 3:
							score +=40;
							break;
						case 2:
							score +=50;
							break;
						case 1:
							score +=60;
							break;
						case 0:
							score +=70;
							break;
				    	}
		    	}*/
		    }
		  }

	    score += (int)(Math.random() * RANDOM_WEIGHT);
	   // System.out.println("score win: "+ score+" turn: "+ turn);
	    return score;
	    
	 }
	//End of Cutoff
	
	//MinMax call
	 public static int MiniMax(int[][] board, int depth, int max_depth, int[] the_move, int toMove, int[] counter)
	  {
		 startTime = System.currentTimeMillis();
		 return MiniMax(board,depth,max_depth,the_move,toMove,counter,-INFINITY,INFINITY);
	  }
	 
	 //Initializing turn at the start to infinity and - infinity
	 static int which_turn(int turn) {
	 		
		    return Move.color(turn) == CamelotBoard.AIturn ? -INFINITY : INFINITY;
	}
	 
	 //MinMax Calculation
	static int MiniMax(int[][] board, int depth, int max_depth,int[] the_move, int turn, int[] counter, int alpha, int beta)
	  {
			
			int the_score=0;
			int[][] new_board=new int[8][14];
			int best_score, chosen_score;
			int[] best_move=new int[4];
			
			DisplayParameter.maxDepthLbl = Math.max(DisplayParameter.maxDepthLbl, depth+1);
			//System.out.println("depth:"+depth);
			Vector result_list = new Vector();
			Vector moves_list = new Vector();//vector of 4x1 arrays
			
			Thread.yield();
		 	endTime = System.currentTimeMillis();
		 	if(((endTime-startTime)/1000F>8.5)||depth==max_depth){
		 		//System.out.println("MaDepth reached");
		 		best_score = Evaluation(board,opponent(turn));
				counter[0]++;
				return best_score;
				
		 	}
		 	
		    else {
		    	result_list = Move.generate_moves(board,turn);
		    	if(result_list.size()!= 0){
			    	moves_list = (Vector) result_list.get(0);
			    	if(moves_list.size() == 0){
			    		moves_list = (Vector) result_list.get(1);
			    	}
		    	}
		    	else{
		    		result_list = moves_list;
		    	}
				best_score=which_turn(turn);
				switch (moves_list.size())
				{
					case 0:
						best_score = Evaluation(board,opponent(turn));
						counter[0]++;
						return best_score;
					case 1:
						if (depth == 0)  
						{                  
							// forced move: immediately return control
							best_move = (int[])moves_list.elementAt(0);
							for (int k=0; k<4; k++)
								the_move[k]=best_move[k];
							best_score = Evaluation(board,opponent(turn));
							return best_score;
						}
						
				}    

				for (int i=0;i<moves_list.size();i++){
					DisplayParameter.nodeLbl++;
					new_board = copy_board(board);  //board need not be touched
			        Move.move_board(new_board, (int[])moves_list.elementAt(i)); //returns new_board
					int temp[] = new int[4];
					the_score= MiniMax(new_board, depth+1, max_depth, temp, opponent(turn), counter, alpha, beta);
					
					//Incase of max turn
					if (turn==CamelotBoard.AIturn && the_score > best_score) {
						best_move = (int[])moves_list.elementAt(i);
						best_score = the_score;
						if (best_score > alpha) 
						{
							if (best_score >= beta) {
								DisplayParameter.prunMax++;
								break;  /*  alpha_beta cutoff  */
							}
							else
								alpha = best_score;  //the_score
						}
				}
					//Incase of min turn
				else if (turn != CamelotBoard.AIturn && the_score < best_score) {
					best_move = (int[])moves_list.elementAt(i);
					best_score = the_score;
					if (best_score < beta) 
					{
						if (best_score <= alpha) {
							DisplayParameter.prunMin++;
							break;  /*  alpha_beta cutoff  */
						}
						else
						 	beta = best_score;  //the_score
					}
				}
					
			
		    } //end for
				depth -= 1;

			}
		 	System.out.println("========================");
			
		 	for (int k=0; k<4; k++){
		    	the_move[k]=best_move[k];
		    	System.out.print(the_move[k]);
		    	System.out.print(",");
		 	}
		 	System.out.println("best_score:"+best_score+"\n");
		 	System.out.println("========================");
		    return best_score;
			
	  }
	//Temp copy of board
	  static int[][] copy_board(int[][] board){
		    int[][] copy = new int[8][14];

		    for (int i=0; i<8; i++)
		      System.arraycopy(board[i],0,copy[i],0,14);
		    return copy;
	}
	//Random value generation  
	public int[] randomMove(int[][] board, int turn){
		
		int move;
		int[] move_array = new int[4];
		Boolean isCapture = false;
		 for (int i=0; i<8; i++){
			 for (int j=0; j<14; j++){
				if (turn==Move.color(board[i][j])) {
					if (Move.canCapture(board,i,j)) {
						
						 for (int k=-2,mid_i=-1; k<=2; k+=2,mid_i+=1){
							 for (int l=-2,mid_j=-1; l<=2; l+=2,mid_j+=1)
									 {
										move=Move.IsMoveLegal(board,i,j,i+k,j+l,turn);
										if (move == Move.LEGALMOVE && (Move.jumpI>-1 && Move.jumpJ>-1))
										{
											
											move_array[0]=i; move_array[1]=j;
											move_array[2]=i+k; move_array[3]=j+l;
											isCapture = true;
											break;
										} 
									} 
							 if(isCapture){
								 break;
							 }
						 }
					}
					
				}
				if(isCapture){
					 break;
				}
				
			
			 }
			 if(isCapture){
				 break;
			 }
		
		 }
		 //Randomly choosing value of i and j
		 if(!isCapture){
			 while(true){
				 Boolean found = false;
				 int i = (int)(Math.random()*7);
				 int j = (int)(Math.random()*7);
				 for (int k=-1; k<=1; k+=1){
						for (int l=-1; l<=1; l+=1){
							if (Move.inRange(i+k,j+l))
							{
								move=Move.IsWalkLegal(board,i,j,i+k,j+l,turn);
								if (board[i+k][j+l]==Board.EMPTY && move == Move.LEGALMOVE)
								{
									move_array[0]=i; move_array[1]=j;
									move_array[2]=i+k; move_array[3]=j+l;
									found = true;
									break;
								} 
								
							}							
						}
						if(found){
							break;
						}
				 }
				 if(found)
					 break;
			 }
		 }
		 return move_array;
	}
}
