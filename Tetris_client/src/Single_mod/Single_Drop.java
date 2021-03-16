package Single_mod;

public class Single_Drop extends Thread {
	public void gameover() {
		synchronized (this) {
			for(int j=20; j>=0; j--) {
				for(int i=0; i<10; i++) {
					if(Single.Map_object.map[i][j] != 0)
						Single.Map_object.map[i][j] = -1;
				}
				try {sleep(50);} catch (Exception e) {e.getStackTrace();}
				Single.jp_play_tetris.repaint();
			}
		}
	}
	public void starts() {
		synchronized (this) {
			if(Single.Map_object.c_block.currrent_block != 9)
				try {sleep(300 - Single_Data.level*21);} catch (Exception e) {e.getStackTrace();} //쉬었다가 !
			Single.Map_object.c_block.down_Block();
			Single.jp_play_tetris.repaint();
			Single.jp_info_tetris.repaint();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {sleep(1);} catch (Exception e) {e.getStackTrace();}

			if(Single_Data.status.equals("gameover")) { //게임이 끝나있는상태
				gameover();
			} else if(Single_Data.status.equals("start")) { //게임이 실행중인상태
				starts();
			} else if(Single_Data.status.equals("out")) { //게임창을 벗어나있는상태 (즉, 스레드를 종료시킨다)
				break;
			}
		}
	}
}
