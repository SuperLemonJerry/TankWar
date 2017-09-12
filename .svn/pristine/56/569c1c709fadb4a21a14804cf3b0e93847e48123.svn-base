package visualclass;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import TankClients.MyTankGame;
import TankClients.PaintThread;


public class AloneGameSetPanel extends JPanel{
	
	private MyTankGame myTankGame = null;
	private JCheckBox check_p2;
	
	public AloneGameSetPanel(MyTankGame myTankGame){
		this.myTankGame = myTankGame;
		
//		this.setBackground(Color.BLACK);	
		this.setLayout(null);
		this.setSize(800, 600);
		this.setVisible(true);
		
		new Thread(new PaintThread(this)).start();
		
		this.launch();
	}
	
	public void launch() {
		JLabel jlabel_1 = new JLabel("玩家P1");
		jlabel_1.setBounds(30, 10, 100, 30);
		this.add(jlabel_1);
//		check_p1 = new JCheckBox("加入游戏");
//		check_p1.setBounds(140, 10, 100, 30);
//		check_p1.setSelected(true);
//		this.add(check_p1);
		
		JLabel jlabel_p1 = new JLabel("已加入游戏");
		jlabel_p1.setBounds(140, 10, 100, 30);
		this.add(jlabel_p1);
		
		JLabel jlabel_2 = new JLabel("玩家P2");
		jlabel_2.setBounds(30, 210, 100, 30);
		this.add(jlabel_2);
		check_p2 = new JCheckBox("加入游戏");
		check_p2.setBounds(140, 210, 100, 30);
		this.add(check_p2);		
		
		JLabel jlabel_3 = new JLabel("地图选择");
		jlabel_3.setBounds(30, 410, 100, 30);
		this.add(jlabel_3);
		JComboBox<String> maps = new JComboBox<String>();
		maps.setBounds(140, 410, 200, 30);
		maps.addActionListener(new Monitor());
		maps.setActionCommand("selectmap");
		this.add(maps);
		
		JButton start = new JButton("开始游戏");
		start.setBounds(100, 460, 100, 30);
		start.addActionListener(new Monitor());
		start.setActionCommand("startgame");
		this.add(start);
		
		this.repaint();
	}

	private class Monitor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("selectmap")) {
				
			}
			if (e.getActionCommand().equals("startgame")) {
							
				if (!check_p2.isSelected()) {
					myTankGame.removeAloneGameSetPanel();
					myTankGame.createTankClient(MyTankGame.SINGLE_GAME);
				}else {
					myTankGame.removeAloneGameSetPanel();
					myTankGame.createTankClient(MyTankGame.DOUBLE_GAME);
				}
				
				
			}

		}
		
	}



	
}