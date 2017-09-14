package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TankClients.MyClient;
import TankClients.TankClient;
import TankServer.TankServer;

public class GameStartMsg implements Msg{
	int msgType = Msg.GAME_START_MSG;
	MyClient mc = null;
	int id;
	

	public GameStartMsg(int id) {
		super();
		this.id = id;
	}

	public GameStartMsg(MyClient mc) {
		super();
		this.mc = mc;
	}
	
	
	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.writeBoolean(true);		
	
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

			int msgId = dis.readInt();			
			boolean b = dis.readBoolean();
			
			if (!b) return 0;
			
			System.out.println(mc.getId());
			
			mc.startGame();
	
					
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return 0;
	}

}
