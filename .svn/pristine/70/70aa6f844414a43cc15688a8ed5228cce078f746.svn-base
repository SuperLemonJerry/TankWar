package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TankClients.Explode;
import TankClients.TankClient;

import dataclass.Missile;

public class MissileDeadMsg implements Msg{
	
	int msgType = Msg.MISSILE_DEAD_MSG;
	TankClient tc = null;
	int TankId;
	int id;

	
	public MissileDeadMsg(int tankId, int id) {
		super();
		TankId = tankId;
		this.id = id;
	}

	public MissileDeadMsg(TankClient tc) {
		super();
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(TankId);
			dos.writeInt(id);
		
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
			int tankId = dis.readInt();	
			
			
			int id = dis.readInt();	
			for (Missile m : tc.missiles){
				if (m.gettankID() == tankId && m.getId() == id){
					m.setLive(false);
					tc.explodes.add(new Explode(m.getX(), m.getY(), tc));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
