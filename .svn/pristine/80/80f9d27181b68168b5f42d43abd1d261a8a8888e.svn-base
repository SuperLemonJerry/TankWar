package dataclass;

import java.awt.Graphics;
import java.awt.Rectangle;

import TankClients.GameObject;
import TankClients.Tank;


public abstract class Barrier extends GameObject {
	public static final int UNIT_WIDTH = 10;
	public static final int UNIT_HEIGHT = 10;
	
	public static final int WALL_BAR = 1;
	public static final int FOREST_BAR = 2;
	public static final int RIVER_BAR = 3;
	
	private int x;
	private int y;
	int w;
	int h;	
	private int rows;
	private int cols;
	public int barType;

	public Barrier(int x, int y, int rows, int cols){
		this.setX(x);
		this.setY(y);
		this.setRows(rows);
		this.setCols(cols);
		this.w = cols * UNIT_WIDTH;
		this.h = rows * UNIT_HEIGHT;
	}
	
	public abstract void draw(Graphics g);

	public boolean collidesWithTank(Tank tank) {		
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(getX(), getY(), w, h);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
}
