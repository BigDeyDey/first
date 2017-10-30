package lifegame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class World extends JPanel implements Runnable,MouseListener,MouseMotionListener{
	private static final long serialVersionUID = -3742703266219985169L;
	private final int rows;
	private final int columns;
	private final CellStatus[][] generation1;
	private final CellStatus[][] generation2;
	private CellStatus[][] currentGeneration;
	private CellStatus[][] nextGeneration;
	private boolean isChanging=false;
	//初始化游戏地图
	public World(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		generation1 = new CellStatus[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				generation1[i][j] = CellStatus.Dead;
			}
		}
		
		generation2 = new CellStatus[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				generation2[i][j] = CellStatus.Dead;
			}
		}
		
		currentGeneration = generation1;
		nextGeneration = generation2;    
	}
	
	
	@Override
	public void run(){
		addMouseListener(this);  
        addMouseMotionListener(this);  
      
		while(true){
			
			while(isChanging)
			{
				try 
				{
					this.wait();
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
				repaint();
				sleep(1);
				
				
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < columns; j++)
					{
						evolve(i, j);
					}
				}
			
				CellStatus[][] temp = null;
				temp = currentGeneration;
				currentGeneration = nextGeneration;
				nextGeneration = temp;
				
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < columns; j++)
					{
						nextGeneration[i][j] = CellStatus.Dead;
					}
				}
			
		}
	}
	

	  
	public void mouseClicked(MouseEvent e){ }     
    public void mousePressed(MouseEvent e){  
        int cellX = e.getX()/20;  
        int cellY = e.getY()/20;  
        generation1[cellY][cellX] = CellStatus.Active;
        generation2[cellY][cellX] = CellStatus.Active;
        repaint();  
    }  
    public void mouseReleased(MouseEvent e){}  
    public void mouseEntered(MouseEvent e){}  
    public void mouseExited(MouseEvent e){}  
    public void mouseDragged(MouseEvent e){  
        this.mousePressed(e);   
    }  
    public void mouseMoved(MouseEvent e){}
  
	
	@Override
	//绘制细胞状态
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
            	if(currentGeneration[i][j] == CellStatus.Active)
            	{
            		g.fillRect(j * 20, i * 20, 20, 20);
            	}
            	else
            	{
                    g.drawRect(j * 20, i * 20, 20, 20);            		
            	}
            }
        }
    }	
	
		
	//得出后来细胞的状态
	public void evolve(int x, int y){
		int activeSurroundingCell = 0;
		
		if(isValidCell(x - 1, y - 1) && (currentGeneration[x - 1][y - 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}

		if(isValidCell(x, y - 1) && (currentGeneration[x][y - 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}	
		
		if(isValidCell(x + 1, y - 1) && (currentGeneration[x + 1][y - 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}

		if(isValidCell(x + 1, y) && (currentGeneration[x + 1][y] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}
		
		if(isValidCell(x + 1, y + 1) && (currentGeneration[x + 1][y + 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}

		if(isValidCell(x, y + 1) && (currentGeneration[x][y + 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}
		
		if(isValidCell(x - 1, y + 1) && (currentGeneration[x - 1][y + 1] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}

		if(isValidCell(x - 1, y) && (currentGeneration[x - 1][y] == CellStatus.Active))
		{
			activeSurroundingCell++;
		}	
		
		if(activeSurroundingCell == 3)
		{
			nextGeneration[x][y] = CellStatus.Active;
		}
		else if(activeSurroundingCell == 2)
		{
			nextGeneration[x][y] = currentGeneration[x][y];
		}
		else
		{
			nextGeneration[x][y] = CellStatus.Dead;
		}
	}
	
	//判断细胞是否在区域内
	public boolean isValidCell(int x, int y){
		if((x >= 0) && (x < rows) && (y >= 0) && (y < columns))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//刷新间隔时间
	private void sleep(int x){
		try 
		{
			Thread.sleep(1000 * x);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	static enum CellStatus{
		Active,
		Dead;
	}
	
	public void setCurrent(int i,int j){
		generation1[i][j] = CellStatus.Active;
		currentGeneration = generation1;
	}
	
	public CellStatus getNext(int i,int j){
		return nextGeneration[i][j];
		
	}
	
	
}