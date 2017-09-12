package TankClients;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Explode extends GameObject {
	int x, y;
	private boolean live = true;
	int [] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};

	private static Image[] images = {	
		null, null,null
	};
	
	static{

		try {
			images[1] = ImageIO.read(new File("src/images/bomb_2.gif"));
		} catch (Exception e) {
		
		}
		try {
			images[2] = ImageIO.read(new File("src/images/bomb_3.gif"));
		} catch (Exception e) {
		
		}
	}
	
	int step = 9;
	private TankClient tc;
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;	
			
	}
	
	public void draw(Graphics g){
		if (!live){ 
			return;
		}
		
		if(this.step>6)
		{	
			g.drawImage(images[0], x, y, 30, 30, tc);
		}else if(this.step>3)
		{	
			g.drawImage(images[1], x, y, 30, 30, tc);
		}else{	
			g.drawImage(images[2], x, y, 30, 30, tc);
		}

		if (step == 0){
			live = false;
			return;
		} 
		step--;	
	}
}
