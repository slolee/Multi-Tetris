package Multi_mod;

import Client.GUI_manager;
import Single_mod.Single_Data;

public class Multi_Map {
	//0, 11은 테두리다, 21도 테두리다
		//내용물로 0이면 비어있다, 1~7까지는 각색별정보
		public int[][] map = new int[10][21];
		Multi_Block c_block = null; //맵에서 움직이고있는 현재블럭
		GUI_manager F = null;
		
		public Multi_Map(GUI_manager F) {
			this.F = F;
		}
		
		public void burst_line(boolean[] burst_check, int line_count) {
			for(int j=0; j<21; ++j) {
				if(burst_check[j]) {
					for(int i=0; i<10; i++) {
						this.map[i][j] = 0;
					}
					for(int i=j-1; i>=0; --i) {
						for(int k=0; k<10; ++k) {
							this.map[k][i + 1] = this.map[k][i];
						}
					}
				}
			}
			c_block.color = 0; //current_shape와 터뜨릴라인과의 딜레이를 지우기위해서.
			//how? current_shape는 color가 0이 아닐경우에만 출력한다. 
			
			try {Thread.sleep(Multi_Data.speed * 10);} catch (Exception e) {e.getStackTrace();} //쉬었다가 !
			
			if(line_count == 3) {
				F.pw.println("attack " + 2);
				F.pw.flush();
			}else if(line_count == 4) {
				F.pw.println("attack " + 4);
				F.pw.flush();
			}
			
			if(line_count <= 2)
				Multi_Data.score += (100 * line_count);
			else if(line_count == 3)
				Multi_Data.score += 400;
			else
				Multi_Data.score += 1000;
			
			if(Multi_Data.score < 0)
				Multi_Data.speed = 1;
			else if(Multi_Data.score < 700)
				Multi_Data.speed = 2;
			else if(Multi_Data.score < 1500)
				Multi_Data.speed = 3;
			else if(Multi_Data.score < 2500)
				Multi_Data.speed = 4;
			else if(Multi_Data.score < 3000)
				Multi_Data.speed = 5;
			else if(Multi_Data.score < 5000)
				Multi_Data.speed = 6;
		}
		
		public void check_complete() { //완성된 줄이있는지 검사
			int line_count = 0; //완성된 줄의 총갯수
			boolean[] burst_check = new boolean[21];
			
			for(int i=0; i<4; i++) {
				int count = 0;
				for(int j=0; j<10; j++) {
					boolean check = true;
					for(int k=0; k<i; k++) {
						if(c_block.current_shape[1][i] == c_block.current_shape[1][k]){
							check = false;
							break;
						}
					}
					if(check && this.map[j][c_block.current_shape[1][i]/Multi_Data.myBlockSize] !=0) 
						count++;
				}
				if(count == 10) {
					burst_check[c_block.current_shape[1][i]/Single_Data.BlockSize] = true;
					line_count++;
				}
			}
			if(line_count != 0) //line_count가 한개이상이면 터뜨리러가야됨 !
				burst_line(burst_check, line_count);
		}
		
		public void load_map() { //마지막까지 내려온 블럭을 map에 저장하는 함수
			for(int i=0; i<4; i++) {
				this.map[c_block.current_shape[0][i]/Multi_Data.myBlockSize]
						[c_block.current_shape[1][i]/Multi_Data.myBlockSize] = c_block.color;
			}
		}
}
