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
import TankClients.PlayerTank;
import TankClients.Tank;
import TankClients.TankClient;

public class TankNewMsg implements Msg{
	Tank tank;
	public TankClient tc = null;
	int MsgType = Msg.TANK_NEW_MSG;

	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	public TankNewMsg(TankClient tc) {
		this.tc = tc;
		this.tank = tc.getMyTank();
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
			dos.writeInt(tank.getLife());
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
			int life = dis.readInt();
			
			boolean exist = false;
			for (Tank tank : tc.tanks){
				if (tank.getId() == id){
					exist = true;
					break;
				}
			}
			
			if (!exist){
				
				if (tc.isMaster){
					NewMapMsg msg = new NewMapMsg(tc);
					tc.getMc().send(msg);
					
					for (int i=0; i<tc.tanks.size(); i++){
						Tank t = tc.tanks.get(i);
						if (!t.getClass().getName().contains("AITank")){
							TankNewMsg tmsg = new TankNewMsg(t);
							tc.getMc().send(tmsg);
						}else {
							AITankNewMsg aimsg = new AITankNewMsg(t);
							tc.getMc().send(aimsg);
						}
						
					}
				}
				
				PlayerTank t = new PlayerTank(id,x, y, tankType, PlayerTank.PLAY_1, tc);
				t.setDir(dir);
				t.setLife(life);
				tc.tanks.add(t);
				tc.gameObjects.add(t);
			}
			
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		return 0;
		
		
	}
}
