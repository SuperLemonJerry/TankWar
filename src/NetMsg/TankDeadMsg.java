package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TankClients.Tank;
import TankClients.TankClient;

public class TankDeadMsg implements Msg{
	int msgType = Msg.TANK_DEAD_MSG;
	TankClient tc = null;
	int id;
	

	public TankDeadMsg(int id) {
		super();
		this.id = id;
	}

	public TankDeadMsg(TankClient tc) {
		super();
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
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
			int id = dis.readInt();	
			
			if (tc.getMyTank().getId() == id){
				return 0;
			}
	
			for (Tank tank : tc.tanks){
				if (tank.getId() == id){
					tank.setLive(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}

}
