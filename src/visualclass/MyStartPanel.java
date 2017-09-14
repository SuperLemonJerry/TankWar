package visualclass;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import TankClients.MyTankGame;
import TankClients.PaintThread;


public class MyStartPanel extends GamePanel implements MouseListener, MouseMotionListener{
	
	private int mouseX;
	private int mouseY;
	private boolean mouseOnSingleGame = false;
	private boolean mouseOnDoubleGame = false;
	private boolean mouseOnDraftMode = false;
	
	
	public MyStartPanel(MyTankGame myTankGame){
		super(myTankGame);	
		this.panelType =  GameUI.START_PANEL;
		
		this.createPanel();
		
		new Thread(new PaintThread(this)).start();
	}
	
	@Override
	public void createPanel() {
		
		this.setBackground(Color.BLACK);		
		this.setSize(800, 600);
		
				
		myTankGame.setSize(MyTankGame.GAME_WIDTH, MyTankGame.GAME_HEIGHT);
		myTankGame.requestFocus();
		myTankGame.add(this);
		myTankGame.addMouseListener(this);
		myTankGame.addMouseMotionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void removePanel() {
		
		myTankGame.removeMouseListener(this);
		myTankGame.removeMouseMotionListener(this);
		myTankGame.remove(this);

		
	}
	
	
	@Override
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MyTankGame.GAME_WIDTH, MyTankGame.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		Font font = new Font("华文新魏", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("坦克大战", 180, 120);
		Font font_1 = new Font("华文新魏", Font.BOLD, 30);
		Font font_2 = new Font("华文新魏", Font.BOLD, 40);
		
		if (mouseOnSingleGame){
			g.setFont(font_2);
			g.drawString("单机游戏", 280, 302);
		}
		else{
			g.setFont(font_1);
			g.drawString("单机游戏", 299, 302);
		}
		
		if (mouseOnDoubleGame){
			g.setFont(font_2);
			g.drawString("联机游戏", 280, 342);
		}
		else{
			g.setFont(font_1);
			g.drawString("联机游戏", 299, 342);
		}
		
		if (mouseOnDraftMode){
			g.setFont(font_2);
			g.drawString("竞技模式", 280, 382);
		}else{
			g.setFont(font_1);
			g.drawString("竞技模式", 299, 382);
		}
		
		g.setColor(c);
		
	}
	
	private Rectangle getSingleGame(){
		return new Rectangle(264, 270, 200, 42);
	}
	
	private Rectangle getDoubleGame(){
		return new Rectangle(264, 312, 200, 42);
	}
	
	private Rectangle getDraftMode(){		//竞技模式
		return new Rectangle(264, 354, 200, 42);
	}
	
	private int contaninsSite(int x, int y){
		if (this.getSingleGame().contains(x, y)){
			return 1;
		}
		else if (this.getDoubleGame().contains(x, y)){
			return 2;
		}
		else if (this.getDraftMode().contains(x, y)){
			return 3;
		}
		return 0;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX() ;
		mouseY = e.getY() - 50;	
		
		switch(this.contaninsSite(mouseX, mouseY)){
			case 1:				
				myTankGame.createPanel(GameUI.SINGLE_PANEL);
				break;
				
			case 2:
				myTankGame.createPanel(GameUI.LINE_PANEL);

				
		
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		mouseX = e.getX() ;
		mouseY = e.getY() - 50;	
		
		switch(this.contaninsSite(mouseX, mouseY)){
		case 1:
			mouseOnSingleGame = true;
			mouseOnDoubleGame = false;
			mouseOnDraftMode = false;
			break;
		case 2:
			mouseOnSingleGame = false;
			mouseOnDoubleGame = true;
			mouseOnDraftMode = false;
			break;
		case 3:
			mouseOnSingleGame = false;
			mouseOnDoubleGame = false;
			mouseOnDraftMode = true;
			break;			
		case 0:
			mouseOnSingleGame = false;
			mouseOnDoubleGame = false;
			mouseOnDraftMode = false;
			break;			
		}		
							
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
			
	}

	public int getPanelType() {
		return panelType;
	}

	public void setPanelType(int panelType) {
		this.panelType = panelType;
	}


}