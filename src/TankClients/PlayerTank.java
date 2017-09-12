package TankClients;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayerTank extends Tank {

	public static final int PLAY_1 = 1;
	public static final int PLAY_2 = 2;

	private int lifeCount = 2;
	private boolean gameOver = false;
	private int playerType = PLAY_1;
	private static List<PlayerTank> playerTanks = new ArrayList<PlayerTank>(); 

	public PlayerTank(int id, int x, int y, int tankType, int playerType, TankClient tc) {
		super(id,x, y, tankType, tc);
		this.playerType = playerType;
		playerTanks.add(this);

	}

	public static boolean isGameOver() {
		for (PlayerTank play : playerTanks){
			if (play.gameOver == false){
				return false;
			}
		}
		return true;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		if (tc.getMc() == null) {
			if (playerType == PLAY_1)
				g.drawString("p1", getX() + 25, getY() - 10);	
			else if (playerType == PLAY_2)
				g.drawString("p2", getX() + 25, getY() - 10);	
		} else {
			if (this == tc.getMyTank())
				g.drawString(tc.userName + "", getX() + 25, getY() - 10);
			else {
				String userName = tc.getMc().getUserName();
				g.drawString(userName + "", getX() + 25, getY() - 10);
			}
				
		}
		

	}
	
	public void keyReleased(KeyEvent e) {// 接受按键状态
		int key = e.getKeyCode();

		if (playerType == PLAY_1) {
			switch (key) {
			case KeyEvent.VK_F2:
				if (!this.live && this.lifeCount > 0) {
					tc.newMyTank(playerType);
					this.live = true;
					this.life = 100;
					lifeCount--;
				}
				break;
			case KeyEvent.VK_J:
				if (!this.cloaking()) {
					fire(getPtDir());
				}
				break;
			case KeyEvent.VK_A:
				bL = false;
				break;
			case KeyEvent.VK_W:
				bU = false;
				break;
			case KeyEvent.VK_D:
				bR = false;
				break;
			case KeyEvent.VK_S:
				bD = false;
				break;
			case KeyEvent.VK_I:
				superFire();
				break;
			}
		}else if (playerType == PLAY_2){
			switch (key) {
			case KeyEvent.VK_NUMPAD8:
				if (!this.live && this.lifeCount > 0) {
					tc.newMyTank(playerType);
					this.live = true;
					this.life = 100;
					lifeCount--;
				}
				break;
			case KeyEvent.VK_NUMPAD1:
				if (!this.cloaking()) {
					fire(getPtDir());
				}
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_NUMPAD5:
				superFire();
				break;
			}
		}

		locateDirection();
	}

	public void keyPressed(KeyEvent e) {// 接受按键状态
		int key = e.getKeyCode();

		if (playerType == PLAY_1){
			switch (key) {
			case KeyEvent.VK_A:
				bL = true;
				break;
			case KeyEvent.VK_W:
				bU = true;
				break;
			case KeyEvent.VK_D:
				bR = true;
				break;
			case KeyEvent.VK_S:
				bD = true;
				break;
			}			
		}else if (playerType == PLAY_2){
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			}			
		}

		locateDirection();
	}
	
	public void beHitBy() {
		this.setLife(this.getLife() - 20);
		if (this.getLife() <= 0) {
			this.setLive(false);

			if (this.lifeCount == 0)
				this.gameOver = true;
		}
	}

}
