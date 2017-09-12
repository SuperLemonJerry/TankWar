package dataclass;

import java.awt.*;
import java.util.List;

import TankClients.AITank;
import TankClients.Direction;
import TankClients.Explode;
import TankClients.GameObject;
import TankClients.Tank;
import TankClients.TankClient;

public class Missile extends GameObject{
	public static final int XSPEED = 20;
	public static final int YSPEED = 20;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public int FIRING_RANGE = 600;//…‰≥Ã
	private int life = FIRING_RANGE;
	private int x, y;
	private Direction dir;
	private static int ID = 0;
	private int id = 0;
	
	private int tankID;
	private boolean superBullet = false; //≥¨º∂¥©«ΩµØ
	private Boolean bullet = false;
	private boolean live = true;
	private TankClient tc;
	private int tankType;
	
	public boolean isSuperBullet() {
		return superBullet;
	}

	public void setSuperBullet(boolean bullet) {
		this.superBullet = bullet;
	}

	public boolean isLive() {
		return live;
	}

	public Missile(int tankID, int x, int y, Direction dir){
		this.setX(x);
		this.setY(y);
		this.setDir(dir);
		this.settankID(tankID);
		this.setId(Missile.ID++);
	}
	
	public Missile(int tankID, int x, int y, int tankType, Direction dir, TankClient tc){
		this(tankID, x, y, dir);
		this.tc = tc;
		this.setTankType(tankType);
				
	}
	
	public void draw(Graphics g){
		if (!isLive()){
			tc.missiles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		if (getTankType() == Tank.RED) g.setColor(Color.red);
		else g.setColor(Color.BLUE);
		g.fillOval(getX(), getY(), 10, 10);
		g.setColor(c);
		
		move();
	}

	private void move() {
		
		switch(getDir()){
		case L:
			setX(getX() - XSPEED);
			break;
		case U:
			setY(getY() - YSPEED);
			break;
		case R:
			setX(getX() + XSPEED);
			break;
		case D:
			setY(getY() + YSPEED);
			break;
		}	
		this.life -= XSPEED;
		this.life -= YSPEED;
		if (getX() < 0 || getY() < 0 || getX() > TankClient.GAME_WIDTH || getY() > TankClient.GAME_HEIGHT || this.life == 0){
			setLive(false);			
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if (this.isLive() && this.getRect().intersects(t.getRect()) && t.isLive() && this.getTankType() != t.getTankType()){ 
			t.beHitBy();
			this.setLive(false);
			Explode e = new Explode(getX(), getY(), tc);
			tc.explodes.add(e);
			tc.gameObjects.add(e);
			return true;
		}		
		return false;
	}

	public boolean hitTanks(List<Tank> tanks){
		for (int i=0; i<tanks.size(); i++){
			if (hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if (this.isLive() && w.collidesWithMissile(this)){
			
			if (superBullet){
				w.beHitByBullet(this);
			}
			else if (bullet){
				w.beHitByBullet(this);
				this.setLive(false);
				return true;	
			}else
			{
				this.setLive(false);
				return true;	
			}		
		}
		return false;
	}
	
	public boolean hitWall(List<Wall> walls) {
		for (Wall wall : walls) {
			if (this.hitWall(wall)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitBase(Base base){
		if ( this.isLive() && this.getRect().intersects(base.getRect()) && base.isLive()){
			this.setLive(false);
			if (this.getTankType() != base.getTankType()){
				base.beHit(this);
				Explode e = new Explode(getX(), getY(), tc);
				tc.explodes.add(e);
				tc.explodes.add(e);
				return true;
			}			
		}
		return false;		
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

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public int gettankID() {
		return tankID;
	}

	public void settankID(int tankID) {
		this.tankID = tankID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getTankType() {
		return tankType;
	}

	public void setTankType(int tankType) {
		this.tankType = tankType;
	}
}
