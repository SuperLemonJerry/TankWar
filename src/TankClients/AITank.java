package TankClients;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import NetMsg.TankMoveMsg;

public class AITank extends Tank {

	public AITank(int id, int x, int y, int tankType, TankClient tc) {
		super(id, x, y, tankType, tc);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		g.drawString(getId() + "", getX() + 25, getY() - 10);
	}

	protected void move() {

		super.move();

		if (tankType == Tank.BLUE) {
			Direction olddir = getDir();
			this.newAction();
			Direction[] dirs = Direction.values();
			if (step == 0) {
				step = r.nextInt(28) + 3;
				int rn = r.nextInt(dirs.length);
				setDir(dirs[rn]);

				if (getDir() != olddir && tc.getMc() != null) {
					TankMoveMsg tankMoveMsg = new TankMoveMsg(id, getX(),
							getY(), getDir(), getPtDir());
					tc.getMc().send(tankMoveMsg);
				}
			}
			step--;
			if (r.nextInt(40) > 37) {
				this.fire(getPtDir());
			}
		}
	}

	// ��ǰ���
	public Rectangle getFireRange(Direction direction) {
		switch (direction) {
		case L:
		case R:
			return new Rectangle(getX() + Tank.HEIGHT / 2 - 200, getY()
					+ Tank.WIDTH / 2 - 200, 300, 300);
		case U:
		case D:
			return new Rectangle(getX() + Tank.WIDTH / 2 - 200, getY()
					+ Tank.HEIGHT / 2 - 200, 300, 300);

		}
		return new Rectangle();
	}

	//

	public void newAction() {
		if (this.getFireRange(this.getDir()).intersects(
				tc.getMyTank().getRect())) {// ��⵽�ҵ�̹�˽���
			int myTankX = tc.getMyTank().getX();
			int myTankY = tc.getMyTank().getY();
			this.Alert(myTankX, myTankY);
		}
	}

	public void Alert(int myTankX, int myTankY) {// �����ҵ�̹��
		// ������Դ� �� �Ļ�
		// if (Math.abs(myTankX - this.x) == Math.abs(myTankY - this.y) ||
		// myTankX == this.x || myTankY == this.y){
		if (!tc.getMyTank().live)
			return;
		switch (this.getMyTankSite(myTankX, myTankY)) {
		case 2:
			this.fire(Direction.R);
			break;
		case 4:
			this.fire(Direction.D);
			break;
		case 5:
			this.fire(Direction.U);
			break;
		case 7:
			this.fire(Direction.L);
			break;
		// }
		}
		// ����ӽ���
		// else{
		// switch(this.getMyTankSite(myTankX, myTankY)){
		// case 1:this.fire(Direction.RD);break;
		// case 2:this.fire(Direction.R);break;
		// case 3:this.fire(Direction.RU);break;
		// case 4:this.fire(Direction.D);break;
		// case 5:this.fire(Direction.U);break;
		// case 6:this.fire(Direction.LD);break;
		// case 7:this.fire(Direction.L);break;
		// case 8:this.fire(Direction.LU);break;
		// }
		//
		// }

	}

	public int getMyTankSite(int myTankX, int myTankY) {
		if (myTankX > this.getX()) {
			if (myTankY > this.getY()) {
				return 1;// ����
			} else if (Math.abs(myTankY - this.getY()) <= 35) {
				return 2;// ����
			} else {
				return 3;// ����
			}
		} else if (Math.abs(myTankX - this.getX()) <= 35) {
			if (myTankY > this.getY()) {
				return 4;// ��
			} else {
				return 5;// ��
			}
		} else {
			if (myTankY > this.getY()) {
				return 6;// ����
			} else if (Math.abs(myTankY - this.getY()) <= 35) {
				return 7;// ��
			} else {
				return 8;// ����
			}
		}
	}

	List<NoPassPoint> noPassPoints = new ArrayList<NoPassPoint>();

	// public void recoderNoPassPoint(int x, int y, Direction direction){
	// if (this.nextMove(x, y, dir).intersects(r)){
	//
	// }
	// }
	public Rectangle nextMove(int x, int y, Direction direction) {
		switch (direction) {
		case U:
			return new Rectangle(x, y -= YSPEED, WIDTH, HEIGHT);
		case D:
			return new Rectangle(x, y += YSPEED, WIDTH, HEIGHT);
		case L:
			return new Rectangle(x -= XSPEED, y, HEIGHT, WIDTH);
		case R:
			return new Rectangle(x += XSPEED, y, HEIGHT, WIDTH);
		}
		return new Rectangle();
	}

	public void moveToMyTank(int myTankX, int myTankY) {

	}

	private class NoPassPoint {
		int x, y;

		public NoPassPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

}
