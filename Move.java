package game;

import java.util.*;



class Move 
{
        final static int LEGALMOVE = 1;
        final static int ILLEGALMOVE = 2;
        public static int jumpI = -1;
    	public static int jumpJ = -1;
        
    	
    	//Removed piece position is stored if the move is capturing 
        public static void remove(int mid_i, int mid_j, int turn,int[][] position){
    		
    		//Capturing move
        	if(inRange(mid_i,mid_j)){   
	    		if(turn != position[mid_i][mid_j]){
	    			jumpI = mid_i;
	    			jumpJ = mid_j;
	    			//removed = true;
	    		}
	    		else{
	    			jumpI = -1;
	    			jumpJ = -1;
	    		}
        	}
    	}
        static boolean noMovesLeft(int[][] position,int toMove)
        {
               
                return true;
        }
        
        //Checks whether current state is win state
        static boolean checkWinState(int[][] position,int turn){
    		
    		boolean allOpponentsKilled = true;
    		
    		int opponent = (Seed.getType(turn) == Seed.BLACK)?Seed.WHITE.getCode():Seed.BLACK.getCode();
        	if(Seed.BLACK.getCode() == turn){
    			
    			if(position[3][0] == turn || position[4][0] == turn){
    				
    				return true;
    			}
    			
    		}
    		else if(position[3][13] == turn || position[4][13] == turn){
    			
    			return true;
    		}
    		for(int i = 0; i<8; i++){
    			if(allOpponentsKilled){
	    			for(int j = 0; j<14; j++){
	    				
	    				if(position[i][j] == opponent){
	    					
	    					allOpponentsKilled = false;
	    					break;
	    				}
	    			}
    			}
    			else{
    				break;
    			}
    		}
    		if(allOpponentsKilled)
    			return true;
    		else
    			return false;
    	}              


        // ApplyMove checks if the move entered is legal, illegal and make changes on the board
        static int ApplyMove(int[][] position,int start_i,int start_j,int end_i,int end_j,int mid_i,int mid_j)
        {
		      
                {
                        if ((Math.abs(end_i - start_i) == 1 && end_j == start_j)||
                				(Math.abs(end_j - start_j) == 1 && end_i == start_i)||
                				(Math.abs(end_i- start_i) == 1 && Math.abs(end_j - start_j) == 1))
                        {
                                position[end_i][end_j] = position[start_i][start_j];
                                position[start_i][start_j] = Board.EMPTY;
                        }
                        else // capture
                        {
                        	if(mid_i>-1 && mid_j>-1){
                                position[mid_i][mid_j] = Board.EMPTY;
                                position[end_i][end_j] = position[start_i][start_j];
                                position[start_i][start_j] = Board.EMPTY;
                        	}
                        }

                       
                }

                return LEGALMOVE;
        }

        // IsMoveLegal checks if the move entered is legal.
        // Returns ILLEGALMOVE or LEGALMOVE;
        // have to check with canCapture(int[][],int,int) to see
        
        static int IsMoveLegal(int[][] position,int start_i,int start_j,int end_i,int end_j,int turn)
        {
        	
        	jumpI = -1;
        	jumpJ = -1;
			if (! (inRange(start_i,start_j) && inRange(end_i,end_j) ) )
				return ILLEGALMOVE;
            if (position[end_i][end_j] != Board.EMPTY)
                return ILLEGALMOVE;

            int piece = position[start_i][start_j];
            
            //checking jump length if more than 2
            if((Math.abs(start_i - end_i)>2) || (Math.abs(start_j - end_j)>2)){
            	return ILLEGALMOVE;
            }
            
          //Motion  adjacent or diagonal check
            if ((Math.abs(end_i - start_i) == 1 && end_j == start_j)||
    				(Math.abs(end_j - start_j) == 1 && end_i == start_i)||
    				(Math.abs(end_i- start_i) == 1 && Math.abs(end_j - start_j) == 1))
            {
                // checking if captures are possible because it is mandatory rule
                switch (Seed.getType(piece))
                {
					case WHITE:
						for (int i=0;i<8;i++)
							for (int j=0;j<14;j++)
	                        {
								if ((position[i][j] == Seed.WHITE.getCode()) && canCapture(position,i,j))
									return ILLEGALMOVE;
	                        }
						break;
					case BLACK:
	                    for (int i=0;i<8;i++)
	                        for (int j=0;j<14;j++)
	                        {
	                            if ((position[i][j] == Seed.BLACK.getCode()) && canCapture(position,i,j))
	                                return ILLEGALMOVE;
						}
	                    break;
				} 
                // Ending of checking capture
                
                if(position[end_i][end_j] == Seed.EMPTY.getCode())
        		{
        			return LEGALMOVE;
        		}
                
				return ILLEGALMOVE;

			} 

            else if ((Math.abs(end_i - start_i) == 2 && end_j == start_j)||
    				(Math.abs(end_j - start_j) == 2 && end_i == start_i)||
    				(Math.abs(end_i - start_i) == 2 && Math.abs(end_j - start_j) == 2)&&
    				position[end_i][end_j] == Seed.EMPTY.getCode())
            {
            	for (int k=-2,mid_i=-1; k<=2; k+=2,mid_i+=1)
					 for (int l=-2,mid_j=-1; l<=2; l+=2,mid_j+=1)
							 {
						 		if((start_i+k == end_i) && (start_j+l == end_j)){
						 			
						 			if(inRange(start_i+mid_i,start_j+mid_j)){
						 				if(position[start_i+mid_i][start_j+mid_j]==0){
						 					return ILLEGALMOVE;
						 				}
						 				else{
						 					remove(start_i+mid_i,start_j+mid_j,piece,position);
				    						return LEGALMOVE;
						 				}
						 			}
						 		}
								
							}
            	
			}
            return ILLEGALMOVE;
        }
        
        
        //Checks single step in any direction is legal or not
        static int IsWalkLegal(int[][] position,int start_i,int start_j,int end_i,int end_j,int turn)
        {
			if (! (inRange(start_i,start_j) && inRange(end_i,end_j) ) )
				return ILLEGALMOVE;
            if (position[end_i][end_j] != 0)
                return ILLEGALMOVE;

            return LEGALMOVE;
        }

  
        // checks a board position to see if the piece indicated at (i,j) can make a capture
        static boolean canCapture(int[][] position, int i, int j)
        {
                
                boolean result = false;
                int piece = position[i][j];
                int oppPiece = (Seed.getType(piece) == Seed.BLACK)?Seed.WHITE.getCode():Seed.BLACK.getCode(); 
                
                for (int k=-2,mid_i=-1; k<=2; k+=2,mid_i+=1){
                	
					 for (int l=-2,mid_j=-1; l<=2; l+=2,mid_j+=1)
							 {
						 	//System.out.println("i+mid_i: "+i+mid_i+" : j+mid_j: "+j+mid_j);
								if(inRange(i+mid_i,j+mid_j)&&inRange(i+k,j+l)){
									if (position[i+mid_i][j+mid_j] == oppPiece && position[i+k][j+l]==0)
									{
										result = true;
										break;
									} // if (move)
									
								}
							}
					 if(result){
						 break;
					 }
                }
                return result;
                
        } 
        //End of Can Capture

        // canWalk() returns true if the piece on (i,j) can make a
        
       

        

      
		// returns the color of a piece
		static int color(int piece)
		{
			if(piece == Board.WHITE)
				return Board.WHITE;
			if(piece ==  Board.BLACK)
				return Board.BLACK;
			if(piece == Board.EMPTY)
				return Board.EMPTY;
			return Board.INVALID;
		}

		// check that i is between 0 and 7 and j is between 0 and 13 inclusive
        public static boolean inRange(int i, int j)
		{
			switch(i){
				
				case 0:
				case 7:
					if(j>=3 && j<=10){
						return true;
					}
					else
						return false;
			case 1:
				case 6:
					if(j>=2 && j<=11){
						return true;
					}
					else
						return false;
			case 2:
				case 5:
					if(j>=1 && j<=12){
						return true;
					}
					else
						return false;
			case 3:
				case 4:
					if(j>=0 && j<=13){
						return true;
					}
					else
						return false;
			default:
					return false;
				
			}
		}

        static boolean canBeCapture(int[][] position, int i, int j){
			
        	int turn = position[i][j];
        	int opponent = GameEngine.opponent(turn); 
        	for (int k=-1; k<=1; k+=1)
				for (int l=-1; l<=1; l+=1){
					if (inRange(i+k,j+l))
					{
						if(position[i+k][j+l]==opponent){
							
							return true;
						}
					}
				}
        	
        	return false;
        	
        }
		
    //given a board, generates all the possible moves depending on whose turn
	static Vector  generate_moves(int[][] board, int turn) {
		Vector moves_list = new Vector();
		Vector capture_list = new Vector();
		boolean capture = false;
		int move;
		if(checkWinState(board, turn))
			 return moves_list;
		 for (int i=0; i<8; i++){
			 for (int j=0; j<14; j++){
				if (turn==color(board[i][j])) {
					if (canCapture(board,i,j)) {
						
						 for (int k=-2,mid_i=-1; k<=2; k+=2,mid_i+=1)
							 for (int l=-2,mid_j=-1; l<=2; l+=2,mid_j+=1)
									 {
										move=IsMoveLegal(board,i,j,i+k,j+l,turn);
										if (move == LEGALMOVE && (jumpI>-1 && jumpJ>-1))
										{
											int[] int_array = new int[4];
											int_array[0]=i; int_array[1]=j;
											int_array[2]=i+k; int_array[3]=j+l;
											int[][] temp_board = GameEngine.copy_board(board);
											move = Move.ApplyMove(temp_board,i,j,i+k,j+l,i+mid_i,j+mid_j);
											//moves_list.addElement(int_array);
											capture_list.addElement(int_array);
										} 
									} 
					}
					else {
						
					for (int k=-1; k<=1; k+=1)
						for (int l=-1; l<=1; l+=1){
							if (inRange(i+k,j+l))
							{
								move=IsWalkLegal(board,i,j,i+k,j+l,turn);
								if (board[i+k][j+l]==Board.EMPTY && move == LEGALMOVE	)
								{
									int[] int_array = new int[4];
									int_array[0]=i; int_array[1]=j;
									int_array[2]=i+k; int_array[3]=j+l;
									//a walk has taken place
									//add the new array to the Vector moves_list
									moves_list.addElement(int_array);
								} 
							} 
					} 
						
			 }
		 }
		}
		}
		 Vector result = new Vector();
		 result.addElement(capture_list);
		 result.addElement(moves_list);
		 return  result;
		// return moves_list;

	}
	//End of generate_moves
	
//"apply move" in the Minimax.  simply moves the board give moves
static void move_board(int[][] board, int[] move)
{
	int startx = move[0];
	int starty = move[1];
	int endx = move[2];
	int endy = move[3];
	int midx = -1;
	int midy =-1;
	//while (endx>0 || endy>0)
	{
		if ((Math.abs(endx - starty) == 2 && endy == starty)||
				(Math.abs(endy - starty) == 2 && endx == startx)||
				(Math.abs(endx - startx) == 2 && Math.abs(endy - starty) == 2)&&
				board[endx][endy] == Seed.EMPTY.getCode())
        {
        	for (int k=-2,mid_i=-1; k<=2; k+=2,mid_i+=1)
				 for (int l=-2,mid_j=-1; l<=2; l+=2,mid_j+=1)
						 {
					 		if((startx+k == endx) && (starty+l == endy)){
					 			
					 			if(inRange(startx+mid_i,starty+mid_j)){
					 				midx = startx+mid_i;
					 				midy = starty+mid_j;
					 			}
					 		}
							
						}		
		
	} 
		
		ApplyMove(board,startx,starty,endx,endy,midx,midy);
}
}

//Not working properly so commented successive capture and cantering move
//add all of them to moves_list 
/*private static void forceCaptures(int[][] board, int turn, int[] move, Vector moves_list,int inc){
int newx = move[2], newy = move[3], opponent;

while (newx>7 || newy>7){
	newx/=10;
	newy/=10;
}//end while 
for (int i=-2; i<=2; i+=4)
 for (int j=-2; j<=2; j+=4)
   if (inRange(newx+i,newy+j)) {
		int[][] tempPosition = GameEngine.copy_board(board);
		int moveResult = ApplyMove(tempPosition,newx,newy,newx+i,newy+j);
		if (moveResult == LEGALMOVE) {
			int[] new_move = new int[4];
			new_move[0] = move[0];
			new_move[1] = move[1];
			new_move[2] = move[2]+(newx+i)*inc;
			new_move[3] = move[3]+(newy+j)*inc;
			moves_list.addElement(new_move);
		} // if (moveResult == LEGALMOVE)
		else if (moveResult == INCOMPLETEMOVE)
		{
			int[] new_move = new int[4];
			new_move[0] = move[0];
			new_move[1] = move[1];
			new_move[2] = move[2]+(newx+i)*inc;
			new_move[3] = move[3]+(newy+j)*inc;
			
			forceCaptures(tempPosition, turn, new_move, moves_list, inc*10);
		}
	 } //end if for

}*/


} // End of class Move



















