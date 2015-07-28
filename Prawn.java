package game;

public class Prawn {
	private int posX;
	private int posY;
	private int color;
	
	public Prawn(int x, int y, int color){
		this.posX = x;
		this.posY = y;
		this.color = color;
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public int getcolor(){
		return color;
	}
	public void setXY(int x, int y){
		
		this.posX = x;
		this.posY = y;
	}

}
