package Multi_mod;

import Client.GUI_manager;

public class Multi_Drop extends Thread {
	GUI_manager F = null;
	public Multi_Drop(GUI_manager F) {
		this.F = F;
	}
	
	public void gameover() {
		synchronized (this) {
			for(int j=20; j>=0; j--) {
				for(int i=0; i<10; i++) {
					if(Battle_room.Map_object.map[i][j] != 0)
						Battle_room.Map_object.map[i][j] = -1;
				}
				try {sleep(50);} catch (Exception e) {e.getStackTrace();}
				Battle_room.my_game_panel.repaint();
				Battle_room.jb_quit.setEnabled(true);
				if(!Multi_Data.check) {
					F.pw.println("gameover");
					F.pw.flush();
					Multi_Data.check = true;
				}
				
				try {
					F.pw.println("transmit");
					F.pw.flush();
					String value = "";
					for(int n=0; n<10; ++n) 
						for(int m=0; m<21; ++m) 
							value += Battle_room.Map_object.map[n][m] + " ";
					F.pw.println(value);
					F.pw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void starts() {
		synchronized (this) {
			if(Battle_room.Map_object.c_block.currrent_block != 9)
				try {sleep(300 - Multi_Data.speed*31);} catch (Exception e) {e.getStackTrace();} //쉬었다가 !
			Battle_room.Map_object.c_block.down_Block();
			Battle_room.my_game_panel.repaint();
			Battle_room.next_block_panel.repaint();
		}
	}
	
	@Override
	public void run() {
		boolean c = false;
		while(true) {
			try {sleep(1);} catch (Exception e) {e.getStackTrace();}

			if(Multi_Data.status.equals("gameover")) { //게임이 끝나있는상태
				if(!c) {
					gameover();
					c = true;
				}
			} else if(Multi_Data.status.equals("start")) { //게임이 실행중인상태
				starts();
			} else if(Multi_Data.status.equals("out")) { //게임창을 벗어나있는상태 (즉, 스레드를 종료시킨다)
				break;
			}
		}
	}
}
