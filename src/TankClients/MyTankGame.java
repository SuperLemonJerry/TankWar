package TankClients;

import java.awt.event.*;

import javax.swing.*;

import visualclass.AloneGameSetPanel;
import visualclass.ConnectShell;
import visualclass.GameOverPanel;
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
	public JPanel thisJpanel = null;
	
	private MyStartPanel myStartPanel = null;	
	public TankClient tc = null;
	private GameOverPanel gameOverPanel = null;
	private MapEditor mapEditor = null;
	private AloneGameSetPanel aloneSetPanel = null;	
	private ConnectShell cs;

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
		this.setVisible(true);
		this.setResizable(false);
		
		this.menu();
		if (mc == null){
			this.createMyStartPanel();
		}else {
			this.createTankClient(MyTankGame.SINGLE_GAME,"edit.tkm");
		}
		
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
			this.removeMyStartPanel(myStartPanel, myStartPanel);
			this.createTankClient(MyTankGame.SINGLE_GAME,"edit.tkm");
		}
		else if (e.getActionCommand().equals("Exit")){
			System.exit(0);
		}
		else if (e.getActionCommand().equals("GameOver")){
			this.removeMyStartPanel(myStartPanel, myStartPanel);
			this.createGameOverPanel();
		}
		else if (e.getActionCommand().equals("MapEditor")){
			this.removeMyStartPanel(myStartPanel, myStartPanel);
			this.createMapEditor();
		}
	}
	
	public void createMapEditor(){
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		mapEditor = new MapEditor(this);
		thisJpanel = mapEditor;

		this.addMouseListener(mapEditor);
		this.addMouseMotionListener(mapEditor);
		this.addKeyListener(mapEditor);	
		
		getContentPane().add(thisJpanel);
		thisJpanel.setVisible(true);
	}
	
	public void createMyStartPanel(){
		
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		myStartPanel = new MyStartPanel(this);		
		thisJpanel = myStartPanel;
		this.requestFocus();
		getContentPane().add(thisJpanel);
		this.addMouseListener(myStartPanel);
		this.addMouseMotionListener(myStartPanel);
	}
	
	public void removeMyStartPanel(MouseListener mListener, MouseMotionListener mMotionListener){
		this.removeMouseListener(mListener);
		this.removeMouseMotionListener(mMotionListener);
		this.remove(thisJpanel);
	}
	
	public void createTankClient(int gameType,String mapName){
		
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		
		tc = new TankClient(this, myTankID, mc, tankType, isMaster, userName, gameType);
		thisJpanel = tc;

		this.addKeyListener(tc.keyListener);
		this.requestFocus();
		getContentPane().add(thisJpanel);
		
		tc.getMyTank().setId(myTankID); 
	}
	
	public void removeTankClient(KeyListener keyListener){
		this.removeKeyListener(keyListener);
		this.remove(thisJpanel);
	}
	
	public void createGameOverPanel(){
		
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		
		gameOverPanel = new GameOverPanel(this);
		thisJpanel = gameOverPanel;
		this.requestFocus();
		getContentPane().add(thisJpanel);
		this.addKeyListener(gameOverPanel);			
	}
	
	public void removeGameOverPanel(KeyListener keyListener){
		this.removeKeyListener(keyListener);
		this.remove(thisJpanel);
	}

	public void createAloneGameSetPanel() {
		aloneSetPanel = new AloneGameSetPanel(this);
		thisJpanel = aloneSetPanel;
		getContentPane().add(thisJpanel);

	}
	
	public void removeAloneGameSetPanel(){
		this.remove(thisJpanel);
	}
	
	public void createConnectShell() {
		cs = new ConnectShell();
		thisJpanel = cs;
		getContentPane().add(thisJpanel);
		
	}
	public void removeConnectShell() {
		this.remove(thisJpanel);
	}


}

