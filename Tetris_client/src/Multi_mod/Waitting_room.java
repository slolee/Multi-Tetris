package Multi_mod;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Client.Client;
import Client.GUI_manager;
import Util.*;

@SuppressWarnings("serial")
public class Waitting_room extends JPanel implements MouseListener, ActionListener{
	GUI_manager F = null;
	String[] login_user_id = null;
	String[][] room_list = new String[7][2];
	JPopupMenu pm = null;
	JMenuItem item1 = null;
	JMenuItem item2 = null;
	JButton[] join_button = new JButton[7];
	JLabel[][] battle_user = new JLabel[7][2];
	
	int click_x, click_y; //클릭한 좌표
	int[] y_array = new int[9];
	
	public Waitting_room(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(36, 31, 32));
		String call = null;
		//여기서 Server로부터 접속중인 유저목록
		//room정보를 읽어들여야한다.
		try {
			F.pw.println("user_list");
			F.pw.flush();
			while((call = F.br.readLine()) != null) {
				String[] temp = call.split(" ");
				if(temp[0].equals("user_list")) {
					login_user_id = new String[temp.length - 1];
					int index = 0;
					for(int i=1; i<temp.length; i++) {
						if(!temp[i].equals(F.login_id)) {
							login_user_id[index] = temp[i];
							index++;
						}
					}
					for(int i=login_user_id.length - 2; i >= 0; --i) 
						login_user_id[i + 1] = login_user_id[i];
					login_user_id[0] = F.login_id;
				}else if(temp[0].equals("room_list")) {
					int k = 1;
					for(int i=0; i<7; ++i) {
						for(int j=0; j<2; ++j) {
							room_list[i][j] = temp[k++];
						}
					}
				}else if(temp[0].equals("end"))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i<y_array.length; i++)
			y_array[i] = 37 + (30*i);
		
		gui();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == MouseEvent.BUTTON3) {
			click_x = 382 + arg0.getX();
			click_y = 8 + arg0.getY();

			if(click_x > 382 && click_x < 527) {
				for(int i=0; i<y_array.length; i++) {
					if(click_y > y_array[i] && click_y < y_array[i] + 25)
						pm.show(this, click_x, click_y);
				}
			}		
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }
	
	void gui() {
		JPanel jp_right = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(new Color(248, 202, 65));
				g.fillRoundRect(0, 29, 145, 25, 7, 7);
				g.setColor(new Color(240, 240, 240));
				for(int i=59; i<290; i+=30) 
					g.fillRoundRect(0, i, 145, 25, 7, 7);
				
				g.setColor(new Color(20, 20, 20));
				g.setFont(new Font("HY울릉도M 보통", Font.BOLD, 13));
				int j = 5;
				for(int i=1; i<=login_user_id.length; i++) {
					g.drawString(login_user_id[i - 1], 20, j + (i*30) + 10);
				}
			}
		};
		
		pm = new JPopupMenu();
		item1 = new JMenuItem("Info");
		item2 = new JMenuItem("Message");
		pm.add(item1);
		pm.add(item2);
		jp_right.add(pm); // add해도 show하기전엔 안나타난다.
		jp_right.addMouseListener(this);
		
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int click_user_index = -1;
				for(int i=0; i<y_array.length; i++) {
					if(click_y > y_array[i] && click_y < y_array[i] + 25)
						click_user_index = i;
				}
				try {
					String temp = "select_user " + login_user_id[click_user_index];
					F.pw.println(temp);
					F.pw.flush();
					String[] call = null;
					while((temp = F.br.readLine()) != null) {
						call = temp.split(" ");
						if(call[0].equals("success")) {
							User_infopage info = new User_infopage(F.getX(), F.getY(), login_user_id[click_user_index], Integer.parseInt(call[1]), 
									Integer.parseInt(call[2]), Double.parseDouble(call[3]));
							User_infopage.jb_quit.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent arg0) {
									info.dispose();
								}
							});
						}
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "사용자가 존재하지 않습니다.", "실패", 2);
				} catch (Exception e2) {
					e2.getMessage();
				}
			}
		});
		
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int click_user_index = -1;
				for(int i=0; i<y_array.length; i++) {
					if(click_y > y_array[i] && click_y < y_array[i] + 25)
						click_user_index = i;
				}
				if(click_user_index == 0) {
					JOptionPane.showMessageDialog(null, "자기자신에게 메세지를 보낼수 없습니다.", "실패", 2);
				}else if(click_user_index < 0 || click_user_index >= login_user_id.length){
					JOptionPane.showMessageDialog(null, "사용자가 존재하지 않습니다.", "실패", 2);
				}else {
					String Message = JOptionPane.showInputDialog(F, "보낼 메세지 입력", "Send Message", 1);
					if(!Message.equals("")) {
						String temp = "send_message " + login_user_id[click_user_index] + " " + Message;
						F.pw.println(temp);
						F.pw.flush();
					}
				}
			}
		});
		
		JPanel jp_left = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(new Color(240, 240, 240));
				for(int i=64; i<=292; i+=38)
					g.fillRoundRect(12, i, 352, 30, 7, 7);
				
				g.setColor(new Color(10, 10, 10));
				g.setFont(new Font("HY울릉도B", Font.BOLD, 17));
				for(int i=64; i<=292; i+=38)
					g.drawString("VS", 165, i + 22);
				
			}
		};
		
		jp_right.setBackground(new Color(62, 58, 57));
		jp_left.setBackground(new Color(62, 58, 57));
		
		jp_left.setBounds(8, 8, 374, 334);
		jp_right.setBounds(382, 8, 160, 334);
		this.add(jp_right);
		this.add(jp_left);
		
		JLabel jl_title = new JLabel("1:1 BATTLE MODE");
		jl_title.setFont(new Font("HY울릉도M", Font.BOLD, 18));
		jl_title.setForeground(new Color(240, 240, 240));
		jl_title.setBounds(100, 20, 200, 30);
		jp_left.add(jl_title);
		
		JButton jb_refresh = new JButton();
		jb_refresh.setBorderPainted(false);
		jb_refresh.setContentAreaFilled(false);
		jb_refresh.setDefaultCapable(false);
		jb_refresh.setFocusPainted(false);
		
		jb_refresh.setMargin(new Insets(3, 3, 3, 3));
		jb_refresh.setMultiClickThreshhold(100);
		jb_refresh.setOpaque(false);
		jb_refresh.setToolTipText("새로고침");
		jb_refresh.setIcon(new ImageIcon("C://Tetris_DB//Refresh_button.png"));
		jb_refresh.setPressedIcon(new ImageIcon("C://Tetris_DB//Refresh_button2.png"));
		jb_refresh.setBounds(313, 8, 45, 45);
		jp_left.add(jb_refresh);
	
		JButton jb_quit = new RoundButton("QUIT", new Color(20, 20, 180), new Color(0, 0, 160), 5);
		button_setting(jb_quit);
		jb_quit.setBounds(78, 307, 50, 20);
		jp_right.add(jb_quit);
		
		JButton jb_join = new RoundButton("JOIN", new Color(190, 75, 20), new Color(170, 55, 0), 5);
		button_setting(jb_join);
		jb_join.setBounds(22, 307, 50, 19);
		jp_right.add(jb_join);
		
		for(int i=0; i<7; ++i) {
			battle_user[i][0] = new JLabel(room_list[i][0].equals("null") ? "" : room_list[i][0]);
			battle_user[i][1] = new JLabel(room_list[i][1].equals("null") ? "" : room_list[i][1]);
			battle_user[i][0].setForeground(new Color(10, 10, 10));
			battle_user[i][1].setForeground(new Color(10, 10, 10));
			battle_user[i][0].setFont(new Font("HY울릉도M", Font.BOLD, 13));
			battle_user[i][1].setFont(new Font("HY울릉도M", Font.BOLD, 13));
			battle_user[i][0].setHorizontalAlignment(JLabel.CENTER);
			battle_user[i][1].setHorizontalAlignment(JLabel.CENTER);
			battle_user[i][0].setBounds(12, 64 + (i * 38), 153, 30);
			battle_user[i][1].setBounds(195, 64 + (i * 38), 153, 30);
			jp_left.add(battle_user[i][0]);
			jp_left.add(battle_user[i][1]);
		}
		for(int i=0; i<7; ++i) {
			join_button[i] = new RoundButton("IN",  new Color(57, 51, 52), new Color(36, 31, 32), 2);
			button_setting(join_button[i]);
			join_button[i].setBounds(338, 67 + (i * 38), 24, 24);
			jp_left.add(join_button[i]);
			join_button[i].addActionListener(this);
		}
		
		jb_refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.getContentPane().remove(2);
				F.change_panel("waitting_room");
			}
		});
		jb_join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.pw.println("create_room");
				F.pw.flush();
				
				String temp = null;
				try {
					while((temp = F.br.readLine()) != null) {
						if(temp.equals("full_room")) 
							JOptionPane.showMessageDialog(jb_join, "더이상 방을 만들수 없습니다!");
						else if(temp.equals("success")) {
							Multi_Data.status = "stop";
							Multi_Data.score = 0;
							Multi_Data.speed = 0;
							Multi_Data.check = true;
							F.getContentPane().remove(2);
							F.change_panel("battle_room");
						}
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		jb_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.change_panel("menu");
				F.getContentPane().remove(2);
				F.opponent_id = "";
				Client.oppo_id = "";
			}
		});
	}
	
	public static void button_setting(JButton par) {
		par.setBorderPainted(false); 		//테두리를 지운다
		par.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		par.setDefaultCapable(false);		//??
		par.setFocusable(false);				//??
		par.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		par.setForeground(new Color(210, 210, 210));
		par.setMargin((new InsetsUIResource(2, 2, 2, 2)));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(int i=0; i<7; ++i) {
			if(arg0.getSource().equals(join_button[i])) {
				F.pw.println("join_room " + i);
				F.pw.flush();
				
				String call = null;
				try {
					while((call = F.br.readLine()) != null) {
						String[] temp = call.split(" ");
						if(temp[0].equals("empty_room")) {
							JOptionPane.showMessageDialog(F, "비어있는 방입니다!!", "isEmpty", 2);
						}else if(temp[0].equals("already")) {
							JOptionPane.showMessageDialog(F, "방이 꽉찼습니다!!", "isFull", 2);
						}else if(temp[0].equals("success")) {
							F.opponent_id = temp[1];
							Multi_Data.status = "stop";
							Multi_Data.score = 0;
							Multi_Data.speed = 0;
							F.getContentPane().remove(2);
							F.change_panel("battle_room");
						}else if(temp[0].equals("end"))
							break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
