package Multi_mod;

import Client.GUI_manager;
import Single_mod.Single_Data;

public class Multi_Map {
	//0, 11�� �׵θ���, 21�� �׵θ���
		//���빰�� 0�̸� ����ִ�, 1~7������ ����������
		public int[][] map = new int[10][21];
		Multi_Block c_block = null; //�ʿ��� �����̰��ִ� �����
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
			c_block.color = 0; //current_shape�� �Ͷ߸����ΰ��� �����̸� ��������ؼ�.
			//how? current_shape�� color�� 0�� �ƴҰ�쿡�� ����Ѵ�. 
			
			try {Thread.sleep(Multi_Data.speed * 10);} catch (Exception e) {e.getStackTrace();} //�����ٰ� !
			
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
		
		public void check_complete() { //�ϼ��� �����ִ��� �˻�
			int line_count = 0; //�ϼ��� ���� �Ѱ���
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
			if(line_count != 0) //line_count�� �Ѱ��̻��̸� �Ͷ߸������ߵ� !
				burst_line(burst_check, line_count);
		}
		
		public void load_map() { //���������� ������ ���� map�� �����ϴ� �Լ�
			for(int i=0; i<4; i++) {
				this.map[c_block.current_shape[0][i]/Multi_Data.myBlockSize]
						[c_block.current_shape[1][i]/Multi_Data.myBlockSize] = c_block.color;
			}
		}
}
