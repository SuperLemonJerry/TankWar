package visualclass;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

import TankClients.MyTankGame;
import TankClients.PaintThread;
import dataclass.Forest;
import dataclass.Rivers;
import dataclass.Wall;


public class SinglePanel extends GamePanel{
	

	private JCheckBox check_p2;
	private JComboBox<String> mapsCombox;
	
	
	public SinglePanel(MyTankGame myTankGame){
		super(myTankGame);
		this.panelType = GameUI.SINGLE_PANEL;
		
		this.createPanel();
		
		new Thread(new PaintThread(this)).start();
		

	}
	
	public void launch() {
		JLabel jlabel_1 = new JLabel("玩家P1");
		jlabel_1.setBounds(30, 10, 100, 30);
		this.add(jlabel_1);
		
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
		mapsCombox = new JComboBox<String>(this.listMaps());
		mapsCombox.setBounds(140, 410, 200, 30);
		mapsCombox.addActionListener(new Monitor());
		this.add(mapsCombox);
		
		JButton start = new JButton("开始游戏");
		start.setBounds(100, 460, 100, 30);
		start.addActionListener(new Monitor());
		start.setActionCommand("startgame");
		this.add(start);
		

	}

	private Vector<String> listMaps() {
		
		File f = new File("src" + File.separator + "maps");
		
		String[] ss = f.list();
		
		Vector<String> v = new Vector<String>();
		
		for (String s : ss) {
			
			if (s.matches(".*(\\.tkm)$")) {
				v.add(s);
			}
		}
			
		return v;
	}
	
	private class Monitor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String mapName = (String)mapsCombox.getSelectedItem();

			if (e.getActionCommand().equals("startgame")) {
				
				if (!check_p2.isSelected()) {
					myTankGame.getNowPanel().removePanel();
					myTankGame.createTankClient(MyTankGame.SINGLE_GAME,mapName);
				}else {
					myTankGame.getNowPanel().removePanel();
					myTankGame.createTankClient(MyTankGame.DOUBLE_GAME,mapName);
				}
				
				
			}

		}
		
	}

	@Override
	public void createPanel() {
		
		
		this.setLayout(null);
		this.setSize(800, 600);
	
		this.launch();
		
		this.setVisible(true);
		
		myTankGame.add(this);	
		
	}

	@Override
	public void removePanel() {
		myTankGame.remove(this);
		
	}



	
}