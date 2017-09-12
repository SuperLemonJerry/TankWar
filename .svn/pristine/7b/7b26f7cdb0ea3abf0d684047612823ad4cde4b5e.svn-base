package TankClients;
import java.util.Properties;

public class PropertyMgr {
	private static Properties props = new Properties();
	static {
			
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}

}
