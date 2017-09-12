package TankClients;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import NetMsg.AITankNewMsg;
import NetMsg.MissileDeadMsg;
import NetMsg.NewMapMsg;
import NetMsg.SynchronMasterMsg;
import NetMsg.TankDeadMsg;
import NetMsg.TankNewMsg;

import dataclass.Barrier;
import dataclass.Base;
import dataclass.Blood;
import dataclass.Forest;
import dataclass.Missile;
import dataclass.Rivers;
import dataclass.Wall;
import visualclass.ConnectShell;

public class TankClient extends JPanel {

	public static final int GAME_WIDTH = 810;
	public static final int GAME_HEIGHT = 600;

	private PlayerTank myTank = null;
	private PlayerTank play2 = null;
	private Image offScreenImage = null;
	public KeyListener keyListener = null;
	public MyTankGame myTankGame = null;
	private Blood blood = new Blood();
	private Base base = new Base(360, 540, 1, 1, this);
	private MyClient mc = null;


	private int myTankID;
	public List<Tank> tanks = new ArrayList<Tank>();
	public List<Missile> missiles = new ArrayList<Missile>();
	public List<Explode> explodes = new ArrayList<Explode>();
	public List<AITank> aiTanks = new ArrayList<AITank>();
	public List<GameObject> gameObjects = new ArrayList<GameObject>();
	public List<Wall> walls = new ArrayList<Wall>();
	public List<Forest> forests = new ArrayList<Forest>();
	public List<Rivers> rivers = new ArrayList<Rivers>();
	public List<Barrier> barriers = new ArrayList<Barrier>();
	private int tankType;
	public boolean isMaster;
	public String userName;
	private int gameType;
	private int enemyCount = 3;

	public TankClient(MyTankGame myTankGame, int myTankID, MyClient mc,
			int tankType, boolean isMaster, String userName, int gameType) {

		this.myTankGame = myTankGame;
		this.setMyTankID(myTankID);
		this.mc = mc;
		this.tankType = tankType;
		this.isMaster = isMaster;
		this.userName = userName;
		this.gameType = gameType;

		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.ORANGE);
		keyListener = new KeyMonitor();
		this.addKeyListener(keyListener);
		this.setVisible(true);

		new Thread(new PaintThread(this)).start();

		if (gameType == MyTankGame.SINGLE_GAME) {
			this.newMyTank(PlayerTank.PLAY_1);
		} else if (gameType == MyTankGame.DOUBLE_GAME) {
			this.newMyTank(PlayerTank.PLAY_1);
			this.newMyTank(PlayerTank.PLAY_2);
		}

		if (isMaster) {
			this.launchEnemy();
			this.loadMap();
		}

		barriers.add(base);
		gameObjects.add(base);
		
		this.repaint();
	}

	public void newMyTank(int playerType) {

		if (playerType == PlayerTank.PLAY_1) {
			if (myTank == null) {
				myTank = new PlayerTank(myTankID, 300, 520, tankType, playerType, this);
				tanks.add(myTank);
				gameObjects.add(myTank);
				
				if (isMaster && mc != null) {
					NewMapMsg msg = new NewMapMsg(this);
					mc.send(msg);
				}


			} else{
				myTank.setLive(true);
				myTank.setX(300);
				myTank.setY(520);
			}
			
			if (mc != null) {
				TankNewMsg tankNewMsg = new TankNewMsg(myTank);
				mc.send(tankNewMsg);
			}
		} else {
			if (play2 == null) {
				play2 = new PlayerTank(myTankID+1,270, 520, tankType, playerType, this);
				tanks.add(play2);
				gameObjects.add(play2);
			} else{
				play2.setLive(true);
				play2.setX(270);
				play2.setY(520);
			}
				
		}

	}

	@Override
	public void paint(Graphics g) {

		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.GRAY);
		goffScreen.fillRect(MyTankGame.GAME_WIDTH, 0,
				TankClients.MyTankGame.FRAME_WIDTH - MyTankGame.GAME_WIDTH,
				MyTankGame.GAME_HEIGHT);
		super.paint(goffScreen);
		this.draw(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		goffScreen.setColor(c);

		this.gameEvent();
	}

	public void launchEnemy() {

		int initTankCount = Integer.parseInt((PropertyMgr
				.getProperty("initTankCount")));
		for (int i = 0; i < initTankCount; i++) {
			AITank aitank = new AITank(myTankID * 10 + i,5 + 30 * (i + 1), 50, Tank.BLUE, this);
			aiTanks.add(aitank);
			tanks.add(aitank);
			gameObjects.add(aitank);

			if (mc != null) {
				AITankNewMsg msg = new AITankNewMsg(aitank);
				mc.send(msg);
			}
		}

	}

	public void gameEvent() {
		
		 if (isMaster && aiTanks.size() <= 0 && enemyCount != 0){
			 this.launchEnemy();
			 enemyCount--;
		 }

		if (!base.isLive() || PlayerTank.isGameOver()) {
			myTankGame.removeTankClient(this.keyListener);
			myTankGame.createGameOverPanel();
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			if (m.hitTank(getMyTank()) && mc != null) {
				TankDeadMsg msg = new TankDeadMsg(myTank.getId());
				mc.send(msg);
				MissileDeadMsg msg2 = new MissileDeadMsg(m.gettankID(),
						m.getId());
				mc.send(msg2);
			}
			
			if (m.hitWall(walls) && mc != null){
				MissileDeadMsg msg = new MissileDeadMsg(m.gettankID(),
						m.getId());
				mc.send(msg);
			}
			
			if (m.hitBase(base) && mc != null){
				MissileDeadMsg msg = new MissileDeadMsg(m.gettankID(),
						m.getId());
				mc.send(msg);
			
			}
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (!t.live && !(t instanceof PlayerTank)) {
				tanks.remove(t);
				gameObjects.remove(t);
			}
			t.collidesWithBarrier(barriers);
			t.collidesWithTank(aiTanks);
		}

		if (myTank.isLive()) {
			myTank.collidesWithBarrier(base);
			myTank.eat(blood);
		}

		if (gameType == MyTankGame.DOUBLE_GAME) {
			if (play2.isLive()) {
				play2.collidesWithBarrier(base);
				play2.eat(blood);
			}
		}

	}

	public void draw(Graphics goffScreen) {

		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject gob = gameObjects.get(i);
			
			if (gob == null){
				tanks.remove(gob);
				aiTanks.remove(gob);
				gameObjects.remove(gob);
				break;
			}
			
			if (gob instanceof AITank) {
				if (((AITank) gob).isLive())
					gob.draw(goffScreen);
				else{
					tanks.remove(gob);
					aiTanks.remove(gob);
					gameObjects.remove(gob);
				}
					
			}
			else if (gob instanceof PlayerTank) {
				if (((PlayerTank) gob).isLive())
					gob.draw(goffScreen);
			} else
				gob.draw(goffScreen);

		}

		goffScreen.drawString("Base life:" + base.getLife(), 10, 50);
		goffScreen.drawString("mytank life:" + getMyTank().getLife(), 10, 70);

		for (Forest forest : forests) {
			forest.draw(goffScreen);
		}

	}

	public void loadMap() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("src" + File.separator + "maps"
					+ File.separator + "edit.tkm"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		DataInputStream dis = new DataInputStream(fis);
		char c = 'A';
		try {
			while (c != 'E') {
				c = dis.readChar();
				switch (c) {
				case 'W':
					walls.add(new Wall(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), this));
					break;
				case 'F':
					forests.add(new Forest(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), this));
					break;
				case 'R':
					rivers.add(new Rivers(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), this));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("µØÍ¼¼ÓÔØÊ§°Ü!");
		} finally {
			try {
				dis.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("file close error!");
			}
		}

		barriers.addAll(rivers);
		barriers.addAll(walls);
		gameObjects.addAll(barriers);

	}

	public MyClient getMc() {
		return mc;
	}

	public void setMc(MyClient mc) {
		this.mc = mc;
	}
	
	public int getMyTankID() {
		return myTankID;
	}

	public void setMyTankID(int myTankID) {
		this.myTankID = myTankID;
	}

	public PlayerTank getMyTank() {
		return myTank;
	}

	public void setMyTank(PlayerTank myTank) {
		this.myTank = myTank;
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
			if (gameType == MyTankGame.DOUBLE_GAME) {
				play2.keyPressed(e);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
			if (gameType == MyTankGame.DOUBLE_GAME) {
				play2.keyReleased(e);
			}
		}
	}

}
