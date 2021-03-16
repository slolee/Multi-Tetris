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
		jl_id.setFont(new Font("HY�︪��B", 0, 16));
		jl_pw.setFont(new Font("HY�︪��B", 0, 16));
		
		jl_id.setBounds(40, 30, 40, 40);
		jl_id.setForeground(new Color(255, 255, 255));
		jl_pw.setBounds(37, 60, 40, 40);
		jl_pw.setForeground(new Color(255, 255, 255));
		add(jl_id);
		add(jl_pw);
		
		//---------------------------------------------------------------------------
		JTextField jt_id = new JTextField();
		JPasswordField jt_pw = new JPasswordField();
		jt_id.setFont(new Font("HY�︪��M", 0, 13));
		//�ؽ�Ʈ�ʵ� �׵θ� ���ֱ�
		jt_id.setBorder(BorderFactory.createEmptyBorder());
		jt_pw.setBorder(BorderFactory.createEmptyBorder());
		
		//�ؽ�Ʈ�ʵ� ���ڻ� �����ϱ�
		jt_id.setForeground(new Color(255, 255, 255));
		jt_pw.setForeground(new Color(255, 255, 255));
		
		//�ؽ�Ʈ�ʵ� �����ϰ� �����
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
		//��ư�� �̹��� ������
		JButton jb_login = new JButton();
		jb_login.setBorderPainted(false);
		jb_login.setContentAreaFilled(false);
		jb_login.setDefaultCapable(false);
		jb_login.setFocusPainted(false);
		
		jb_login.setMargin(new Insets(3, 3, 3, 3));
		jb_login.setMultiClickThreshhold(100);
		jb_login.setOpaque(false);
		jb_login.setToolTipText("�α����Ϸ��� Ŭ���ϼ���");
		jb_login.setIcon(new ImageIcon("C://Tetris_DB//Login_button.png"));
		jb_login.setPressedIcon(new ImageIcon("C://Tetris_DB//Login_button2.png"));
		jb_login.setBounds(235, 36, 60, 60);
		
		add(jb_login);
		
		//---------------------------------------------------------------------------
		//ȸ������ ��ư�����
		JButton jb_signup = new RoundButton("ȸ�������ϱ�", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		jb_signup.setBounds(45, 112, 72, 19);
		button_option(jb_signup);
		add(jb_signup);
		
		JButton jb_search_id = new RoundButton("���̵�ã��", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		button_option(jb_search_id);
		jb_search_id.setBounds(130, 112, 72, 19);
		add(jb_search_id);

		JButton jb_search_pw = new RoundButton("��й�ȣã��", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		button_option(jb_search_pw);
		jb_search_pw.setBounds(216, 112, 72, 19);
		add(jb_search_pw);
		
		JButton jb_exit = new RoundButton("�����ϱ�", new Color(77, 66, 73), new Color(35, 24, 21), 7);
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
					JOptionPane.showMessageDialog(jb_signup, "���̵� Ȥ�� ��й�ȣ�� �Է��ϼ���!", "���Է�����", 2);
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
								JOptionPane.showMessageDialog(jb_signup, "�������� �ʴ� ���̵��Դϴ�!", "Login Error", 2);
							}else if(callback.equals("pw_discord")) {
								JOptionPane.showMessageDialog(jb_signup, "��й�ȣ�� �߸��Է� �Ǿ����ϴ�!", "Login Error", 2);
								jt_pw.setText("");
								jt_pw.requestFocusInWindow();
							}else if(callback.equals("over")) {
								JOptionPane.showMessageDialog(jb_signup, "���� ������ �ſ� ȥ���մϴ�!", "Login Error", 2);
							}else if(callback.equals("already_login")) {
								JOptionPane.showMessageDialog(jb_signup, "�̹� �α��εǾ��ִ� ������Դϴ�!", "Login Error", 2);
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
		r.setBorderPainted(false); 		//�׵θ��� �����
		r.setContentAreaFilled(false);		//���� ĥ�ϱ����� Background�� ����ش�
		r.setDefaultCapable(false);		//??
		r.setFocusable(false);				//??
		r.setFont(new Font("���� ���", 0, 10));
		r.setForeground(new Color(210, 210, 210));
		r.setMargin(new InsetsUIResource(2, 2, 2, 2));
	}
}
