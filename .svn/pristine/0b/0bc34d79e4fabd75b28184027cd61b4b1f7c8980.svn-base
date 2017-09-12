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
import TankClients.Tank;
import TankClients.TankClient;

public class AITankNewMsg implements Msg{

	Tank tank;
	public TankClient tc = null;
	int MsgType = Msg.AI_TANK_NEW_MSG;

	public AITankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	public AITankNewMsg(TankClient tc) {
		this.tc = tc;
	}

	public int send(DatagramSocket ds, String IP, int UDPport){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(MsgType);
			dos.writeInt(tank.getId());
			dos.writeInt(tank.getX());
			dos.writeInt(tank.getY());
			dos.writeInt(tank.getDir().ordinal());
			dos.writeInt(tank.getTankType());
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

	public int parse(DataInputStream dis) {
		try {
			int id = dis.readInt();	
			if (tc.getMyTank().getId() == id){
				return 0;
			}
			
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			int tankType = dis.readInt();
			
			
			boolean exist = false;
			for (int i=0; i<tc.tanks.size(); i++){
				if (tc.tanks.get(i).getId() == id){
					exist = true;
					break;
				}
			}
			
			if (!exist){
				
				Tank t = new Tank(id, x, y, tankType, tc);

				t.setDir(dir);
				tc.tanks.add(t);
				tc.gameObjects.add(t);
			}
			
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		return 0;
		
	}

}
