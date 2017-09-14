package TankClients;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import NetMsg.*;
import visualclass.LineGamePanel;
import visualclass.MyStartPanel;

public class MyClient {
	private MyTankGame myTankGame = null;
	DatagramSocket ds = null;

	private String TCP_IP = null;
	private int TCP_PORT = 0;	
	private int TCP_PORT2 = 0;
	private int id;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	private String userName;
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	private int tankType;
	private boolean isMaster = false;	
	
	public boolean isMaster() {
		return isMaster;
	}


	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}


	public MyClient(MyTankGame myTankGame, DatagramSocket ds,int id,String userName,int tankTypr,boolean isMaster,String TCP_IP,int TCP_PORT,int TCP_PORT2) {
		this(null,ds,isMaster);
		
		this.myTankGame = myTankGame;
		this.id = id;
		this.userName = userName;
		this.tankType = tankTypr;
		this.isMaster = isMaster;
		this.TCP_IP = TCP_IP;
		this.TCP_PORT = TCP_PORT;
		this.TCP_PORT2 = TCP_PORT2;
		
	}
	
	
	public void startGame() {
		
		myTankGame.lineSet(id, this, tankType, isMaster, userName);		
		myTankGame.createTankClient(MyTankGame.SINGLE_GAME,"edit.tkm");
		
	}
	
	public MyClient(MyTankGame myTankGame, DatagramSocket ds, boolean isMaster) {
		this.setMyTankGame(myTankGame);
		this.ds = ds;
		this.isMaster = isMaster;
		
		new Thread(new UDPRecvThread()).start();


	}
	
	public void send(Msg msg) {
		msg.send(ds, TCP_IP, TCP_PORT );
	}

	public void sendToserver(Msg msg) {
		msg.send(ds, TCP_IP, TCP_PORT2);
	}
	
	public MyTankGame getMyTankGame() {
		return myTankGame;
	}

	public void setMyTankGame(MyTankGame myTankGame) {
		this.myTankGame = myTankGame;
	}
	
	private class UDPRecvThread implements Runnable {

		byte[] buf = new byte[10240];//10k

		@Override
		public void run() {
			while (ds != null) {
				
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				
				try {
					ds.receive(dp);
					parse(dp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		private void parse(DatagramPacket dp) {
			ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0,
					dp.getLength());
						
			DataInputStream dis = new DataInputStream(bais);
			try {
				int msgType = dis.readInt();
				Msg msg = null;
				switch (msgType) {
				case Msg.TANK_NEW_MSG:
					msg = new TankNewMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;
				case Msg.TANK_MOVE_MSG:
					msg = new TankMoveMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;

				case Msg.MISSILE_NEW_MSG:
					msg = new MissileNewMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;
				case Msg.TANK_DEAD_MSG:
					msg = new TankDeadMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;

				case Msg.MISSILE_DEAD_MSG:
					msg = new MissileDeadMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;
					
				case Msg.NEW_MAP_MSG:
					if (getMyTankGame() == null) {
						System.out.println(1 + " -----" + MyClient.this.id);
					}
					
					if (getMyTankGame().tc == null) {
						System.out.println(2);
					}
					msg = new NewMapMsg(getMyTankGame().tc);			
					msg.parse(dis);
					break;
				case Msg.AI_TANK_NEW_MSG:
					msg = new AITankNewMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;
				case Msg.SYNCHRON_MASTER_MSG:
					msg = new SynchronMasterMsg(getMyTankGame().tc);
					msg.parse(dis);
					break;	
				case Msg.GAME_START_MSG:
					msg = new GameStartMsg(MyClient.this);
					msg.parse(dis);
					break;

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	
}
