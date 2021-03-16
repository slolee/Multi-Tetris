package Client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Util.*;

@SuppressWarnings("serial")
public class Signup extends JPanel {
	GUI_manager F = null;
	private boolean check_overlap = false;
	
	public Signup(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(62, 58, 57));
		gui();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(155, 155, 155));
		g.fillRoundRect(45, 80, 152, 26, 9, 9);
		g.fillRoundRect(45, 130, 152, 26, 9, 9);
		g.fillRoundRect(45, 180, 152, 26, 9, 9);
		g.fillRoundRect(45, 230, 152, 26, 9, 9);
	}
	
	public void gui() {
		JLabel jl_1 = new JLabel("ȸ �� �� ��");
		jl_1.setFont(new Font("���� ���", 1, 18));
		jl_1.setForeground(new Color(240, 240, 240));
		jl_1.setBounds(77, 15, 100, 30);
		this.add(jl_1);
		
		JLabel jl_2 = new JLabel("�̸�");
		jl_2.setFont(new Font("���� ���", 0, 12));
		jl_2.setForeground(new Color(240, 240, 240));
		jl_2.setBounds(47, 63, 30, 15);
		this.add(jl_2);
		
		JLabel jl_3 = new JLabel("ID");
		jl_3.setFont(new Font("���� ���", 0, 12));
		jl_3.setForeground(new Color(240, 240, 240));
		jl_3.setBounds(47, 113, 30, 15);
		this.add(jl_3);
		
		JLabel jl_4 = new JLabel("PW");
		jl_4.setFont(new Font("���� ���", 0, 12));
		jl_4.setForeground(new Color(240, 240, 240));
		jl_4.setBounds(47, 163, 30, 15);
		this.add(jl_4);
		
		JLabel jl_5 = new JLabel("PW_RE");
		jl_5.setFont(new Font("���� ���", 0, 12));
		jl_5.setForeground(new Color(240, 240, 240));
		jl_5.setBounds(47, 213, 50, 15);
		this.add(jl_5);
		
		JButton jb_check = new RoundButton("ID�ߺ�üũ", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		jb_check.setBorderPainted(false); 		//�׵θ��� �����
		jb_check.setContentAreaFilled(false);		//���� ĥ�ϱ����� Background�� ����ش�
		jb_check.setDefaultCapable(false);		//??
		jb_check.setFocusable(false);				//??
		jb_check.setFont(new Font("���� ���", 0, 10));
		jb_check.setForeground(new Color(210, 210, 210));
		jb_check.setBounds(125, 282, 65, 21);
		jb_check.setMargin((new InsetsUIResource(2, 2, 2, 2)));
		this.add(jb_check);
		
		JButton jb_cancel = new RoundButton("�ڷΰ���", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		jb_cancel.setBorderPainted(false); 		//�׵θ��� �����
		jb_cancel.setContentAreaFilled(false);		//���� ĥ�ϱ����� Background�� ����ش�
		jb_cancel.setDefaultCapable(false);		//??
		jb_cancel.setFocusable(false);				//??
		jb_cancel.setFont(new Font("���� ���", 0, 10));
		jb_cancel.setForeground(new Color(210, 210, 210));
		jb_cancel.setBounds(54, 282, 65, 21);
		jb_cancel.setMargin((new InsetsUIResource(2, 2, 2, 2)));
		this.add(jb_cancel);
		
		JButton jb_correct = new RoundButton("ȸ�����ԿϷ�", new Color(77, 66, 73), new Color(35, 24, 21), 7);
		jb_correct.setBorderPainted(false); 		//�׵θ��� �����
		jb_correct.setContentAreaFilled(false);		//���� ĥ�ϱ����� Background�� ����ش�
		jb_correct.setDefaultCapable(false);		//??
		jb_correct.setFocusable(false);				//??
		jb_correct.setFont(new Font("���� ���", 0, 10));
		jb_correct.setForeground(new Color(210, 210, 210));
		jb_correct.setBounds(71, 308, 101, 19);
		
		this.add(jb_correct);
		
		JTextField jt_name = new JTextField();
		JTextField jt_id = new JTextField();
		JPasswordField jt_pw = new JPasswordField();
		JPasswordField jt_pw_re = new JPasswordField();
		
		jt_name.setFont(new Font("���� ���", 0, 13));
		jt_name.requestFocusInWindow();
		jt_id.setFont(new Font("���� ���", 0, 13));
		//�ؽ�Ʈ�ʵ� �׵θ� ���ֱ�
		jt_name.setBorder(BorderFactory.createEmptyBorder());
		jt_id.setBorder(BorderFactory.createEmptyBorder());
		jt_pw.setBorder(BorderFactory.createEmptyBorder());
		jt_pw_re.setBorder(BorderFactory.createEmptyBorder());
		
		//�ؽ�Ʈ�ʵ� ���ڻ� �����ϱ�
		jt_name.setForeground(new Color(255, 255, 255));
		jt_id.setForeground(new Color(255, 255, 255));
		jt_pw.setForeground(new Color(255, 255, 255));
		jt_pw_re.setForeground(new Color(255, 255, 255));
		
		//�ؽ�Ʈ�ʵ� �����ϰ� �����
		jt_name.setOpaque(false);
		jt_id.setOpaque(false);
		jt_pw.setOpaque(false);
		jt_pw_re.setOpaque(false);
		
		jt_name.setDocument(new BoundDocument(15, jt_id));
		jt_id.setDocument(new BoundDocument(15, jt_id));
		jt_pw.setDocument(new BoundDocument(15, jt_pw));
		jt_pw_re.setDocument(new BoundDocument(15, jt_pw));
		
		jt_name.setBounds(53, 80, 145, 27);
		jt_id.setBounds(53, 130, 145, 27);
		jt_pw.setBounds(53, 180, 145, 27);
		jt_pw_re.setBounds(53, 230, 145, 27);
		
		this.add(jt_name);
		this.add(jt_id);
		this.add(jt_pw);
		this.add(jt_pw_re);
		
		jb_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jt_name.setText(null);
				jt_id.setText(null);
				jt_pw.setText(null);
				jt_pw_re.setText(null);
				
				F.change_panel("login");
				F.getContentPane().remove(1);
			}
		});
		
		jb_check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = jt_id.getText();
				
				if(id.equals("")) {
					JOptionPane.showMessageDialog(jt_pw, "���̵� �Է����ּ���!", "Error", 2);
				}else {
					String temp = "overlap " + id;
					F.pw.println(temp);
					F.pw.flush();
					
					String callback = null;
					try {
						while((callback = F.br.readLine()) != null) {
							if(callback.equals("success")) {
								check_overlap = true;
								JOptionPane.showMessageDialog(jt_pw, "��밡���� ���̵��Դϴ�!", "Correct", 1);
							}else if(callback.equals("fail")){
								JOptionPane.showMessageDialog(jt_pw, "�̹� ������� ���̵��Դϴ�!", "Error", 2);
							}
							break;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		jb_correct.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = jt_name.getText();
				String id = jt_id.getText();
				String pw = jt_pw.getText();
				String pw_re = jt_pw_re.getText();
				
				if(name.equals("") || id.equals("") || pw.equals("") || pw.equals("")) {
					JOptionPane.showMessageDialog(jt_pw, "�Էµ��� ���� ������ �ֽ��ϴ�!", "Error", 2);
				}else if(name.length() < 2 || name.length() > 4) {
					JOptionPane.showMessageDialog(jt_pw, "�̸��� ���̰� �ùٸ��� �ʽ��ϴ�!", "Error", 2);
				}else if(id.length() < 5) {
					JOptionPane.showMessageDialog(jt_pw, "���̵��� ���̰� �ʹ� ª���ϴ�!", "Error", 2);
				}else if(id.length() > 13) {
					JOptionPane.showMessageDialog(jt_pw, "���̵��� ���̰� �ʹ� ��ϴ�!", "Error", 2);
				}else if(pw.length() < 7) {
					JOptionPane.showMessageDialog(jt_pw, "��й�ȣ�� ���̰� �ʹ� ª���ϴ�!", "Error", 2);
				}else if(pw.length() > 15) {
					JOptionPane.showMessageDialog(jt_pw, "��й�ȣ�� ���̰� �ʹ� ��ϴ�!", "Error", 2);
				}else if(!pw.equals(pw_re)) {
					JOptionPane.showMessageDialog(jt_pw, "��й�ȣ�� ��й�ȣȮ���� �ٸ��ϴ�!", "Error", 2);
				}else if(!check_overlap){
					JOptionPane.showMessageDialog(jt_pw, "���̵� �ߺ�Ȯ���� ���ּ���!", "Error", 2);
				}else {
					JOptionPane.showMessageDialog(jt_pw, "ȸ������ �Ϸ�!", "Correct", 1);
					try {
						String temp = "signup " + id + " " + pw + " " + name;
						F.pw.println(temp);
						F.pw.flush();
					} catch (Exception e2) {
						e2.getStackTrace();
					}
					
					F.change_panel("login");
					F.getContentPane().remove(1);
				}
			}
		});
	}
}
