package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Util.*;

@SuppressWarnings("serial")
public class Login extends JPanel{
	GUI_manager F = null;
	public Login(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(62, 58, 57));
		gui();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(155, 155, 155));
		g.fillRoundRect(69, 37, 152, 26, 12, 12);
		g.fillRoundRect(69, 67, 152, 26, 12, 12);
	}

	public void gui()	{
		JLabel jl_id = new JLabel("ID");
		JLabel jl_pw = new JLabel("PW");
		jl_id.setFont(new Font("HY울릉도B", 0, 16));
		jl_pw.setFont(new Font("HY울릉도B", 0, 16));
		
		jl_id.setBounds(40, 30, 40, 40);
		jl_id.setForeground(new Color(255, 255, 255));
		jl_pw.setBounds(37, 60, 40, 40);
		jl_pw.setForeground(new Color(255, 255, 255));
		add(jl_id);
		add(jl_pw);
		
		//---------------------------------------------------------------------------
		JTextField jt_id = new JTextField();
		JPasswordField jt_pw = new JPasswordField();
		jt_id.setFont(new Font("HY울릉도M", 0, 13));
		//텍스트필드 테두리 없애기
		jt_id.setBorder(BorderFactory.createEmptyBorder());
		jt_pw.setBorder(BorderFactory.createEmptyBorder());
		
		//텍스트필드 글자색 변경하기
		jt_id.setForeground(new Color(255, 255, 255));
		jt_pw.setForeground(new Color(255, 255, 255));
		
		//텍스트필드 투명하게 만들기
		jt_id.setOpaque(false);
		jt_id.requestFocusInWindow();
		jt_pw.setOpaque(false);
		
		jt_id.setDocument(new BoundDocument(15, jt_id));
		jt_pw.setDocument(new BoundDocument(15, jt_pw));
		jt_id.setBounds(77, 37, 145, 27);
		jt_pw.setBounds(77, 67, 145, 27);
		
		add(jt_id);
		add(jt_pw);

		//---------------------------------------------------------------------------
		//버튼에 이미지 입히기
		JButton jb_login = new JButton();
		jb_login.setBorderPainted(false);
		jb_login.setContentAreaFilled(false);
		jb_login.setDefaultCapable(false);
		jb_login.setFocusPainted(false);
		
		jb_login.setMargin(new Insets(3, 3, 3, 3));
		jb_login.setMultiClickThreshhold(100);
		jb_login.setOpaque(false);
		jb_login.setToolTipText("로그인하려면 클릭하세요");
		jb_login.setIcon(new ImageIcon("C://Tetris_DB//Login_button.png"));
		jb_login.setPressedIcon(new ImageIcon("C://Tetris_DB//Login_button2.png"));
		jb_login.setBounds(235, 36, 60, 60);
		
		add(jb_login);
		
		//---------------------------------------------------------------------------
		//회원가입 버튼만들기
		JButton jb_signup = new RoundButton("회원가입하기", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		jb_signup.setBounds(45, 112, 72, 19);
		button_option(jb_signup);
		add(jb_signup);
		
		JButton jb_search_id = new RoundButton("아이디찾기", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		button_option(jb_search_id);
		jb_search_id.setBounds(130, 112, 72, 19);
		add(jb_search_id);

		JButton jb_search_pw = new RoundButton("비밀번호찾기", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		button_option(jb_search_pw);
		jb_search_pw.setBounds(216, 112, 72, 19);
		add(jb_search_pw);
		
		JButton jb_exit = new RoundButton("종료하기", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		button_option(jb_exit);
		jb_exit.setBounds(45, 135, 243, 19);
		add(jb_exit);
		
		//Event
		jb_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String login_id = jt_id.getText().trim();
				@SuppressWarnings("deprecation")
				String login_pw = jt_pw.getText().trim();
				
				if(login_id.equals("") || login_pw.equals("")) {
					JOptionPane.showMessageDialog(jb_signup, "아이디 혹은 비밀번호를 입력하세요!", "미입력정보", 2);
				}else {
					String temp = "login " + login_id + " " + login_pw;
					F.pw.println(temp);
					F.pw.flush();
					
					try {
						String callback = null;
						while((callback = F.br.readLine()) != null) {
							
							if(callback.equals("success")) {
								jt_id.setText(null);
								jt_pw.setText(null);
								F.login_id = login_id;
								F.change_panel("menu");
								break;
							}else if(callback.equals("id_discord")){
								JOptionPane.showMessageDialog(jb_signup, "존재하지 않는 아이디입니다!", "Login Error", 2);
							}else if(callback.equals("pw_discord")) {
								JOptionPane.showMessageDialog(jb_signup, "비밀번호가 잘못입력 되었습니다!", "Login Error", 2);
								jt_pw.setText("");
								jt_pw.requestFocusInWindow();
							}else if(callback.equals("over")) {
								JOptionPane.showMessageDialog(jb_signup, "현재 서버가 매우 혼잡합니다!", "Login Error", 2);
							}else if(callback.equals("already_login")) {
								JOptionPane.showMessageDialog(jb_signup, "이미 로그인되어있는 사용자입니다!", "Login Error", 2);
							}
							break;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		jb_signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jt_id.setText("");
				jt_pw.setText("");
				F.change_panel("signup");
			}
		});
		
		jb_search_pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		jb_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
	}
	
	public void button_option(JButton r) {
		r.setBorderPainted(false); 		//테두리를 지운다
		r.setContentAreaFilled(false);		//색을 칠하기위해 Background를 비워준다
		r.setDefaultCapable(false);		//??
		r.setFocusable(false);				//??
		r.setFont(new Font("맑은 고딕", 0, 10));
		r.setForeground(new Color(210, 210, 210));
		r.setMargin(new InsetsUIResource(2, 2, 2, 2));
	}
}
