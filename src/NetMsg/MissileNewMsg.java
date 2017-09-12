package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TankClients.Direction;
import TankClients.TankClient;

import dataclass.Missile;

public class MissileNewMsg implements Msg{
	int msgType = Msg.MISSILE_NEW_MSG;
	TankClient tc = null;
	Missile m = null;
	

	public MissileNewMsg(Missile m) {
		this.m = m;
	}
	
	public MissileNewMsg(TankClient tc) {
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(m.gettankID());
			dos.writeInt(m.getId());
			dos.writeInt(m.getX());
			dos.writeInt(m.getY());
			dos.writeInt(m.getDir().ordinal());
			dos.writeInt(m.getTankType());
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, UDPport));
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
		
			int fatherTankID = dis.readInt();		
			if (fatherTankID == tc.getMyTank().getId()){
				return 0;
			}
			int id = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			int tankType = dis.readInt();
			
			Missile m = new Missile(fatherTankID, x, y, tankType, dir, tc);
			m.setId(id);
			tc.missiles.add(m);
			tc.gameObjects.add(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}

}
