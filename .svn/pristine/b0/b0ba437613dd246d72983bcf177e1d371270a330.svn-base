package visualclass;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import TankClients.MyTankGame;
import TankClients.PaintThread;


public class LineGameSetPanel extends JPanel {
	
	private MyTankGame myTankGame = null;
	
	public LineGameSetPanel(MyTankGame myTankGame){
		this.myTankGame = myTankGame;
		
//		this.setBackground(Color.BLACK);	
		this.setLayout(null);
		this.setSize(800, 600);
		this.setVisible(true);
		
		new Thread(new PaintThread(this)).start();
		
		this.launch();
	}
	
	public void launch() {
		JLabel jlabel_1 = new JLabel("按键设置<暂无>");
		jlabel_1.setBounds(30, 10, 100, 30);
		this.add(jlabel_1);
		
		JLabel jlabel_2 = new JLabel("主机设置");
		jlabel_2.setBounds(30, 210, 100, 30);
		this.add(jlabel_2);
		
		JLabel jlabel_3 = new JLabel("主机端口");
		jlabel_3.setBounds(30, 250, 100, 30);
		this.add(jlabel_3);
		JTextField port = new JTextField();
		port.setBounds(140, 250, 100, 30);
		this.add(port);	
		
		JLabel jlabel_4 = new JLabel("主机IP");
		jlabel_4.setBounds(30, 290, 100, 30);
		this.add(jlabel_4);
		JTextField ip = new JTextField();
		ip.setBounds(140, 290, 100, 30);
		this.add(ip);	
				
		JButton start = new JButton("开始游戏");
		start.setBounds(100, 460, 100, 30);
		this.add(start);
		
		this.repaint();
	}

	


	
}