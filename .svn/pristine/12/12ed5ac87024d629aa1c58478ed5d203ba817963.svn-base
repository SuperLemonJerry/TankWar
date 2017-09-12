package dataclass;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import TankClients.TankClient;

public class Forest extends Barrier{

	TankClient tc = null;
	List<Tree> forest = new ArrayList<Tree>();	

	public Forest(int x, int y, int rows, int cols){
		super(x, y, rows, cols);
				
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++){
				forest.add(new Tree(x+j*Missile.WIDTH, y+i*Missile.HEIGHT));
			}
		}
		
		this.barType = Barrier.FOREST_BAR;
	}
	
	public Forest(int x, int y, int rows, int cols, TankClient tc) {
		this(x, y, rows, cols);
		this.tc = tc;
	}
		
	public void draw(Graphics g){
		for (int i=0; i<forest.size(); i++){
			Tree tree = forest.get(i);
			tree.draw(g);
		}
	}
	
	private class Tree{
		private int x;
		private int y;
		
		public Tree(int x, int y){
			this.x = x;
			this.y = y;	
		}
		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.GREEN);
			g.fillRect(x, y, 10, 10);
			g.setColor(c);
		}
		
	}
}