package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class LevelSelBtn extends JPanel{
	
	//public class RadioBtn extends JRadioButton

		private int val;
		private JRadioButton radio;
		private JLabel label;
		static int selected = 1;
		private Image icon;
		public LevelSelBtn(int val,String iconPath){
			
			radio = new JRadioButton();
			label = new JLabel(new ImageIcon(iconPath));
			radio.addActionListener(radioActionListenerLevel);
			add(radio);
			add(label);
			this.val =val;
			
			/*URL url = this.getClass().getResource(iconPath);
			final String html = "<html><body><img src='" +iconPath +"' width=128 height=128>";
			//System.out.println(html);
			//this.icon = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("white.png"));
	       // try    {Thread.sleep(500);} catch (InterruptedException e)    {e.printStackTrace();}
	       // setSize(100, 50);
	       // setOpaque(false);
			this.val = val;		
			//setText(html);
			addActionListener(radioActionListener);
			selected = val;*/
		}
		public JRadioButton getRadio(){
			return radio;
		}
		public static void setSelected(int val){
			
			selected = val;
		}
		public LevelSelBtn(int val,String iconPath,Boolean enable){
					
			this(val,iconPath);
			this.setEnabled(enable);
			
		}
		public LevelSelBtn(String label,int val){
			radio = new JRadioButton();
			this.label = new JLabel(label);
			radio.addActionListener(radioActionListenerLevel);
			add(radio);
			add(this.label);
			this.val =val;
			
			
		}
		public LevelSelBtn(String label,int val,Boolean enable){
			
			radio = new JRadioButton();
			this.label = new JLabel(label);
			radio.addActionListener(radioActionListenerLevel);
			add(radio);
			add(this.label);
			this.val =val;
			radio.setSelected(enable);
			
		}
		 @Override
		 public void paint(Graphics g)
		 {
		        g.drawImage(icon, 30, 0, 50, 50, null);
		        super.paint(g);
		 }
		public ActionListener radioActionListenerLevel = new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            if(e.getSource() instanceof JRadioButton){
		            	JRadioButton radioButton = (JRadioButton) e.getSource();
		            	LevelSelBtn parent = (LevelSelBtn) radioButton.getParent();
		                if(radioButton.isSelected()){
		                   // System.out.println);
		                    parent.setSelected(val);
		                }
		            }
		        }
		    };
}



