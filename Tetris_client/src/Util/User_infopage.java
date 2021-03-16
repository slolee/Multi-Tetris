package Util;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Client.*;

@SuppressWarnings("serial")
public class User_infopage extends JFrame {
	GUI_manager f = null;
	ImageIcon icon = null;
	String id = null;
	String win = "W:", lose = "L:";
	String rating = null;
	public static JButton jb_quit = null;
	
	public User_infopage(int x, int y, String id, int win, int lose, double rating) {
		this.id = id;
		
		int count = 0;
		String temp = win + "";
		while(win > 0) {
			win /= 10;
			count++;
		}
		count = 4 - count;
		for(int i=0; i<count; ++i)
			this.win += "0";
		this.win += temp;
		
		count = 0;
		temp = lose + "";
		while(lose > 0) {
			lose /= 10;
			count++;
		}
		count = 4 - count;
		for(int i=0; i<count; ++i)
			this.lose += "0";
		this.lose += temp;
		
		this.rating = Math.round(rating) + " %";
		
		this.setTitle("-- User Info --");
		this.setBounds(x + 250, y + 20, 215, 337);
		this.setLayout(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		icon = new ImageIcon("C://Tetris_DB//Person.png");
		gui();
	}
	
	void gui() {
		JPanel jp_background = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics arg0) {
				super.paintComponent(arg0);
				
				arg0.setColor(new Color(202, 202, 202));
				arg0.fillRoundRect(12, 12, 190, 288, 20, 20);
				arg0.setColor(new Color(180, 180, 180));
				arg0.fillRect(12, 189, 190, 37);
				arg0.setColor(new Color(160, 160, 160));
				arg0.fillRect(12, 226, 190, 37);
				arg0.setColor(new Color(140, 140, 140));
				arg0.fillRect(12, 263, 190, 37);
				
				arg0.drawImage(icon.getImage(), 33, 33, 150, 150, null);
				
			}
		};
		jp_background.setBounds(0, 0, 230, 350);
		jp_background.setBackground(new Color(76, 71, 72));
		
		JLabel jl_id = new JLabel(this.id);
		jl_id.setFont(new Font("HY울릉도M", 0, 17));
		jl_id.setHorizontalAlignment(JLabel.CENTER);
		jl_id.setBounds(12, 195, 190, 20);
		jp_background.add(jl_id);
		
		JLabel jl_win = new JLabel(this.win);
		JLabel jl_lose = new JLabel(this.lose);
		jl_win.setFont(new Font("HY울릉도M", 0, 17));
		jl_win.setHorizontalAlignment(JLabel.CENTER);
		jl_lose.setFont(new Font("HY울릉도M", 0, 17));
		jl_lose.setHorizontalAlignment(JLabel.CENTER);
		jl_win.setBounds(12, 232, 95, 20);
		jl_lose.setBounds(107, 232, 95, 20);
		jp_background.add(jl_win);
		jp_background.add(jl_lose);
		
		JLabel jl_rating = new JLabel(this.rating);
		jl_rating.setFont(new Font("HY울릉도M", 0, 17));
		jl_rating.setHorizontalAlignment(JLabel.CENTER);
		jl_rating.setBounds(12, 269, 190, 20);
		jp_background.add(jl_rating);
		
		jb_quit = new RoundButton("QUIT", new Color(20, 20, 180), new Color(0, 0, 160), 5);
		jb_quit.setBorderPainted(false); 		//테두리를 지운다
		jb_quit.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		jb_quit.setDefaultCapable(false);		//??
		jb_quit.setFocusable(false);				//??
		jb_quit.setFont(new Font("HY고딕", Font.BOLD, 10));
		jb_quit.setForeground(new Color(210, 210, 210));
		jb_quit.setMargin((new InsetsUIResource(2, 2, 2, 2)));
		jb_quit.setBounds(83, 310, 44, 19);
		jp_background.add(jb_quit);
		
		this.add(jp_background);
		
		this.setResizable(false);
		this.setVisible(true);
	}
}
