package TankClients;

import java.awt.event.*;

import javax.swing.*;

import visualclass.AloneGameSetPanel;
import visualclass.ConnectShell;
import visualclass.GameOverPanel;
import visualclass.GamePanel;
import visualclass.GameUI;
import visualclass.MapEditor;
import visualclass.MyStartPanel;
import java.awt.GridLayout;




public class MyTankGame extends JFrame implements ActionListener {
	
	public static final int GAME_WIDTH = 817;//810
	public static final int GAME_HEIGHT = 654;//600
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 654;
	
	public static final int SINGLE_GAME = 1;
	public static final int DOUBLE_GAME = 2;
	
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
	
	public GamePanel thisJpanel = null;
	
	private MyStartPanel myStartPanel = null;	
	public TankClient tc = null;
	private MapEditor mapEditor = null;


	private MyClient mc = null;
	public int myTankID = 0;
	private int tankType;
	private boolean isMaster = true;
	private String userName = null;
	private int gameType;

	
	public static void main(String[] args){
		new MyTankGame(100,null,Tank.RED,true,"player1");
	}

	public MyTankGame(int id,MyClient mc, int tankType, boolean isMaster,String userName){
		this.myTankID = id;
		this.mc = mc;
		this.tankType = tankType;
		this.isMaster = isMaster;
		this.userName = userName;
		
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
				
		this.setLocation(300, 50);
		
		this.setResizable(false);
		
		this.menu();
		
		this.createPanel(GameUI.START_PANEL);

		this.setVisible(true);
		
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
		jMenuItem_1_1.addActionListener(this);
		jMenuItem_1_1.setActionCommand("NewGame");
		jMenuItem_1_1.setMnemonic('N');	
		jMenuItem_1_3 = new JMenuItem("退出游戏");
		jMenuItem_1_3.addActionListener(this);
		jMenuItem_1_3.setActionCommand("Exit");
		jMenuItem_1_3.setMnemonic('E');
		jMenuItem_3_1 = new JMenuItem("结束画面");
		jMenuItem_3_1.addActionListener(this);
		jMenuItem_3_2 = new JMenuItem("当前状态");
		//*
		jMenuItem_3_1.setActionCommand("GameOver");
		jMenuItem_4_1 = new JMenuItem("打开地图编辑器");
		jMenuItem_4_1.addActionListener(this);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("NewGame")){				
			thisJpanel.removePanel();
			this.createTankClient(MyTankGame.SINGLE_GAME,"edit.tkm");
		}
		else if (e.getActionCommand().equals("Exit")){
			System.exit(0);
		}
		else if (e.getActionCommand().equals("GameOver")){		
			this.createPanel(GameUI.END_PANEL);
		}
		else if (e.getActionCommand().equals("MapEditor")){
			this.createPanel(GameUI.MAP_EDITOR_PANEL);
		}
	}
	
	public void lineSet(int id, MyClient mc,int tankType,boolean isMaster,String userName) {//连线配置
		
		this.myTankID = id;
		this.mc = mc;
		this.tankType = tankType;
		this.isMaster = isMaster;
		this.userName = userName;
		
	}
	
	public void createPanel(int panelType) {
		
		if (thisJpanel != null) {
			thisJpanel.removePanel();
		}
		
		switch (panelType) {
		case GameUI.START_PANEL:		
			MyStartPanel myStartPanel = new MyStartPanel(this);		
			thisJpanel = myStartPanel;
			break;

		case GameUI.SINGLE_PANEL:
			AloneGameSetPanel aloneSetPanel = new AloneGameSetPanel(this);
			thisJpanel = aloneSetPanel;			
			break;
			
		case GameUI.LINE_PANEL:
			ConnectShell cs = new ConnectShell(this);
			thisJpanel = cs;
			break;
			
		case GameUI.END_PANEL:					
			GameOverPanel gameOverPanel = new GameOverPanel(this);
			thisJpanel = gameOverPanel;					
			break;
			
		case GameUI.MAP_EDITOR_PANEL:
					
			mapEditor = new MapEditor(this);
			thisJpanel = mapEditor;
			
			break;
		default:
			break;
		}
		
		
		
	}
		

	public void createTankClient(int gameType,String mapName){
		
		if (thisJpanel != null) {
			thisJpanel.removePanel();
		}
		
		tc = new TankClient(this, myTankID, mc, tankType, isMaster, userName, gameType,mapName);	
		thisJpanel = tc;

	}
	

	public GamePanel getNowPanel() {
		return thisJpanel;
	}
	



}

