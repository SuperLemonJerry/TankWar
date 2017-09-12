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

public class TankMoveMsg implements Msg{

	int msgType = Msg.TANK_MOVE_MSG;
	int id;
	int x,y;
	Direction dir;
	Direction ptdir;
	private TankClient tc;
	
	public TankMoveMsg(int id,int x, int y, Direction dir, Direction ptdir) {
		this.id = id;
		this.dir = dir;
		this.ptdir = ptdir;
		this.x = x;
		this.y = y;
		
	}

	public TankMoveMsg(TankClient tc) {
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(ptdir.ordinal());
	
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
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			Direction ptdir = Direction.values()[dis.readInt()];

			for (Tank tank : tc.tanks){
				if (tank.getId() == id){
					tank.setX(x);
					tank.setY(y);
					tank.setDir(dir);
					tank.setPtDir(ptdir);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		return 0;
	}
	
}
