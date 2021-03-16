package Client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import Multi_mod.*;

public class Listen extends Thread {
	Socket socket2 = null;
	String call = null;
	BufferedReader br2 = null;
	PrintWriter pw2 = null;
	GUI_manager F = null;
	
	public Listen(Socket socket2, GUI_manager F) {
		this.F = F;
		this.socket2 = socket2;
		
		try {
			pw2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket2.getOutputStream())));
			br2 = new BufferedReader(new InputStreamReader(this.socket2.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while((call = br2.readLine()) != null) {
				String[] temp = call.split(" ");
				
				switch (temp[0]) {
				case "oppo_join":
					JOptionPane.showMessageDialog(F, temp[1] + "님이 입장하셨습니다!!\n새로고침버튼을 눌러주세요!!", "Join", 1);
					Client.oppo_id = temp[1];
					break;
				case "oppo_exit":
					JOptionPane.showMessageDialog(F, "상대방이 퇴장하였습니다!!\n새로고침버튼을 눌러주세요!!", "Exit", 1);
					Client.oppo_id = "";
					break;
				case "start":
					synchronized (this) {
						Multi_Data.status = "start";
						Battle_room.my_game_panel.requestFocus();
						Battle_room.jb_ready.setEnabled(false);
						Battle_room.jb_quit.setEnabled(false);
					}
					break;
				case "gameover":
					synchronized (this) {
						Multi_Data.status = "gameover";
					}
					break;
				case "transmit":
					synchronized (this) {
						try {
							StringTokenizer st = new StringTokenizer(br2.readLine(), " ");
							for(int i=0; i<10; ++i) 
								for(int j=0; j<21; ++j) 
									Battle_room.oppo_map[i][j] = Integer.parseInt(st.nextToken());
							Battle_room.oppo_game_panel.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case "win":
					JOptionPane.showMessageDialog(F, "승리!!!", "Win", 1);
					break;
				case "lose":
					JOptionPane.showMessageDialog(F, "패배!!!", "Lose", 1);
					break;
				case "attack":
					synchronized (this) {
						int gap = Integer.parseInt(temp[1]);
						for(int i=gap; i<21; ++i) 
							for(int j=0; j<10; ++j)
								Battle_room.Map_object.map[j][i-gap] = Battle_room.Map_object.map[j][i];
						for(int i=20; i>20-gap; --i) {
							int tmp = (int)(Math.random() * 10);
							for(int j=0; j<10; ++j) {
								Battle_room.Map_object.map[j][i] = tmp == j ? 0 : 8;
							}
						}
						Battle_room.my_game_panel.repaint();
					}
					break;
				case "chat":
					synchronized (this) {
						String chat_content = "";
						for(int i=1; i<temp.length; ++i)
							chat_content += temp[i] + " ";
						for(int i=0; i<3; ++i) 
							Battle_room.chat_content[i] = Battle_room.chat_content[i + 1];
						Battle_room.chat_content[3] = chat_content;
						Battle_room.chat_panel.repaint();
					}
					break;
				case "message":
					String message_content = "";
					for(int i=2; i<temp.length; ++i)
						message_content += temp[i] + " ";
					JOptionPane.showMessageDialog(F, temp[1] + "님의 메세지 입니다.\n" + message_content, "Message", 1);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
