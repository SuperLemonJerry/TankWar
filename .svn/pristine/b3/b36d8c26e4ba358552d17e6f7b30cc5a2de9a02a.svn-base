package dataclass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import TankClients.Tank;
import TankClients.TankClient;

public class Rivers extends Barrier{

	TankClient tc = null;
	List<Brook> river = new ArrayList<Brook>();	
	
	public Rivers(int x, int y, int rows, int cols){
		super(x, y, rows, cols);
				
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++){
				river.add(new Brook(x+j*Missile.WIDTH, y+i*Missile.HEIGHT));
			}
		}
	
		this.barType = Barrier.RIVER_BAR;
	}
	
	public Rivers(int x, int y, int rows, int cols, TankClient tc) {
		this(x, y, rows, cols);
		this.tc = tc;	
	}
		
	public void draw(Graphics g){
		for (int i=0; i<river.size(); i++){
			Brook brook = river.get(i);
			brook.draw(g);
		}
	}
		
	public boolean collidesWithTank(Tank tank){
		for (int i=0; i<river.size(); i++){
			Brook brook = river.get(i);
			if (tank.getRect().intersects(brook.getRect())){
				return true;//Åö×²·µ»Øtrue
			}
		}	
		return false;
	}

	private class Brook{
		private int x;
		private int y;
		
		public Brook(int x, int y){
			this.x = x;
			this.y = y;	
		}		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.CYAN);
			g.fillRect(x, y, 10, 10);
			g.setColor(c);
		}	
		public Rectangle getRect(){
			return new Rectangle(this.x, this.y, 10, 10);
		}
		
	}
}
