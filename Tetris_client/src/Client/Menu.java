package Client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Single_mod.Single_Data;
import Util.RoundButton;

@SuppressWarnings("serial")
public class Menu extends JPanel{
	GUI_manager F = null;
	public Menu(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(62, 58, 57));
		gui();
	}
	
	public void gui() {
		//메인메뉴의 타이틀을 생성하는 곳
		JLabel jl_title = new JLabel("GAME MENU");
		jl_title.setFont(new Font("HY울릉도B", 0, 23));
		jl_title.setBounds(97, 35, 150, 20);
		jl_title.setForeground(new Color(255, 255, 255));
		add(jl_title);
		
		//4개의 버튼을 생성하는 곳
		JButton jb_single_mode = new RoundButton("SINGLE MODE", new Color(237, 56, 77),new Color(195, 13, 35), 7);
		button_option(jb_single_mode);
		jb_single_mode.setBounds(55, 85, 225, 35);
		add(jb_single_mode);
		
		JButton jb_battle_mode = new RoundButton("1:1 BATTLE MODE", new Color(188, 49, 173), new Color(146, 7, 131), 7);
		button_option(jb_battle_mode);
		jb_battle_mode.setBounds(55, 137, 225, 35);
		add(jb_battle_mode);
		
		JButton jb_score = new RoundButton("SCORES", new Color(255, 226, 87), new Color(248, 182, 45), 7);
		button_option(jb_score);
		jb_score.setBounds(55, 190, 225, 35);
		jb_score.setForeground(new Color(150, 75, 0));
		add(jb_score);
		
		JButton jb_quit = new RoundButton("QUIT", new Color(45, 152, 226), new Color(3, 110, 184), 7);
		button_option(jb_quit);
		jb_quit.setBounds(55, 245, 225, 35);
		add(jb_quit);
		
		//버전을 표시하는 라벨생성
		JLabel jl_version = new JLabel("VER.0.3");
		jl_version.setFont(new Font("HY울릉도B", 0, 10));
		jl_version.setForeground(Color.white);
		jl_version.setBounds(258, 290, 40, 40);
		
		add(jl_version);
		
		jb_single_mode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Single_Data.status = "stop";
				Single_Data.score = 0;
				Single_Data.level = 0;
				F.change_panel("single");
			}
		});
		jb_battle_mode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.change_panel("waitting_room");
			}
		});
		
		jb_score.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				F.change_panel("score");
			}
		});
		
		jb_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F.change_panel("login");
				F.getContentPane().remove(1);
				F.pw.println("logout");
				F.pw.flush();
			}
		});
	}
	
	public void button_option(JButton r) {
		r.setBorderPainted(false); 		//테두리를 지운다
		r.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		r.setDefaultCapable(false);		//??
		r.setFocusable(false);				//??
		r.setFont(new Font("맑은 고딕", 1, 14));
		r.setForeground(new Color(240, 240, 240));
		r.setMargin(new InsetsUIResource(2, 2, 2, 2));
	}
}
