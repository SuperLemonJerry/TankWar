package NetMsg;

import java.io.DataInputStream;
import java.net.DatagramSocket;

public interface Msg {
	public static final int TANK_NEW_MSG = 1;
	public static final int TANK_MOVE_MSG = 2;
	public static final int MISSILE_NEW_MSG = 3;
	public static final int TANK_DEAD_MSG = 4;
	public static final int MISSILE_DEAD_MSG = 5;
	public static final int NEW_MAP_MSG = 6;
	public static final int AI_TANK_NEW_MSG = 7;
	public static final int SYNCHRON_MASTER_MSG = 8;
	public static final int LIVE_MSG = 9;
	public static final int GAME_START_MSG = 10;
	
	public int send(DatagramSocket ds, String IP, int UDPport);
	public int parse(DataInputStream dis);
}