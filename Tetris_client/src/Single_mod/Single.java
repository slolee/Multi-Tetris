package Single_mod;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Client.GUI_manager;
import Util.RoundButton;

@SuppressWarnings("serial")
public class Single extends JPanel implements KeyListener, Runnable{
	GUI_manager F = null;
	static JPanel jp_play_tetris = null;
	static JPanel jp_info_tetris = null;
	static JLabel jl_score = null;
	static JLabel jl_speed = null;
	JButton jb_start = null;

	public static Map Map_object = null;
	
	public Single(GUI_manager f) {
		this.F = f;
		for(int i=0; i<7; i++) {
			ImageIcon icon = new ImageIcon("C://Tetris_DB//Figure"+ (i+1) + ".png");
			Single_Data.icon_array[i] = icon.getImage();
		}
		for(int i=0; i<3; i++) {
			Single_Data.next_block[i] = (int) (Math.random() * 7);
			if(i != 0 && Single_Data.next_block[i] == Single_Data.next_block[i - 1])
				i--;
		}
		
		Map_object = new Map();
		Map_object.c_block = new Block();
		this.setBackground(new Color(62, 58, 57));
		this.setLayout(null);

		gui();
	}
	
	public void gui() {
		//테트리스가 플레이될 패널
		jp_play_tetris = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				synchronized (this) {
					super.paintComponent(g);
					
					//테트리스게임판 배경에 선을 그려준다.
					for(int i=1; i<=20; i++) 
						g.drawLine(0, i*Single_Data.BlockSize, 187, i*Single_Data.BlockSize);
					for(int i=1; i<=10; i++) 
						g.drawLine(i*Single_Data.BlockSize, 0, i*Single_Data.BlockSize, 358);
					
					//테트리스에 map에 저장된 도형들을 그려준다.
					for(int i=0; i<10; i++) {
						for(int j=0; j<21; j++) {
							switch (Map_object.map[i][j]) {
							case -1:
								g.setColor(new Color(62, 58, 57)); //회색
								break;
							case 0:
								continue;
							case 1:
								g.setColor(new Color(230, 230, 0)); //노랑
								break;
							case 2:
								g.setColor(new Color(54, 172, 56)); //녹색
								break;
							case 3:
								g.setColor(new Color(241, 10, 18)); //적색
								break;
							case 4:
								g.setColor(new Color(146, 55, 164)); //보라
								break;
							case 5:
								g.setColor(new Color(0, 100, 247)); //파랑
								break;
							case 6:
								g.setColor(new Color(225, 115, 0)); //주황
								break;
							case 7:
								g.setColor(new Color(130, 213, 249)); //하늘색
								break;
							default:
								break;
							}
							g.fill3DRect(i*Single_Data.BlockSize, j*Single_Data.BlockSize, Single_Data.BlockSize, Single_Data.BlockSize, true);
						}
					}
					
					switch (Map_object.c_block.color) {
					case 1:
						g.setColor(new Color(230, 230, 0)); //노랑
						break;
					case 2:
						g.setColor(new Color(54, 172, 56)); //녹색
						break;
					case 3:
						g.setColor(new Color(241, 10, 18)); //적색
						break;
					case 4:
						g.setColor(new Color(146, 55, 164)); //보라
						break;
					case 5:
						g.setColor(new Color(0, 100, 247)); //파랑
						break;
					case 6:
						g.setColor(new Color(225, 115, 0)); //주황
						break;
					case 7:
						g.setColor(new Color(130, 213, 249)); //하늘색
						break;
					default:
						break;
					}
					if (Single_Data.status.equals("start") && Map_object.c_block.color != 0)
						for(int i=0; i<4; i++) {
							g.fill3DRect(Map_object.c_block.current_shape[0][i], Map_object.c_block.current_shape[1][i],
									Single_Data.BlockSize, Single_Data.BlockSize, true);
						}
				}
			}
		};
		jp_play_tetris.setLayout(null);
		jp_play_tetris.setBackground(new Color(36, 31, 32));
		jp_play_tetris.setBounds(18, 18, 171, 358);
		add(jp_play_tetris);
		
		//----------------------------------------------------------------------
		//우측에 next_block 등 정보를 출력하게될 패널
		jp_info_tetris = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(int i=0; i<3; i++) {
					int w = Single_Data.icon_array[Single_Data.next_block[i]].getWidth(null);
					int h = Single_Data.icon_array[Single_Data.next_block[i]].getHeight(null);
					if (Single_Data.next_block[i] == 6)
						g.drawImage(Single_Data.icon_array[Single_Data.next_block[i]], 21, 65 + (i*50), w, h, null);
					else if (Single_Data.next_block[i] == 0)
						g.drawImage(Single_Data.icon_array[Single_Data.next_block[i]], 34, 65 + (i*50), w, h, null);
					else
						g.drawImage(Single_Data.icon_array[Single_Data.next_block[i]], 27, 65 + (i*50), w, h, null);
				}
			}
		};
		
		jb_start = new RoundButton("START", new Color(108, 100, 99), new Color(62, 58, 57), 3);
		jb_start.setBorderPainted(false); 		//테두리를 지운다
		jb_start.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		jb_start.setDefaultCapable(false);		//??
		jb_start.setFocusable(false);				//??
		jb_start.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jb_start.setForeground(new Color(210, 210, 210));
		jb_start.setBounds(48, 190, 75, 19);
		jp_play_tetris.add(jb_start);
		
		jp_info_tetris.setLayout(null);
		jp_info_tetris.setBackground(new Color(36, 31, 32));
		jp_info_tetris.setBounds(198, 18, 111, 358);  // 188-18 = 170  / 358-18 = 340
		add(jp_info_tetris);
		
		JLabel jl_next = new JLabel("NEXT");
		jl_next.setFont(new Font("맑은 고딕", 0, 23));
		jl_next.setForeground(new Color(240, 240, 240));
		jl_next.setBounds(28, 23, 100, 20);
		
		jp_info_tetris.add(jl_next);
		
		//각종 라벨 작성하는 곳 SPEED: 1, SCORE:0
		jl_speed = new JLabel("SPEED: " + Single_Data.level);
		jl_score = new JLabel("SCORE: " + Single_Data.score);
		
		jl_speed.setFont(new Font("맑은 고딕", 0, 14));
		jl_score.setFont(new Font("맑은 고딕", 0, 14));
		jl_speed.setForeground(new Color(240, 240, 240));
		jl_score.setForeground(new Color(240, 240, 240));
		
		jl_speed.setBounds(0, 240, 111, 20);
		jl_score.setBounds(0, 258, 111, 20);
		
		jl_speed.setHorizontalAlignment(JLabel.CENTER);
		jl_score.setHorizontalAlignment(JLabel.CENTER);
		
		jp_info_tetris.add(jl_speed);
		jp_info_tetris.add(jl_score);
		
		//각종 버튼을 작성하는곳 QUIT, 
		JButton jb_quit = new RoundButton("QUIT", new Color(45, 152, 226), new Color(3, 110, 184), 1);
		jb_quit.setBorderPainted(false); 		//테두리를 지운다
		jb_quit.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		jb_quit.setDefaultCapable(false);		//??
		jb_quit.setFocusable(false);				//??
		jb_quit.setFont(new Font("맑은 고딕", Font.BOLD, 8));
		jb_quit.setForeground(new Color(210, 210, 210));
		jb_quit.setMargin((new InsetsUIResource(2, 2, 2, 2)));
		
		jb_quit.setBounds(40, 322, 33, 14);
		jp_info_tetris.add(jb_quit);
		
		jb_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.change_panel("menu");
				if(Single_Data.status.equals("gameover")) {
					String temp = "single_score " + Single_Data.score;
					F.pw.println(temp);
					F.pw.flush();
				}
				Single_Data.status = "out";
				F.getContentPane().remove(2);
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		jp_info_tetris.addKeyListener(this);
		jp_play_tetris.addKeyListener(this);
		
		jb_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Single_Data.status = "start";
				Single_Data.level = 1;
				jl_speed.setText("SPEED: " + Single_Data.level);
				jp_play_tetris.requestFocus();
				jb_start.setVisible(false);
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		synchronized (this) {
			if(Single_Data.status.equals("start")) {
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
			jp_info_tetris.repaint();
			jp_play_tetris.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {	}

	@Override
	public void keyTyped(KeyEvent arg0) {	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              