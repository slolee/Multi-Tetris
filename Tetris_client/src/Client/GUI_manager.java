package Client;

import java.awt.CardLayout;
import java.io.*;
import java.net.*;
import javax.swing.*;

import Multi_mod.*;
import Single_mod.*;

@SuppressWarnings("serial")
public class GUI_manager extends JFrame implements Runnable {
	Socket socket = null;
	public PrintWriter pw = null;
	public BufferedReader br = null;
	public String login_id = null;
	public String opponent_id = null;
	private CardLayout cards = new CardLayout();
	
	//�̱۸�忡�� ����� ����
	boolean gameover = false;
	boolean gamestart = false;
	
	public GUI_manager(Socket socket) {
		this.socket = socket;
		
		this.setBounds(800, 400, 335, 205);
		this.getContentPane().setLayout(cards);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
//		setUndecorated(true);
//		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())));
			br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.setTitle("�α���");
		getContentPane().add("login", new Login(this));
		
		this.setVisible(true);
	}
	
	public void change_panel(String name) {
		if (name.equals("login")) {
			this.setTitle("�α���");
			this.setSize(335, 205);
		} else if (name.equals("signup")) {
			getContentPane().add("signup", new Signup(this));
			this.setTitle("ȸ������");
			this.setSize(250, 375);
		} else if (name.equals("menu")) {
			getContentPane().add("menu", new Menu(this));
			this.setTitle("���θ޴�");
			this.setSize(340, 363);
		} else if (name.equals("single")) {
			Single t = new Single(this);
			Single_Drop b = new Single_Drop();
			
			getContentPane().add("single", t);
			Thread a = new Thread(t);
			a.start();
			b.start();
			
			this.setTitle("SINGLE GAME");
			this.setLocation(800, 350);
			this.setSize(334, 422);
		} else if (name.equals("score")) {
			getContentPane().add("score", new Score(this));
			this.setTitle("��ŷ");
			this.setSize(250, 392);
		} else if(name.equals("waitting_room")) {
			getContentPane().add("waitting_room", new Waitting_room(this));
			this.setTitle("����");
			this.setSize(555, 382);
		} else if(name.equals("battle_room")) {
			Battle_room t = new Battle_room(this);
			Multi_Drop b = new Multi_Drop(this);
			
			getContentPane().add("battle_room", t);
			Thread a = new Thread(t);
			a.start();
			b.start();
			
			this.setTitle("1:1 Battle");
			this.setSize(490, 450);
		}
		
		cards.show(this.getContentPane(), name);
	}
}
