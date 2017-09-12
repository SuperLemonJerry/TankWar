package visualclass;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import TankClients.MyTankGame;


public class TankTest extends JFrame{
	private JMenuBar jMenuBar = null;
	private JMenu jMenu_1 = null;
	private JMenu jMenu_2 = null;
	private JMenu jMenu_3 = null;
	private JMenu jMenu_4 = null;
	private JMenuItem jMenuItem_1_1 = null;
	private JMenuItem jMenuItem_1_3 = null;
	private JMenuItem jMenuItem_3_1 = null;
	private JMenuItem jMenuItem_3_2 = null;
	private JMenuItem jMenuItem_4_1 = null;
	
	public static void main(String[] args) {
		new TankTest();
	}
	public TankTest(){

		this.setTitle("TankWarTest");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
				
		this.setSize(MyTankGame.GAME_WIDTH, MyTankGame.GAME_HEIGHT);
		
		this.setLocation(300, 50);
		this.setVisible(true);
		this.setResizable(false);
		
		this.menu();
		
		this.add(new AloneGameSetPanel(null));
		
	}	
	
	public void menu(){
		jMenuBar = new JMenuBar();
		
		jMenu_1 = new JMenu("游戏(G)");
		jMenu_1.setMnemonic('G');
		jMenu_2 = new JMenu("设置(S)");
		jMenu_2.setMnemonic('S');
		jMenu_3 = new JMenu("测试工具");
		jMenu_4 = new JMenu("地图编辑器");
		
		jMenuItem_1_1 = new JMenuItem("开始新游戏(N)");

		jMenuItem_1_3 = new JMenuItem("退出游戏");

		jMenuItem_3_1 = new JMenuItem("结束画面");

		jMenuItem_3_2 = new JMenuItem("当前状态");
		//*
		jMenuItem_3_1.setActionCommand("GameOver");
		jMenuItem_4_1 = new JMenuItem("打开地图编辑器");

		jMenuItem_4_1.setActionCommand("MapEditor");

		jMenu_1.add(jMenuItem_1_1);
		jMenu_1.add(jMenuItem_1_3);
		jMenu_3.add(jMenuItem_3_1);
		jMenu_3.add(jMenuItem_3_2);
		jMenu_4.add(jMenuItem_4_1);

		jMenuBar.add(jMenu_1);
		jMenuBar.add(jMenu_3);
		jMenuBar.add(jMenu_4);
		
		this.setJMenuBar(jMenuBar);
	}
	
}
