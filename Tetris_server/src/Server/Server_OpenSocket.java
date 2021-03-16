package Server;

import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;

public class Server_OpenSocket {
	static HashMap<String, PrintWriter> login_users = new HashMap<>();
	static HashMap<String, PrintWriter> login_users2 = new HashMap<>();
	static int room_count = 0;
	static String[][] battle_room = new String[7][2];
	static boolean[][] battle_ready = new boolean[7][2];
	
	public static void main(String[] args) {
		ServerSocket main_server = null;
		ServerSocket multi_server = null;
		Socket socket = null;
		Socket multi_socket = null;

		try {
			main_server = new ServerSocket(14444);
			multi_server = new ServerSocket(14445);
			
			while(true) {
				socket = main_server.accept();
				multi_socket = multi_server.accept();
				
				Server_Functions func = new Server_Functions(socket, multi_socket);
				func.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}
