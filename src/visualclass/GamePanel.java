package visualclass;

import javax.swing.JPanel;

import TankClients.MyTankGame;

public abstract class GamePanel extends JPanel{
		
	protected int panelType;
	protected MyTankGame myTankGame;
	
	public GamePanel(MyTankGame myTankGame) {
		this.myTankGame = myTankGame;
	}
	
	public abstract void createPanel();
	
	public abstract void removePanel();
	
}
