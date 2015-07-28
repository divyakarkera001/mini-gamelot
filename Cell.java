package game;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
/*To hold the Jpanel*/


public class Cell extends JPanel {
    private int x = -1;
    private int y = -1;
    
    public Cell(int x, int y) {
       super(new BorderLayout());
       this.x = x;
       this.y = y;
    }

    public int getIndexX() { return x; }
    public int getIndexY() { return y; }
}