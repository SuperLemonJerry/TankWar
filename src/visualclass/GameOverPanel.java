package visualclass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import TankClients.MyTankGame;
import TankClients.PaintThread;


public class GameOverPanel extends JPanel implements KeyListener{
	private MyTankGame myTankGame = null;

	public GameOverPanel(MyTankGame myTankGame) {
		this.myTankGame = myTankGame;
		
		this.setBackground(Color.BLACK);		
		this.setSize(MyTankGame.GAME_WIDTH, MyTankGame.GAME_HEIGHT);
		this.setVisible(true);
		new Thread(new PaintThread(this)).start();
	}
	
	@Override
	public void paint(Graphics g){
		Color c = g.getColor();	
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MyTankGame.GAME_WIDTH, MyTankGame.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		Font font = new Font("»ªÎÄÐÂÎº", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("Game Over", 140, 250);
		g.setColor(c);
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
	
			myTankGame.removeGameOverPanel(this);
			myTankGame.createMyStartPanel();	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
