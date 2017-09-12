package TankClients;
import javax.swing.JPanel;

public class PaintThread implements Runnable{
	private JPanel jPanel = null;
	
	public PaintThread(JPanel jPanel){
		this.jPanel = jPanel;
	}
	@Override
	public void run() {
		while (true && jPanel != null){
			jPanel.repaint();
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				
			}
		}		
	}	
}
