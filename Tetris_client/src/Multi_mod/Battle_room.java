package Multi_mod;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Client.*;
import Single_mod.Single_Data;
import Util.BoundDocument;
import Util.RoundButton;

@SuppressWarnings("serial")
public class Battle_room extends JPanel implements KeyListener, Runnable{
	GUI_manager F = null;
	public static JPanel my_game_panel = null;
	public static JPanel next_block_panel = null;
	public static JPanel oppo_game_panel = null;
	public static JPanel chat_panel = null;
	public static JButton jb_ready = null;
	public static JButton jb_quit = null;
	public static JButton jb_refresh = null;
	
	public static Multi_Map Map_object = null;
	public static int[][] oppo_map = new int[10][21];
	
	public static String[] chat_content = new String[4];
	JTextField jt_input = null;
	
	boolean ready_check = false;
	
	public Battle_room(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(62, 58, 57));
		
		for(int i=0; i<7; i++) {
			ImageIcon icon = new ImageIcon("C://Tetris_DB//Figure"+ (i+1) + ".png");
			Multi_Data.icon_array[i] = icon.getImage();
		}
		for(int i=0; i<3; i++) {
			Multi_Data.next_block[i] = (int) (Math.random() * 7);
			if(i != 0 && Multi_Data.next_block[i] == Multi_Data.next_block[i - 1])
				i--;
		}
		
		Map_object = new Multi_Map(F);
		Map_object.c_block = new Multi_Block(F);
		for(int i=0; i<4; ++i) 
			chat_content[i] = "";
		gui();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(240, 240, 240));
		g.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		g.drawString("VS", 321, 145);
		
		g.setColor(new Color(36, 31, 32));
		g.fillRect(195, 255, 111, 25);
		g.fillRect(360, 255, 111, 25);
	}
	
	void gui() {
		chat_panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(new Color(88, 88, 88));
				g.fillRoundRect(5, 66, 266, 20, 5, 5);
				
				g.setColor(new Color(230, 230, 230));
				for(int i=0; i<4; ++i) {
					g.drawString(chat_content[i], 10, 15 + (i * 15));
				}
			}
		};
		
		chat_panel.setBounds(195, 285, 276, 91);
		chat_panel.setBackground(new Color(36, 31, 32));
		add(chat_panel);
		
		jt_input = new JTextField();
		jt_input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jt_input.getText().equals("")) {
					F.pw.println("chat " + jt_input.getText());
					F.pw.flush();

					for(int i=0; i<3; ++i) 
						chat_content[i] = chat_content[i + 1];
					chat_content[3] = F.login_id + ": " + jt_input.getText();
					chat_panel.repaint();
					
					jt_input.setText("");
				}
			}
		});
		jt_input.setBorder(BorderFactory.createEmptyBorder());
		jt_input.setForeground(new Color(240, 240, 240));
		jt_input.setOpaque(false);
		jt_input.setFont(new Font("¸¼Àº °íµñ", 0, 11));
		jt_input.setDocument(new BoundDocument(38, jt_input));
		jt_input.setBounds(13, 66, 250, 20);
		jt_input.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					jt_input.setFocusable(true);
				}
			}
		});
		chat_panel.add(jt_input);
		
		my_game_panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				//Å×Æ®¸®½º°ÔÀÓÆÇ ¹è°æ¿¡ ¼±À» ±×·ÁÁØ´Ù.
				for(int i=1; i<=20; i++) 
					g.drawLine(0, i*Multi_Data.myBlockSize, 187, i*Multi_Data.myBlockSize);
				for(int i=1; i<=10; i++) 
					g.drawLine(i*Multi_Data.myBlockSize, 0, i*Multi_Data.myBlockSize, 358);
				
				for(int i=0; i<10; i++) {
					for(int j=0; j<21; j++) {
						switch (Map_object.map[i][j]) {
						case -1:
							g.setColor(new Color(62, 58, 57)); //È¸»ö
							break;
						case 0:
							continue;
						case 1:
							g.setColor(new Color(230, 230, 0)); //³ë¶û
							break;
						case 2:
							g.setColor(new Color(54, 172, 56)); //³ì»ö
							break;
						case 3:
							g.setColor(new Color(241, 10, 18)); //Àû»ö
							break;
						case 4:
							g.setColor(new Color(146, 55, 164)); //º¸¶ó
							break;
						case 5:
							g.setColor(new Color(0, 100, 247)); //ÆÄ¶û
							break;
						case 6:
							g.setColor(new Color(225, 115, 0)); //ÁÖÈ²
							break;
						case 7:
							g.setColor(new Color(130, 213, 249)); //ÇÏ´Ã»ö
							break;
						case 8:
							g.setColor(new Color(95, 95, 95)); //È¸»ö
							break;
						default:
							break;
						}
						g.fill3DRect(i*Multi_Data.myBlockSize, j*Multi_Data.myBlockSize, Multi_Data.myBlockSize, Multi_Data.myBlockSize, true);
					}
				}
				
				switch (Map_object.c_block.color) {
				case 1:
					g.setColor(new Color(230, 230, 0)); //³ë¶û
					break;
				case 2:
					g.setColor(new Color(54, 172, 56)); //³ì»ö
					break;
				case 3:
					g.setColor(new Color(241, 10, 18)); //Àû»ö
					break;
				case 4:
					g.setColor(new Color(146, 55, 164)); //º¸¶ó
					break;
				case 5:
					g.setColor(new Color(0, 100, 247)); //ÆÄ¶û
					break;
				case 6:
					g.setColor(new Color(225, 115, 0)); //ÁÖÈ²
					break;
				case 7:
					g.setColor(new Color(130, 213, 249)); //ÇÏ´Ã»ö
					break;
				default:
					break;
				}
				if (Multi_Data.status.equals("start") && Map_object.c_block.color != 0)
					for(int i=0; i<4; i++) {
						g.fill3DRect(Map_object.c_block.current_shape[0][i], Map_object.c_block.current_shape[1][i],
								Multi_Data.myBlockSize, Multi_Data.myBlockSize, true);
					}
			}
		};
		my_game_panel.setBackground(new Color(36, 31, 32));
		my_game_panel.setBounds(18, 18, 171, 358);
		my_game_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					jt_input.setFocusable(false);
					my_game_panel.requestFocus();
				}
			}
		});
		this.add(my_game_panel);
		
		
		next_block_panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(new Color(240, 240, 240));
				g.setFont(new Font("¸¼Àº °íµñ", 0, 23));
				g.drawString("NEXT", 29, 40);
				
				for(int i=0; i<3; i++) {
					int w = Multi_Data.icon_array[Multi_Data.next_block[i]].getWidth(null);
					int h = Multi_Data.icon_array[Multi_Data.next_block[i]].getHeight(null);
					if (Multi_Data.next_block[i] == 6) {
						g.drawImage(Multi_Data.icon_array[Multi_Data.next_block[i]], 23, 75 + (i*55), w, h, null);
					} else if (Single_Data.next_block[i] == 0) {
						g.drawImage(Multi_Data.icon_array[Multi_Data.next_block[i]], 30, 65 + (i*55), w, h, null);
					} else {
						g.drawImage(Multi_Data.icon_array[Multi_Data.next_block[i]], 40, 65 + (i*55), w, h, null);
					}
				}
			}
		};
		next_block_panel.setBackground(new Color(36, 31, 32));
		next_block_panel.setBounds(195, 18, 111, 232);
		this.add(next_block_panel);
		
		oppo_game_panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(int i=1; i<=20; i++) 
					g.drawLine(0, i*Multi_Data.oppo_BlockSize, 187, i*Multi_Data.oppo_BlockSize);
				for(int i=1; i<=10; i++) 
					g.drawLine(i*Multi_Data.oppo_BlockSize, 0, i*Multi_Data.oppo_BlockSize, 358);
				
				synchronized (this) {
					for(int i=0; i<10; i++) {
						for(int j=0; j<21; j++) {
							switch (oppo_map[i][j]) {
							case -1:
								g.setColor(new Color(62, 58, 57)); //È¸»ö
								break;
							case 0:
								continue;
							case 1:
								g.setColor(new Color(230, 230, 0)); //³ë¶û
								break;
							case 2:
								g.setColor(new Color(54, 172, 56)); //³ì»ö
								break;
							case 3:
								g.setColor(new Color(241, 10, 18)); //Àû»ö
								break;
							case 4:
								g.setColor(new Color(146, 55, 164)); //º¸¶ó
								break;
							case 5:
								g.setColor(new Color(0, 100, 247)); //ÆÄ¶û
								break;
							case 6:
								g.setColor(new Color(225, 115, 0)); //ÁÖÈ²
								break;
							case 7:
								g.setColor(new Color(130, 213, 249)); //ÇÏ´Ã»ö
								break;
							case 8:
								g.setColor(new Color(95, 95, 95)); //È¸»ö
								break;
							default:
								break;
							}
							g.fill3DRect(i*Multi_Data.oppo_BlockSize, j*Multi_Data.oppo_BlockSize, Multi_Data.oppo_BlockSize, Multi_Data.oppo_BlockSize, true);
						}
					}
				}
				
			}
		};
		oppo_game_panel.setBackground(new Color(36, 31, 32));
		oppo_game_panel.setBounds(360, 18, 111, 232);
		this.add(oppo_game_panel);
		
		JLabel my_id = new JLabel(F.login_id);
		my_id.setForeground(new Color(240, 240, 240));
		my_id.setFont(new Font("¸¼Àº °íµñ", 0, 13));
		my_id.setBounds(195, 255, 111, 25);
		my_id.setHorizontalAlignment(JLabel.CENTER);
		this.add(my_id);
		
		JLabel op_id = new JLabel(F.opponent_id);
		op_id.setForeground(new Color(240, 240, 240));
		op_id.setFont(new Font("¸¼Àº °íµñ", 0, 13));
		op_id.setBounds(360, 255, 111, 25);
		op_id.setHorizontalAlignment(JLabel.CENTER);
		this.add(op_id);
		
		jb_ready = new RoundButton("READY", new Color(235, 107, 0), new Color(205, 77, 0), 6);
		jb_refresh = new RoundButton("REFRESH", new Color(185, 0, 0), new Color(205, 0, 0), 6);
		jb_quit = new RoundButton("QUIT", new Color(20, 20, 180), new Color(0, 0, 160), 6);
		
		button_setting(jb_ready);
		button_setting(jb_refresh);
		button_setting(jb_quit);
		
		jb_ready.setBounds(255, 386, 50, 19);
		jb_refresh.setBounds(315, 386, 60, 18);
		jb_quit.setBounds(385, 386, 50, 19);
		
		this.add(jb_ready);
		this.add(jb_refresh);
		this.add(jb_quit);
		
		jb_ready.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ready_check) {
					ready_check = false;
					F.pw.println("ready_off");
					F.pw.flush();
					
					((RoundButton)jb_ready).modify_color(new Color(235, 107, 0), new Color(205, 77, 0));
				}else {
					ready_check = true;
					
					F.pw.println("ready");
					F.pw.flush();
					String tmp = null;
					try {
						while((tmp = F.br.readLine()) != null) {
							if(tmp.equals("start")) {
								Multi_Data.status = "start";
								my_game_panel.requestFocus();
								jb_ready.setEnabled(false);
								jb_quit.setEnabled(false);
							}
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					((RoundButton)jb_ready).modify_color(new Color(185, 55, 20), new Color(155, 25, 0));
				}
			}
		});
		jb_refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (this) {
					if(Client.oppo_id == null)
						Client.oppo_id = F.opponent_id;
					op_id.setText(Client.oppo_id);
				}
			}
		});
		jb_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.pw.println("exit_battle_room");
				F.pw.flush();
				
				Multi_Data.status = "out";
				F.opponent_id = "";
				for(int i=0; i<10; ++i)
					for(int j=0; j<21; ++j)
						oppo_map[i][j] = 0;
				F.change_panel("waitting_room");
				F.getContentPane().remove(2);
			}
		});
	}
	
	public static void button_setting(JButton par) {
		par.setBorderPainted(false); 		//Å×µÎ¸®¸¦ Áö¿î´Ù
		par.setContentAreaFilled(false);		//»öÀ» Ä¥ÇÏ±âÀ§ÇØ Background¸¦ ºñ¿öÁØ´Ù
		par.setDefaultCapable(false);		//??
		par.setFocusable(false);				//??
		par.setFont(new Font("¸¼Àº °íµñ", 1, 10));
		par.setForeground(new Color(210, 210, 210));
		par.setMargin((new InsetsUIResource(2, 2, 2, 2)));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		my_game_panel.addKeyListener(this);
		next_block_panel.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		synchronized (this) {
			if(Multi_Data.status.equals("start")) {
				switch(arg0.getKeyCode()) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					Map_object.c_block.move("left");
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					Map_object.c_block.move("right");
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					Map_object.c_block.spin();
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					Map_object.c_block.down_Block();
					break;
				case KeyEvent.VK_SPACE:
					Map_object.c_block.full_down();
					break;
				default:
				}
			}
			my_game_panel.repaint();
			next_block_panel.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {	}

	@Override
	public void keyTyped(KeyEvent arg0) {	}
}
