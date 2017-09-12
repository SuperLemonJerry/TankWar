package TankClients;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.List;

import NetMsg.MissileNewMsg;
import NetMsg.TankMoveMsg;

import dataclass.Barrier;
import dataclass.Blood;
import dataclass.Missile;

public class Tank extends GameObject {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 22;
	public static final int HEIGHT = 30;

	public static final int BLUE = 1;
	public static final int RED = 2;

	protected int tankType;
	private int x;
	private int y;
	protected boolean bL = false, bU = false, bR = false, bD = false;
	private Direction dir = Direction.STOP;
	public TankClient tc = null;
	private Direction ptDir = Direction.D;
	protected boolean live = true;
	protected static Random r = new Random();
	protected int step = r.nextInt(28) + 3;
	protected int oldX, oldY;
	protected int life = 100;
	protected BloodBar bb = new BloodBar();// 血槽

	protected int id = 0;

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isLive() {
		return live;
	}

	public Tank(int id,int x, int y, int tankType) {
		this.setX(x);
		this.setY(y);
		this.oldX = x;
		this.oldY = y;
		this.setTankType(tankType);
		this.id = id;

	}

	public Tank(int id, int x, int y, int tankType, TankClient tc) {
		this(id, x, y, tankType);
		this.tc = tc;
	}

	private void drawUpDown(Graphics g) {
		g.draw3DRect(getX(), getY(), 5, 30, true);
		g.draw3DRect(getX() + 15, getY(), 5, 30, false);
		g.draw3DRect(getX() + 5, getY() + 5, 10, 20, false);
		g.fillOval(getX() + 5, getY() + 8, 10, 10);
	}

	private void drawLeftRight(Graphics g) {

		g.draw3DRect(getX() - 1, getY() + 2, 30, 5, true);
		g.draw3DRect(getX() - 1, getY() + 17, 30, 5, false);
		g.draw3DRect(getX() + 4, getY() + 7, 20, 10, false);
		g.fillOval(getX() + 8, getY() + 7, 10, 10);
	}

	public void draw(Graphics g) {

		if (!live) {
			if (getTankType() == Tank.BLUE) {
				tc.aiTanks.remove(this);
				tc.tanks.remove(this);
				return;
			}
		}

		Color c = g.getColor();
		if (getTankType() == Tank.RED) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLUE);
		}

		bb.draw(g);

		switch (getPtDir()) {
		case L:
			this.drawLeftRight(g);
			g.drawLine(getX() + Tank.HEIGHT / 2, getY() + Tank.WIDTH / 2,
					getX(), getY() + Tank.WIDTH / 2);
			break;

		case U:
			this.drawUpDown(g);
			g.drawLine(getX() + Tank.WIDTH / 2, getY() + Tank.HEIGHT / 2,
					getX() + Tank.WIDTH / 2, getY());
			break;

		case R:
			this.drawLeftRight(g);
			g.drawLine(getX() + Tank.HEIGHT / 2, getY() + Tank.WIDTH / 2,
					getX() + Tank.HEIGHT, getY() + Tank.WIDTH / 2);
			break;

		case D:
			this.drawUpDown(g);
			g.drawLine(getX() + Tank.WIDTH / 2, getY() + Tank.HEIGHT / 2,
					getX() + Tank.WIDTH / 2, getY() + Tank.HEIGHT);
			break;
		}
						
		g.setColor(c);
		this.move();
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	protected void move() {
		this.oldX = getX();
		this.oldY = getY();

		switch (getDir()) {
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
		case STOP:
			break;
		}

		if (this.getDir() != Direction.STOP) {
			this.setPtDir(this.getDir());
		}

		if (getX() < 0)
			setX(0);
		if (getY() < 0)
			setY(0);
		if (getX() + Tank.WIDTH > TankClient.GAME_WIDTH)
			setX(TankClient.GAME_WIDTH - Tank.WIDTH);
		if (getY() + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			setY(TankClient.GAME_HEIGHT - Tank.HEIGHT);

	}


	public boolean cloaking() {
		if (bU == true && bL == true || bU == true && bR == true || bD == true
				&& bL == true || bD == true && bR == true) {
			return true;
		}
		return false;
	}

	public Missile fire(Direction dir) {
		if (!live) {
			return null;
		}
		int x = this.getX() + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.getY() + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(id, x, y, getTankType(), dir, tc);
		tc.missiles.add(m);
		tc.gameObjects.add(m);

		if (tc.getMc() != null) {
			MissileNewMsg msg = new MissileNewMsg(m);
			tc.getMc().send(msg);
		}
		return m;

	}

	protected void locateDirection() {// 确定方向

		Direction oldDir = getDir();

		if (bL && !bU && !bR && !bD)
			setDir(Direction.L);
		else if (!bL && bU && !bR && !bD)
			setDir(Direction.U);
		else if (!bL && !bU && bR && !bD)
			setDir(Direction.R);
		else if (!bL && !bU && !bR && bD)
			setDir(Direction.D);
		else if (!bL && !bU && !bR && !bD)
			setDir(Direction.STOP);

		if (getDir() != oldDir && tc.getMc() != null) {
			TankMoveMsg tankMoveMsg = new TankMoveMsg(id, getX(), getY(),
					getDir(), getPtDir());
			tc.getMc().send(tankMoveMsg);
		}

	}

	public Rectangle getRect() {
		return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
	}

	private void stay() {
		setX(oldX);
		setY(oldY);

		setDir(Direction.STOP);
	}

	public boolean collidesWithTank(Tank tank) {
		if (this != tank) {
			if (this.live && tank.isLive()
					&& this.getRect().intersects(tank.getRect())) {
				this.stay();
				return true;
			}
		}
		return false;
	}

	public boolean collidesWithTank(List<AITank> aiTanks) {
		for (int i = 0; i < aiTanks.size(); i++) {
			Tank t = aiTanks.get(i);
			this.collidesWithTank(t);
		}
		return false;
	}

	public boolean collidesWithBarrier(Barrier barrier) {
		if (this.live && barrier.collidesWithTank(this)) {
			this.stay();
			return true;
		}
		return false;
	}

	public void collidesWithBarrier(List<Barrier> barriers) {
		for (Barrier barrier : barriers) {
			this.collidesWithBarrier(barrier);
		}
	}

	protected void superFire() {
		Direction[] dirs = Direction.values();
		for (int i = 0; i < 4; i++) {
			fire(dirs[i]);
		}
	}

	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			if (tankType == Tank.RED)
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLUE);
			g.drawRect(getX(), getY() - 20, WIDTH, 10);

			int w = WIDTH * life / 100;
			g.fillRect(getX(), getY() - 20, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(Blood b) {
		if (this.live && this.getRect().intersects(b.getRect()) && b.isLive()) {
			this.setLife(100);
			b.setLive(false);
			return true;
		}
		return false;
	}

	public void beHitBy() {

		this.setLife(this.getLife() - 20);
		if (this.getLife() <= 0) {
			this.setLive(false);
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTankType() {
		return tankType;
	}

	public void setTankType(int tankType) {
		this.tankType = tankType;
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

	public Direction getPtDir() {
		return ptDir;
	}

	public void setPtDir(Direction ptDir) {
		this.ptDir = ptDir;
	}

}
