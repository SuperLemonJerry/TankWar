package TankServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import NetMsg.GameStartMsg;
import NetMsg.LiveMsg;
import NetMsg.MissileDeadMsg;
import NetMsg.Msg;

public class TankServer extends JPanel{


	private JTextField textPort;//服务器接收转发端口
	private JTextField textPort2;//服务器广播端口
	private JTextArea textArea;
	private JTextField countPlayer;
	
	
	private ServerSocket server;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private static int TankID = 100;
//	private List listUsers;	
	private int udpPort;//转发端口<区别：转发不解包，广播解包>
	private int serverPort;//广播反馈端口
	
	byte[] buf = new byte[10240];
	DatagramSocket ds2 = null;	

	public static void main(String[] args) {
		JFrame jf = new JFrame("坦克大战服务端");
		jf.setSize(700,500);
	
		jf.add(new TankServer());
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
				
		jf.setLocation(300, 50);
		

	}


	public TankServer(){

		this.setLayout(null);
		this.launch();
		
	}
	
	public void launch() {
		JLabel setPort1 = new JLabel("设置端口1（默认7764）");
		setPort1.setBounds(30, 10, 150, 30);
		this.add(setPort1);
		
		textPort = new JTextField("7764");
		textPort.setBounds(180, 10, 100, 30);
		this.add(textPort);
		
		JLabel setPort2 = new JLabel("设置端口2（默认7766）");
		setPort2.setBounds(300, 10, 200, 30);
		this.add(setPort2);
		
		textPort2 = new JTextField("7766");
		textPort2.setBounds(450, 10, 100, 30);
		this.add(textPort2);
		
		JLabel getIp = new JLabel("当前IP（局域网内）");
		getIp.setBounds(30, 50, 200, 30);
		this.add(getIp);
		
		JLabel myIp = new JLabel("自己查，没实现");
		myIp.setBounds(250, 50, 200, 30);
		this.add(myIp);
		
		JButton start = new JButton("启动服务端");
		start.setBounds(80, 100, 200, 30);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					udpPort = Integer.parseInt(textPort.getText());
					serverPort = Integer.parseInt(textPort2.getText());
					
					server = new ServerSocket(udpPort);
					
					new Thread(new UDPThread()).start();
					new Thread(new ServerUdpThread()).start();
					
					textArea.append("系统提示：坦克大战服务器系统已经启动\n");
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					textArea.append("服务器端口打开出错\n");
					e1.printStackTrace();
				}

				if (server != null) {
					ConnectSocket connect = new ConnectSocket();
					connect.start();
				}
			}
		});
		this.add(start);
	
		textArea = new JTextArea();
		textArea.setBounds(30, 150, 400, 100);
		this.add(textArea);
		textArea.append("--提示信息!\n");
		
		JLabel setPlay = new JLabel("设置玩家数量：");
		setPlay.setBounds(30, 270, 100, 30);
		this.add(setPlay);
		
		countPlayer = new JTextField();
		countPlayer.setBounds(150, 270, 100, 30);
		this.add(countPlayer);
		
		JLabel nowPlay = new JLabel("当前玩家数量：");
		nowPlay.setBounds(30, 310, 100, 30);
		this.add(nowPlay);
		
		JLabel nowPlayer = new JLabel("*");
		nowPlayer.setBounds(150, 310, 100, 30);
		this.add(nowPlayer);
		
		JButton startgame = new JButton("开始游戏");
		startgame.setBounds(80, 350, 200, 30);
		startgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Msg msg = new GameStartMsg(0);
				TankServer.this.send(msg);

			}
		});
		this.add(startgame);
		
	}
	

	public String getListName() {
		String str = "";
		String[] strs = null;

		for (Client client : clients) {
			strs = client.UserName.split(":");
			str = str + strs[1] + ":";
		}

		return str;
	}

	public Client getClient(String username) {
		for (Client client : clients) {
			if (username.equals(client.UserName.substring(client.UserName
					.indexOf(":") + 1))) {
				return client;
			}
		}

		return null;
	}

	public class ConnectSocket extends Thread {
		private Socket socket;

		public void run() {
			while (true) {

				try {
					socket = server.accept();
					
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					String IP = socket.getInetAddress().getHostAddress();
					int udpPort = dis.readInt();
					int id = TankID++;
					
					Client client = new Client(IP, udpPort, id);
					clients.add(client);					
					int num = clients.size();
					
					if (num == 1) 
						client.setMaster(true);
					else 
						client.setMaster(false);
					
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeInt(id);
					dos.writeInt(num);					
					
					textArea.append("玩家:  "+id+"  成功连接\n");
					socket.close();
				
				} catch (IOException e) {
					textArea.append("系统消息：客户连接失败\n");
					e.printStackTrace();
				}

			}
		}
	}

	private void send(Msg msg) {

		for (Client client : clients){
			msg.send(ds2, client.IP,client.udpPort);	
		}
		
		

	}
	private void parse(DatagramPacket dp) {
		byte[] buf = new byte[10240];
		ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0,
				dp.getLength());
					
		DataInputStream dis = new DataInputStream(bais);
		try {
			int msgType = dis.readInt();
			Msg msg = null;
			switch (msgType) {
			case Msg.LIVE_MSG:
				msg = new LiveMsg(0);
				for (Client client : clients){
					if (client.CID == msg.parse(dis)) {
						client.setLive(true);
						break;
					}
				}
				break;
				
			case Msg.GAME_START_MSG:
				msg = new GameStartMsg(0);
				;
				break;
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public void checkLive() {
//		LiveMsg msg = new LiveMsg(0);
//		
//		for (Client client : clients){
//			client.live = false;
//			try {
//				msg.send(new DatagramSocket(udpPort), client.IP,client.udpPort);
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//	}
	
	public class Client {
		private String UserName;
		
		private String IP;
		private int udpPort;
		private int CID;
		private boolean live = true;
		private boolean isMaster;

		public boolean isMaster() {
			return isMaster;
		}

		public void setMaster(boolean isMaster) {
			this.isMaster = isMaster;
		}

		public boolean isLive() {
			return live;
		}

		public void setLive(boolean live) {
			this.live = live;
		}

		public Client(String IP, int udpPort, int CID){
			this.IP = IP;
			this.udpPort = udpPort;
			this.CID = CID;
//			this.isMaster = isMaster;
		}

	}
	
	class ServerUdpThread implements Runnable{

		byte[] buf = new byte[10240];//10K
	
		@Override
		public void run() {
			try {
				ds2 = new DatagramSocket(serverPort);

			} catch (SocketException e) {
				e.printStackTrace();
			}
			
			while (ds2 != null){
				
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds2.receive(dp);
					parse(dp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	
		
	}
	
	class UDPThread implements Runnable{

		byte[] buf = new byte[10240];//10K
		DatagramSocket ds = null;	
		@Override
		public void run() {
			try {
				ds = new DatagramSocket(udpPort);

			} catch (SocketException e) {
				e.printStackTrace();
			}
			
			while (ds != null){
				
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					
					for (Client client : clients){
						dp.setSocketAddress(new InetSocketAddress(client.IP,client.udpPort));
						ds.send(dp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
