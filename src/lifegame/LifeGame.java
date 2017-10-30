package lifegame;

import javax.swing.JFrame;


public class LifeGame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final World world;
    
    public LifeGame(int rows, int columns){
		world = new World(rows, columns);
		init();	
	}
    
	public void init(){
		new Thread(world).start();
		add(world);
	}
    
    public static void main(String[] args) {    	
    	LifeGame frame = new LifeGame(40, 50);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1007, 859);
        frame.setTitle("Game of Life");
        frame.setVisible(true);
        frame.setResizable(false);
    }	  
    
}
