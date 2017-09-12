package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import dataclass.Barrier;
import dataclass.Forest;
import dataclass.Rivers;
import dataclass.Wall;

import TankClients.AITank;
import TankClients.Direction;
import TankClients.PlayerTank;
import TankClients.Tank;
import TankClients.TankClient;

public class SynchronMasterMsg implements Msg {

	int msgType = Msg.SYNCHRON_MASTER_MSG;
	List<Tank> tanks = null;

	private TankClient tc;

	public SynchronMasterMsg(List<Tank> tanks) {
		this.tanks = tanks;

	}

	public SynchronMasterMsg(TankClient tc) {
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);

			for (int i = 0; i < tanks.size(); i++) {
				Tank tank = tanks.get(i);
				int id = tank.getId();
				int x = tank.getX();
				int y = tank.getY();
				Direction dir = tank.getDir();
				Direction ptdir = tank.getPtDir();
				int life = tank.getLife();
				dos.writeChar('T');
				dos.writeInt(id);
				dos.writeInt(x);
				dos.writeInt(y);
				dos.writeInt(dir.ordinal());
				dos.writeInt(ptdir.ordinal());
				dos.writeInt(life);
			}

			dos.writeChar('E');
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(IP, UDPport));
			ds.send(dp);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int parse(DataInputStream dis) {
		try {
			char c = 'A';

			while ((c = dis.readChar()) != 'E'){
				switch (c) {
				case 'T':
					int id = dis.readInt();
					int x = dis.readInt();
					int y = dis.readInt();
					Direction dir = Direction.values()[dis.readInt()];
					Direction ptdir = Direction.values()[dis.readInt()];
					int life = dis.readInt();
					
					boolean exist = false;
					for (Tank tank : tc.tanks) {
						if (tank.getId() == id) {
							exist = true;
							tank.setX(x);
							tank.setY(y);
							tank.setDir(dir);
							tank.setPtDir(ptdir);
							tank.setLife(life);
							break;
						}
					}
					
					if (!exist){
						Tank t = new Tank(id,x, y, Tank.BLUE, tc);
						t.setDir(dir);
						t.setLife(life);
						tc.tanks.add(t);
						tc.gameObjects.add(t);
						exist = false;
					}
					break;

				default:
					break;
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
