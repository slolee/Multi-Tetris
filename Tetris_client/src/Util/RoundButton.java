package Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RoundButton extends JButton {
	Color click_color = null;
	Color color = null;
	int cur = 0;
	
	public RoundButton(String label, Color click_color, Color color, int cur) { 
				//JButton�� �����ڸ� �״�� ����Ѵ�
		super(label); 
		this.click_color = click_color;
		this.color = color;
		this.cur = cur;
			   
		Dimension size = getPreferredSize(); 
		size.width = size.height = Math.max(size.width, size.height); 
				    		    
		//JButton���� �����Ǵ� ��ư�� Background�� �ƹ��͵� ĥ���� �ʴ´�
		//�̷ν� �츮�� ��ư�� Background�� ���ϴ´�� ���� ĥ�� �� �ִ�.
		setContentAreaFilled(false); 
	} 
	
	public void modify_color(Color click_color, Color color) {
		this.click_color = click_color;
		this.color = color;
		
		this.repaint();
	}
		
	//JButton�� Background�� ���� ĥ�� ���̴�.
	protected void paintComponent(Graphics g) { 
		//���� ��ư�� Ŭ���ϸ� ?
		if (getModel().isArmed()) { 
			g.setColor(click_color); 
		} else { 
			g.setColor(color); 
		}
		//RoundRect�� �׷��ش�
		g.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, cur, cur);
				    
		super.paintComponent(g); 
	} 
}
