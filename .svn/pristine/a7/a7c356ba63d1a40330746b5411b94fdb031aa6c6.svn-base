package dataclass;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import TankClients.Tank;
import TankClients.TankClient;

public class Base extends Barrier{
	
	private TankClient tc = null;
	private int life = 100;
	private static Image image = null;
	private boolean live = true;
//	private boolean good = true;
	private int tankType;
	
	static {
		try {
			image = ImageIO.read(new File("src/images/MyBase.png"));
		} catch (Exception e) {
		
		}
	}
	
	public boolean isLive() {
		return live;
	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}


	public Base(int x, int y, int rows, int cols){
		super(x, y, rows, cols);
		this.w = 60;
		this.h = 60;
		
	}
	
	public Base(int x, int y, int rows, int cols, TankClient tc){
		this(x, y, rows, cols);
		this.tc = tc;
	}
	
	public void draw(Graphics g){	
		if (!this.live) return;
		g.drawImage(image, getX(), getY(), w, h, tc);
	}
	
	public boolean collidesWithTank(Tank tank){
		if (this.getRect().intersects(tank.getRect())){
			return true;
		}
		return false;
	}
	
	public void beHit(Missile missile){
		this.life -= 5;
		if (this.life == 0){
			this.live = false;
		}	
	}

	public int getTankType() {
		return tankType;
	}

	public void setTankType(int tankType) {
		this.tankType = tankType;
	}

}
