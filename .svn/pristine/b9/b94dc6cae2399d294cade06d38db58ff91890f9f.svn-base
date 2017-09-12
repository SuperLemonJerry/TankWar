package dataclass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import TankClients.Tank;
import TankClients.TankClient;

public class Wall extends Barrier{

	TankClient tc = null;
	List<Brick> wall = new ArrayList<Brick>();	
	
	
	public Wall(int x, int y, int rows, int cols){
		super(x, y, rows, cols);
		
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++){
				wall.add(new Brick(x+j*Missile.WIDTH, y+i*Missile.HEIGHT));
			}
		}
		this.barType = Barrier.WALL_BAR;
		
	}
	
	public Wall(int x, int y, int rows, int cols, TankClient tc) {
		this(x, y, rows, cols);
		this.tc = tc;	
	}
		
	public void draw(Graphics g){
		for (int i=0; i<wall.size(); i++){
			Brick brick= wall.get(i);
			brick.draw(g);
		}
	}
		
	public boolean collidesWithTank(Tank tank){
		for (int i=0; i<wall.size(); i++){
			Brick brick = wall.get(i);
			if (tank.getRect().intersects(brick.getRect())){
				return true;//Åö×²·µ»Øtrue
			}
		}	
		return false;
	}
	
	public boolean collidesWithMissile(Missile missile){
		for (int i=0; i<wall.size(); i++){
			Brick brick = wall.get(i);
			if (missile.getRect().intersects(brick.getRect())){
				return true;
			}
		}
		return false;
	}
	
	public void beHitByBullet(Missile missile){//×Óµ¯ÆÆ»µ
		for (int i=0; i<wall.size(); i++){
			Brick brick = wall.get(i);
			if (missile.getRect().intersects(brick.getRect())){
				wall.remove(brick);
			}
		}
	}	

	private class Brick{
		private int x;
		private int y;
		
		public Brick(int x, int y){
			this.x = x;
			this.y = y;	
		}
		
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, 10, 10);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x, y, 10, 10);
			g.setColor(c);
		}
		
		public Rectangle getRect(){
			return new Rectangle(this.x, this.y, 10, 10);
		}
	}

}
