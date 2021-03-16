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
				//JButton의 생성자를 그대로 사용한다
		super(label); 
		this.click_color = click_color;
		this.color = color;
		this.cur = cur;
			   
		Dimension size = getPreferredSize(); 
		size.width = size.height = Math.max(size.width, size.height); 
				    		    
		//JButton으로 생성되는 버튼의 Background에 아무것도 칠하지 않는다
		//이로써 우리는 버튼의 Background에 원하는대로 색을 칠할 수 있다.
		setContentAreaFilled(false); 
	} 
	
	public void modify_color(Color click_color, Color color) {
		this.click_color = click_color;
		this.color = color;
		
		this.repaint();
	}
		
	//JButton에 Background에 색을 칠할 것이다.
	protected void paintComponent(Graphics g) { 
		//만약 버튼을 클릭하면 ?
		if (getModel().isArmed()) { 
			g.setColor(click_color); 
		} else { 
			g.setColor(color); 
		}
		//RoundRect을 그려준다
		g.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, cur, cur);
				    
		super.paintComponent(g); 
	} 
}
