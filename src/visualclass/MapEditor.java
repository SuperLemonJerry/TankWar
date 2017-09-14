package visualclass;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import TankClients.GameObject;
import TankClients.MyTankGame;
import TankClients.PaintThread;
import TankClients.Tank;
import TankClients.TankClient;
import dataclass.Barrier;
import dataclass.Base;
import dataclass.Forest;
import dataclass.Rivers;
import dataclass.Wall;


public class MapEditor extends GamePanel implements MouseListener,
		MouseMotionListener, KeyListener {
		
	private int mouseX;
	private int mouseY;
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	private int kind;// 1森林 2墙 3河
	private int clickCount = 0;

	private List<Wall> walls = new ArrayList<Wall>();
	private List<Forest> forests = new ArrayList<Forest>();
	private List<Rivers> rivers = new ArrayList<Rivers>();
	private List<Barrier> barriers = new ArrayList<Barrier>();

	private boolean wallsEdit = false;// 编辑状态
	private boolean riversEdit = false;
	private boolean forestsEdit = false;
	private boolean eraserEdit = false;
	private boolean startEdit = false;
	private boolean endEdit = false;

	private boolean finishStart = false;
	private boolean finishEnd = false;

	private boolean setWalls = false;// 悬浮状态
	private boolean setForests = false;
	private boolean setRiver = false;
	private boolean startPoint = false;
	private boolean endPoint = false;
	private boolean eraser = false;
	private boolean save = false;
	private boolean finish = false;

	private boolean isMove = false;

	private static int ROWS = 0;// 行
	private static int COLS = 0;
	private static int blockSize;

	private int tankX = 4;
	private int tankY = 0;

	private Base base = new Base(360, 540, 1, 1, null);
	Forest f1 = new Forest(837, 25, 3, 3);// 样例
	Wall w1 = new Wall(837, 76, 3, 3);
	Rivers r1 = new Rivers(837, 127, 3, 3);

	Forest f = null;
	Wall w = null;
	Rivers r = null;
	private JTextField mapNameText;
	private JLabel tipsLab;

	public MapEditor(MyTankGame myTankGame) {
		super(myTankGame);
		this.panelType = GameUI.MAP_EDITOR_PANEL;
		
		this.createPanel();

		new Thread(new PaintThread(this)).start();

		if (Tank.HEIGHT >= Tank.WIDTH) {
			ROWS = TankClient.GAME_HEIGHT / Tank.HEIGHT;
			COLS = TankClient.GAME_WIDTH / Tank.HEIGHT;

			blockSize = Tank.HEIGHT;
		} else {
			ROWS = TankClient.GAME_HEIGHT / Tank.WIDTH;
			COLS = TankClient.GAME_WIDTH / Tank.WIDTH;

			blockSize = Tank.WIDTH;
		}

	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, TankClient.GAME_WIDTH, TankClient.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRect(TankClient.GAME_WIDTH, 0, MyTankGame.FRAME_WIDTH
				- TankClient.GAME_WIDTH, TankClient.GAME_HEIGHT);

		g.setColor(Color.red);

		for (int i = 1; i < ROWS; i++) {
			g.drawLine(0, blockSize * i, COLS * blockSize, i * blockSize);
		}
		for (int i = 1; i < COLS; i++) {
			g.drawLine(blockSize * i, 0, blockSize * i, ROWS * blockSize);
		}
		draw(g);
		
		g.setColor(Color.BLUE);
		drawTank(g);
		g.setColor(c);
	}

	private void drawTank(Graphics g) {

		g.draw3DRect(tankX, tankY, 5, 30, true);
		g.draw3DRect(tankX + 15, tankY, 5, 30, false);
		g.draw3DRect(tankX + 5, tankY + 5, 10, 20, false);
		g.fillOval(tankX + 5, tankY + 8, 10, 10);
	}

	private void draw(Graphics g) {
		Color c = g.getColor();

		Font font = new Font("华文新魏", Font.BOLD, 20);
		g.setFont(font);
		if (setForests || forestsEdit) {
			g.setColor(Color.BLACK);
			g.fillRect(879, 25, 84, 31);
			g.setColor(Color.WHITE);
			g.drawString("森林", 898, 45);
		} else {
			g.setColor(Color.RED);
			g.drawString("森林", 898, 45);
		}
		if (setWalls || wallsEdit) {
			g.setColor(Color.BLACK);
			g.fillRect(879, 76, 84, 31);
			g.setColor(Color.WHITE);
			g.drawString("墙壁", 898, 98);
		} else {
			g.setColor(Color.RED);
			g.drawString("墙壁", 898, 98);
		}
		if (setRiver || riversEdit) {
			g.setColor(Color.BLACK);
			g.fillRect(879, 127, 84, 31);
			g.setColor(Color.WHITE);
			g.drawString("河流", 898, 145);
		} else {
			g.setColor(Color.RED);
			g.drawString("河流", 898, 145);
		}
		if (startPoint || startEdit) {
			g.setColor(Color.BLACK);
			g.fillRect(817, 175, 100, 31);
			g.setColor(Color.WHITE);
			g.drawString("起始坐标", 828, 193);
		} else {
			g.setColor(Color.RED);
			g.drawString("起始坐标", 828, 193);
		}
		g.setColor(Color.red);
		g.fillOval(810, 183, 10, 10);
		if (finishStart) {
			g.setColor(Color.BLACK);
			g.drawString("已设置", 923, 193);
		} else {
			g.setColor(Color.BLACK);
			g.drawString("未设置", 923, 193);
		}
		if (endEdit || endPoint) {
			g.setColor(Color.BLACK);
			g.fillRect(817, 223, 100, 31);
			g.setColor(Color.WHITE);
			g.drawString("终点坐标", 828, 241);
		} else {
			g.setColor(Color.RED);
			g.drawString("终点坐标", 828, 241);
		}
		if (finishEnd) {
			g.setColor(Color.BLACK);
			g.drawString("已设置", 923, 241);
		} else {
			g.setColor(Color.BLACK);
			g.drawString("未设置", 923, 241);
		}
		if (finish) {
			g.setColor(Color.BLACK);
			g.fillRect(837, 350, 126, 31);
			g.setColor(Color.WHITE);
			g.drawString("生成", 878, 371);
		} else {
			g.setColor(Color.RED);
			g.drawString("生成", 878, 371);
		}
		if (eraser || eraserEdit) {
			g.setColor(Color.BLACK);
			g.fillRect(837, 400, 126, 31);
			g.setColor(Color.WHITE);
			g.drawString("橡皮擦", 868, 421);
		} else {
			g.setColor(Color.RED);
			g.drawString("橡皮擦", 868, 421);
		}
		if (save) {
			g.setColor(Color.BLACK);
			g.fillRect(837, 451, 126, 31);
			g.setColor(Color.WHITE);
			g.drawString("保存", 878, 471);
		} else {
			g.setColor(Color.RED);
			g.drawString("保存", 878, 471);
		}

		f1.draw(g);
		w1.draw(g);
		r1.draw(g);
		base.draw(g);

		if (finishStart) {
			g.setColor(Color.RED);
			g.fillOval(startX, startY, 10, 10);
		}

		for (GameObject gameObject : barriers) {
			gameObject.draw(g);
		}

		g.setColor(Color.WHITE);

		if (startEdit || endEdit) {
			int rows, cols;
			rows = (int) Math.ceil((double) (Math.abs(startY - endY)));
			cols = (int) Math.ceil((double) (Math.abs(startX - endX)));
			g.drawRect(startX, startY, cols, rows);
		}

		g.setColor(c);
	}

	private Rectangle getForestsRect() {
		return new Rectangle(879, 25, 84, 31);
	}

	private Rectangle getWallsRect() {
		return new Rectangle(879, 76, 84, 31);
	}

	private Rectangle getRiversRect() {
		return new Rectangle(879, 127, 84, 31);
	}

	private Rectangle getEraserRect() {
		return new Rectangle(837, 400, 126, 31);
	}

	private Rectangle getSaveRect() {
		return new Rectangle(837, 451, 126, 31);
	}

	private Rectangle getStartPointRect() {
		return new Rectangle(817, 175, 100, 31);
	}

	private Rectangle getEndPointRect() {
		return new Rectangle(817, 223, 100, 31);
	}

	private Rectangle getFinishRect() {
		return new Rectangle(837, 350, 126, 31);
	}

	private int contaninsSite(int x, int y) {
		if (this.getForestsRect().contains(x, y)) {
			return 1;
		} else if (this.getWallsRect().contains(x, y)) {
			return 2;
		} else if (this.getRiversRect().contains(x, y)) {
			return 3;
		} else if (this.getStartPointRect().contains(x, y)) {
			return 4;
		} else if (this.getEndPointRect().contains(x, y)) {
			return 5;
		} else if (this.getEraserRect().contains(x, y)) {
			return 6;
		} else if (this.getSaveRect().contains(x, y)) {
			return 7;
		} else if (this.getFinishRect().contains(x, y)) {
			return 8;
		}

		return 0;
	}

	private void calculate() {
		int rows, cols;
	
		int endx = endX - endX % blockSize + blockSize;
		int endy = endY - endY % blockSize + blockSize;

		int startx = startX - startX % blockSize;
		int starty = startY - startY % blockSize;

		rows = Math.abs(starty - endy) / 10;
		cols = Math.abs(startx - endx) / 10;

		this.mapFactory(startx, starty, rows, cols);
	}

	private void setStartSite(int x, int y) {
		this.startX = x;
		this.startY = y;
		this.finishStart = true;
	}

	private void setEndSite(int x, int y) {
		this.endX = x;
		this.endY = y;
		this.finishEnd = true;
	}

	public void mapFactory(int x, int y, int rows, int cols) {
		switch (kind) {
		case 1:
			f = new Forest(x, y, rows, cols);
			forests.add(f);
			barriers.add(f);
			break;
		case 2:
			w = new Wall(x, y, rows, cols);
			walls.add(w);
			barriers.add(w);
			break;
		case 3:
			r = new Rivers(x, y, rows, cols);
			rivers.add(r);
			barriers.add(r);
			break;
		case 4:
			break;
		case 6:
			break;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY() - 50;

		switch (this.contaninsSite(mouseX, mouseY)) {
		case 1:
			forestsEdit = !forestsEdit;
			kind = 1;
			wallsEdit = false;
			riversEdit = false;
			eraserEdit = false;
			break;
		case 2:
			wallsEdit = !wallsEdit;
			kind = 2;
			riversEdit = false;
			forestsEdit = false;
			eraserEdit = false;
			break;
		case 3:
			riversEdit = !riversEdit;
			kind = 3;
			wallsEdit = false;
			forestsEdit = false;
			eraserEdit = false;
			break;
		case 4:
			startEdit = !startEdit;
			endEdit = false;
			break;
		case 5:
			endEdit = !endEdit;
			startEdit = false;
			break;
		case 6:
			eraserEdit = !eraserEdit;
			kind = 6;
			wallsEdit = false;
			riversEdit = false;
			forestsEdit = false;
			break;
		case 7:
			this.save();
			break;
		case 8:
			if (finishStart && finishEnd) {
				this.calculate();
			}
			break;
		case 0:
			break;
		}
		if (this.isSetSite(mouseX, mouseY)) {
			if (startEdit) {
				setStartSite(mouseX, mouseY);
			} else if (endEdit) {
				this.setEndSite(mouseX, mouseY);
			}
		}
	}

	private boolean isSetSite(int mouseX, int mouseY) {
		if (mouseX <= MyTankGame.GAME_WIDTH - blockSize && mouseY <= TankClient.GAME_HEIGHT - blockSize
				&& mouseX >= 0 && mouseY >= 0) {
			if (!(mouseX >= 360 && mouseX < 420 && mouseY >= 540 && mouseY <= 600)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY() - 50;

		switch (this.contaninsSite(mouseX, mouseY)) {
		case 1:
			setWalls = false;
			setForests = true;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		case 2:
			setWalls = true;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		case 3:
			setWalls = false;
			setForests = false;
			setRiver = true;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		case 4:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = true;
			endPoint = false;
			finish = false;
			break;
		case 5:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = true;
			finish = false;
			break;
		case 6:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = true;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		case 7:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = true;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		case 8:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = true;
			break;
		case 0:
			setWalls = false;
			setForests = false;
			setRiver = false;
			eraser = false;
			save = false;
			startPoint = false;
			endPoint = false;
			finish = false;
			break;
		}
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
	
	private void saveToMap(String mapName) {
		
		System.out.println(mapName);
		FileOutputStream fos = null;
		File f = new File("src"+File.separator+"maps"+File.separator+mapName);
		if (!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		DataOutputStream dos = new DataOutputStream(fos);

		try {
			for (int i = 0; i < barriers.size(); i++) {
				Barrier bar = barriers.get(i);
				switch (bar.barType) {
				case Barrier.FOREST_BAR:
					dos.writeChar('F');
					dos.writeInt(bar.getX());
					dos.writeInt(bar.getY());
					dos.writeInt(bar.getRows());
					dos.writeInt(bar.getCols());
					break;
				case Barrier.RIVER_BAR:
					dos.writeChar('R');
					dos.writeInt(bar.getX());
					dos.writeInt(bar.getY());
					dos.writeInt(bar.getRows());
					dos.writeInt(bar.getCols());
					break;
				case Barrier.WALL_BAR:
					dos.writeChar('W');
					dos.writeInt(bar.getX());
					dos.writeInt(bar.getY());
					dos.writeInt(bar.getRows());
					dos.writeInt(bar.getCols());

				default:
					break;
				}
			}

			dos.writeChar('E');
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("地图保存失败!");

		}
		try {
			dos.flush();
			dos.close();
		} catch (IOException e) {
			System.out.println("file close error!");
		}
	}
	
	public void save() {
		
		JDialog jd = new JDialog(myTankGame,"",true);
		jd.setBounds(500, 180, 400, 300);
		jd.setLayout(null);
		
		JLabel jlabel_3 = new JLabel("全部地图");
		jlabel_3.setBounds(30,20, 100, 30);
		jd.add(jlabel_3);
		JComboBox<String> maps = new JComboBox<String>(this.listMaps());
		maps.setBounds(140, 20, 200, 30);
		jd.add(maps);
		
		JLabel jlabel_4 = new JLabel("新地图名字");
		jlabel_4.setBounds(30,70, 100, 30);
		jd.add(jlabel_4);
		
		mapNameText = new JTextField();
		mapNameText.setBounds(140,70, 100, 30);
		jd.add(mapNameText);
		
		JLabel jlabel_5 = new JLabel(".tkm");
		jlabel_5.setBounds(250,70, 50, 30);
		jd.add(jlabel_5);
		
		JLabel jlabel_6 = new JLabel("注意：edit.tkm为联机地图请勿删除");
		jlabel_6.setFont(new Font("", Font.BOLD, 14));
		jlabel_6.setForeground(Color.RED);
		jlabel_6.setBounds(30,120, 300, 30);
		jd.add(jlabel_6);
		
		JButton jButton = new JButton("确认保存");
		jButton.setBounds(100, 170, 150, 30);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!mapNameText.getText().equals("")) {
					saveToMap(mapNameText.getText().trim()+".tkm");
					tipsLab.setText("提示：地图保存完成！");
				}else {
					tipsLab.setText("提示：新地图输入名字不正确");
				}
			
				
			}
		});
		jd.add(jButton);
		
		tipsLab = new JLabel("提示：地图尚未保存");
		tipsLab.setFont(new Font("", Font.BOLD, 14));
		tipsLab.setForeground(Color.RED);
		tipsLab.setBounds(80,220, 300, 30);
		jd.add(tipsLab);
		
		jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		jd.setVisible(true);
		


	}
	
	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_A:
			if (isSetSite(tankX - blockSize, tankY)){
				tankX = tankX - blockSize;
				isMove = true;
			}
			
			break;
		case KeyEvent.VK_W:
			if (isSetSite(tankX, tankY - blockSize)){
				tankY = tankY - blockSize;
				isMove = true;
			}			
			break;
		case KeyEvent.VK_D:
			if (isSetSite(tankX + blockSize, tankY)){
				tankX = tankX + blockSize;
				isMove = true;
			}	
			break;
		case KeyEvent.VK_S:
			if (isSetSite(tankX, tankY + blockSize)){
				tankY = tankY + blockSize;
				isMove = true;
			}
		
			break;
		case KeyEvent.VK_J:
			drawMap();
			break;
		}

	}

	private void drawMap() {

		for (int i = 0; i < barriers.size(); i++) {
			Barrier bar = barriers.get(i);
			if (bar.getRect().intersects(
					new Rectangle(tankX - 3, tankY + 1, blockSize - 2,
							blockSize - 2))) {
				barriers.remove(bar);
			}

		}

		if (isMove) {
			clickCount--;
			isMove = false;
		}
		clickCount++;

		if (clickCount == 4) {
			clickCount = 0;
		}

		switch (clickCount) {
		case 0:
			kind = 1;
			mapFactory(tankX - 4, tankY, 3, 3);
			break;
		case 1:
			kind = 2;
			mapFactory(tankX - 4, tankY, 3, 3);
			break;
		case 2:
			kind = 3;
			mapFactory(tankX - 4, tankY, 3, 3);
		case 3:
			kind = 4;
			mapFactory(tankX - 4, tankY, 3, 3);
			break;

		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void createPanel() {
	

		this.setBackground(Color.BLACK);
		myTankGame.setSize(MyTankGame.FRAME_WIDTH, MyTankGame.FRAME_HEIGHT);
		
		
		myTankGame.addMouseListener(this);
		myTankGame.addMouseMotionListener(this);
		myTankGame.addKeyListener(this);	
		
		myTankGame.add(this);
		this.setVisible(true);
		
	}

	@Override
	public void removePanel() {
		myTankGame.removeMouseListener(this);
		myTankGame.removeMouseMotionListener(this);
		myTankGame.removeKeyListener(this);
		myTankGame.remove(this);
		
		
	}

}
