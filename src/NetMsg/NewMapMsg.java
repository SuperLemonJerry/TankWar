package NetMsg;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TankClients.TankClient;

import dataclass.Forest;
import dataclass.Rivers;
import dataclass.Wall;

public class NewMapMsg implements Msg {

	int msgType = Msg.NEW_MAP_MSG;
	TankClient tc = null;

	public NewMapMsg(TankClient tc) {
		this.tc = tc;
	}

	@Override
	public int send(DatagramSocket ds, String IP, int UDPport) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
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
			dos.writeInt(msgType);
			while ((c = dis.readChar()) != 'E') {
				dos.writeChar(c);
				dos.writeInt(dis.readInt());
				dos.writeInt(dis.readInt());
				dos.writeInt(dis.readInt());
				dos.writeInt(dis.readInt());
			}
			dos.writeChar(c);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("地图加载失败!");
		} finally {
			try {
				dis.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("file close error!");
			}
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
		char c = 'A';
		try {
			while ((c = dis.readChar()) != 'E') {
							
				switch (c) {
				case 'W':
					tc.walls.add(new Wall(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), tc));
					break;
				case 'F':
					tc.forests.add(new Forest(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), tc));
					break;
				case 'R':
					tc.rivers.add(new Rivers(dis.readInt(), dis.readInt(), dis
							.readInt(), dis.readInt(), tc));
					break;

				}		
			
			}
		} catch(EOFException e1){
			System.out.println("读到文件末尾");
		} catch (IOException e2) {
			e2.printStackTrace();
			System.out.println("地图加载失败!");
		}

		tc.barriers.addAll(tc.rivers);
		tc.barriers.addAll(tc.walls);
		tc.gameObjects.addAll(tc.barriers);

		return 0;
	}

}
