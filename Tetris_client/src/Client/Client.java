package Client;

import java.net.Socket;

public class Client {
	public static String oppo_id = null;
	
	public static void main(String[] args) {
		Socket socket = null;
		Socket socket2 = null;
		Runnable play = null;
		Thread temp = null;
		Listen listen = null;
		
		try {
			socket = new Socket("192.168.8.101", 14444);
			socket2 = new Socket("192.168.8.101", 14445);
			
			play = new GUI_manager(socket);
			temp = new Thread(play);
			temp.setDaemon(true);
			temp.start();
			
			listen = new Listen(socket2, (GUI_manager)play);
			listen.start();
			temp.join();
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
