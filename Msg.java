package game;

public class Msg {
	
	private boolean state;
	private String msg;
	
	public Msg(){};
	public Msg(boolean state, String msg){
		
		this.state = state;
		this.msg = msg;
	}
	public void setMsg(String msg){
		
		this.msg = msg;
	}
	
	public void setState(boolean state){
		
		this.state = state;
	}
	
	public String getMsg(){
		
		return msg;
	
	}
	public boolean getState(){
		
		return state;
	
	}

}
