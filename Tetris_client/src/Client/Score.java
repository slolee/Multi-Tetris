package Client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

import Util.RoundButton;

@SuppressWarnings("serial")
public class Score extends JPanel{
	GUI_manager F = null;
	String my_rank = null; //�ڽ��� �����ʹ� "rank high_score" ���·� ��������Ŵ�
	String my_high_score = null;
	String[] top5_rank = new String[5]; //1������ 5�������� �����͸� "ID/high_score" ���·� ��������Ŵ�
	
	public Score(GUI_manager f) {
		this.F = f;
		this.setLayout(null);
		this.setBackground(new Color(62, 58, 57));
		
		//�����ڿ��� �̸� ������ ��ŷ��Ȳ�� �����.
		//�׸��� �װ� �ν��Ͻ������� ������ ���Ҵٰ� gui()�Լ� ����� setText����
		String temp = "rank_search";
		F.pw.println(temp);
		F.pw.flush();

		try {
			String callback = null;
			while((callback = F.br.readLine()) != null) {
				String[] t = callback.split(" ");
				if(t[0].equals("my_rank")) {
					my_rank = t[1];
					my_high_score = t[2];
				}else if(t[0].equals("top5_rank")) {
					for(int i=0; i<t.length - 1; i++) 
						top5_rank[i] = t[i + 1];
				}else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(248, 182, 45));
		g.fillRoundRect(30, 60, 185, 31, 11, 11);
		g.setColor(new Color(230, 230, 230));
		g.fillRoundRect(30, 110, 185, 31, 11, 11);
		g.fillRoundRect(30, 150, 185, 31, 11, 11);
		g.fillRoundRect(30, 190, 185, 31, 11, 11);
		g.fillRoundRect(30, 230, 185, 31, 11, 11);
		g.fillRoundRect(30, 270, 185, 31, 11, 11);
	}
	
	void gui() {
		JLabel jl_title = new JLabel("RANKING");
		jl_title.setFont(new Font("���� ���", 1, 17));
		jl_title.setForeground(new Color(240, 240, 240));
		jl_title.setBounds(85, 15, 150, 40);
		
		this.add(jl_title);
		
		JLabel jl_my_rank = new JLabel(this.my_rank + "��");
		JLabel jl_my_id = new JLabel(F.login_id);
		JLabel jl_my_high_score = new JLabel(this.my_high_score);
		jl_my_rank.setFont(new Font("���� ���", 0, 13));
		jl_my_id.setFont(new Font("���� ���", 0, 12));
		jl_my_high_score.setFont(new Font("���� ���", 0, 13));
		
		jl_my_rank.setBounds(39, 65, 50, 20);
		jl_my_id.setBounds(70, 65, 100, 20);
		jl_my_high_score.setBounds(170, 65, 50, 20);
		
		this.add(jl_my_rank);
		this.add(jl_my_id);
		this.add(jl_my_high_score);
		
		JLabel[] jl_rank = new JLabel[5];
		JLabel[] jl_id = new JLabel[5];
		JLabel[] jl_high_score = new JLabel[5];
		
		for(int i=0; i<5; i++) {
			jl_rank[i] = new JLabel((i+1) + "��");
			jl_rank[i].setFont(new Font("���� ���", 0, 13));
			jl_rank[i].setBounds(39, 115 + (i*40), 50, 20);
			jl_id[i] = new JLabel();
			jl_id[i].setFont(new Font("���� ���", 0, 12));
			jl_id[i].setBounds(70, 115 + (i*40), 100, 20);
			jl_high_score[i] = new JLabel();
			jl_high_score[i].setFont(new Font("���� ���", 0, 13));
			jl_high_score[i].setBounds(170, 115 + (i*40), 50, 20);
			
			if(top5_rank[i] != null) {
				jl_id[i].setText(top5_rank[i].split("/")[0]);
				jl_high_score[i].setText(top5_rank[i].split("/")[1]);
			}else {
				jl_id[i].setBounds(130, 115 + (i*40), 50, 20);
				jl_id[i].setText("-");
			}
			
			this.add(jl_rank[i]);
			this.add(jl_id[i]);
			this.add(jl_high_score[i]);
		}
		
		//���� ��ư�� �ۼ��ϴ°� QUIT, 
		JButton jb_quit = new RoundButton("QUIT", new Color(77, 66, 73), new Color(35, 24, 21), 4);
		jb_quit.setBorderPainted(false); 		//�׵θ��� �����
		jb_quit.setContentAreaFilled(false);		//���� ĥ�ϱ����� Background�� ����ش�
		jb_quit.setDefaultCapable(false);		//??
		jb_quit.setFocusable(false);				//??
		jb_quit.setFont(new Font("���� ���", Font.BOLD, 10));
		jb_quit.setForeground(new Color(210, 210, 210));
		jb_quit.setMargin(new InsetsUIResource(2, 2, 2, 2));
		
		jb_quit.setBounds(97, 325, 50, 18);
		this.add(jb_quit);
		
		jb_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				F.change_panel("menu");
				F.getContentPane().remove(2);
			}
		});
	}
}
