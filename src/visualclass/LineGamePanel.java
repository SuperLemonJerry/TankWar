package visualclass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JLabel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import TankClients.MyClient;
import TankClients.MyTankGame;
import TankClients.PaintThread;
import TankClients.Tank;

public class LineGamePanel extends GamePanel {

	private JTextField textIP;
	private JTextArea textArea;
	private JTextField textPort;

	private Socket socket;
	public String clientName;
	public String[] listName;

	private JTextField textUDPport;

	private JComboBox<String> comboTankType;
	private JComboBox<String> comboUserName;

	private int id;
	private int num;
	private JTextField textPort2;

	// public static void main(String[] args) {//
	// JFrame jf = new JFrame("坦克大战客户端");
	// jf.setSize(700,500);
	//
	// jf.add(new ConnectShell(null));
	// jf.setVisible(true);
	// jf.addWindowListener(new WindowAdapter() {
	// @Override
	// public void windowClosing(WindowEvent e){
	// System.exit(0);
	// }
	// });
	//
	// jf.setLocation(300, 50);
	// }

	public LineGamePanel(MyTankGame myTankGame) {

		super(myTankGame);
		this.panelType = GameUI.LINE_PANEL;

		this.createPanel();

		new Thread(new PaintThread(this)).start();

	}

	public void launch() {
		JLabel jLabel_1 = new JLabel("用户名:");
		jLabel_1.setBounds(30, 10, 100, 30);
		this.add(jLabel_1);

		comboUserName = new JComboBox<String>(new String[] { "player1", "player2", "player3" });
		comboUserName.setSelectedItem(0);
		comboUserName.setBounds(150, 10, 200, 30);
		this.add(comboUserName);

		JLabel udpLb = new JLabel("UDP端口：");
		udpLb.setBounds(30, 50, 100, 30);
		this.add(udpLb);

		textUDPport = new JTextField("2234");
		textUDPport.setBounds(150, 50, 200, 30);
		this.add(textUDPport);

		JLabel ipLb = new JLabel("IP地址：");
		ipLb.setBounds(30, 100, 100, 30);
		this.add(ipLb);

		textIP = new JTextField("127.0.0.1");
		textIP.setBounds(150, 100, 200, 30);
		this.add(textIP);

		JLabel tcpPort = new JLabel("服务器转发端口：");// (默认7764)
		tcpPort.setBounds(30, 150, 150, 30);
		this.add(tcpPort);

		textPort = new JTextField("7764");
		textPort.setBounds(150, 150, 150, 30);
		this.add(textPort);

		JLabel tcpPort2 = new JLabel("服务器监听端口：");// (默认7764)
		tcpPort2.setBounds(350, 150, 150, 30);
		this.add(tcpPort2);

		textPort2 = new JTextField("7766");
		textPort2.setBounds(450, 150, 150, 30);
		this.add(textPort2);

		JButton start = new JButton("开始连接");
		start.setBounds(50, 200, 200, 30);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					InetAddress ip = InetAddress.getByName(textIP.getText());
					int port = Integer.parseInt(textPort.getText());

					socket = new Socket(ip, port);

					textArea.append("系统提示：已经建立与服务端的连接\n");

					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeInt(Integer.parseInt(textUDPport.getText()));
					DataInputStream dis = new DataInputStream(socket.getInputStream());

					id = dis.readInt();
					num = dis.readInt();

					textArea.append("你在服务器端的Id为:" + id + "\n");
					textArea.append("当前在服务器的玩家数量:" + num + "\n");

					DatagramSocket ds = new DatagramSocket(Integer.parseInt(textUDPport.getText()));

					String TCP_IP = textIP.getText();
					int TCP_PORT = Integer.parseInt(textPort.getText());
					int TCP_PORT2 = Integer.parseInt(textPort2.getText());

					String userName = (String) comboUserName.getSelectedItem();

					int tankType = 0;
					if (((String) comboTankType.getSelectedItem()).trim().equals("红色")) {
						tankType = Tank.RED;
					} else if (((String) comboTankType.getSelectedItem()).trim().equals("蓝色")) {
						tankType = Tank.BLUE;
					}

					boolean isMaster;

					if (num == 1) {
						isMaster = true;
					} else {
						isMaster = false;
					}

					new MyClient(myTankGame,ds, id, userName, tankType, isMaster, TCP_IP, TCP_PORT, TCP_PORT2);

					dos.close();
					dis.close();
					socket.close();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					textArea.append("服务器端口打开错误！");
					e2.printStackTrace();
				}

			}
		});

		this.add(start);

		textArea = new JTextArea();
		textArea.setBounds(30, 250, 400, 100);
		this.add(textArea);
		textArea.append("--提示信息!\n");

		JLabel tankLb = new JLabel("阵营：");
		tankLb.setBounds(30, 360, 100, 30);
		this.add(tankLb);

		comboTankType = new JComboBox<String>(new String[] { "红色", "蓝色" });
		comboTankType.setSelectedItem(0);
		comboTankType.setBounds(150, 360, 200, 30);
		this.add(comboTankType);

	}

	@Override
	public void createPanel() {

		this.setLayout(null);
		this.setSize(800, 600);
		this.launch();

		myTankGame.add(this);

		this.setVisible(true);

	}

	@Override
	public void removePanel() {
		myTankGame.remove(this);

	}

}
