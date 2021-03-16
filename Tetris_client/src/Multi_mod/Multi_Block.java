package Multi_mod;

import Client.GUI_manager;

public class Multi_Block {
	public int currrent_block = 9; //�ϴ� ���� �����ȵǾ��� �ְ�, game start��ư Ŭ���� next_block[0]�� �Ϸο��� ��ĭ�� ����
	//���� �����̰� �ִ� ���� ���� ����
	public int color = 0; //��, ��, ��, ��, ��, ��, ��
	public int[][] current_shape = new int[2][4];  //0(X��ǥ 4��), 1(Y��ǥ 4��)
	public int spin_index = 0; //������ � ����� �������ִ��� ��ǥ�� �ƴ϶�, �ѹ������� �������ִ� ����.
	//ȸ������ ���� ���� shape�� �ٲٱ� ���� �����Ѵ�
	GUI_manager F = null;
	
	public Multi_Block(GUI_manager F) {
		this.F = F;
	}
	
	public void newBlock() {
		this.currrent_block = Multi_Data.next_block[0];
		Multi_Data.next_block[0] = Multi_Data.next_block[1];
		Multi_Data.next_block[1] = Multi_Data.next_block[2];
		while(true) {
			Multi_Data.next_block[2] = (int) (Math.random() * 7);
			if(Multi_Data.next_block[1] != Multi_Data.next_block[2])
				break;
		}
		setting_Block();
	}
	
	public void spin() {
		int past_spin = this.spin_index;
		switch(this.currrent_block) {
		case 1:
			if(this.spin_index == 1)
				this.spin_index = 7;
			else if(this.spin_index == 7 || this.spin_index == 8)
				this.spin_index++;
			else
				this.spin_index = 1;
			break;
		case 2:
			if(this.spin_index == 2)
				this.spin_index = 10;
			else if(this.spin_index == 10 || this.spin_index == 11)
				this.spin_index++;
			else
				this.spin_index = 2;
			break;
		case 3:
			if(this.spin_index == 3)
				this.spin_index = 13;
			else if(this.spin_index == 13 || this.spin_index == 14)
				this.spin_index++;
			else
				this.spin_index = 3;
			break;
		case 4:
			if(this.spin_index == 4)
				this.spin_index = 16;
			else if(this.spin_index == 16 || this.spin_index == 17)
				this.spin_index++;
			else
				this.spin_index = 4;
			break;
		case 5:
			if(this.spin_index == 5)
				this.spin_index = 19;
			else if(this.spin_index == 19 || this.spin_index == 20)
				this.spin_index++;
			else
				this.spin_index = 5;
			break;
		case 6:
			if(this.spin_index == 6)
				this.spin_index = 22;
			else if(this.spin_index == 22 || this.spin_index == 23)
				this.spin_index++;
			else
				this.spin_index = 6;
			break;
		default:
		}
		
		//���⼭ �������Ǵ��� ����ó������ߵȴ�!! �̰ź��ͽ���
		//������������ temp_x�� temp_y�� ����
		int[] temp_x = new int[4];
		int[] temp_y = new int[4];
		for(int i=0; i<4; i++) {
			int move_x = this.current_shape[0][i]/Multi_Data.myBlockSize - Multi_Data.block_X[past_spin][i];
			int move_y = this.current_shape[1][i]/Multi_Data.myBlockSize - Multi_Data.block_Y[past_spin][i];
			
			temp_x[i] = (Multi_Data.block_X[this.spin_index][i] + move_x) * Multi_Data.myBlockSize;
			temp_y[i] = (Multi_Data.block_Y[this.spin_index][i] + move_y) * Multi_Data.myBlockSize;
		}
		
		//������ ���� ���ʸ��� ������� Ȯ��(Clear)
		//������ ���� �����ʸ��� ������� Ȯ��(Clear)
		//������ ���� �ǹؿ��� ������� Ȯ��(Clear)
		//������ ���� �������� �ٸ����� ħ���ߴ��� Ȯ��
		//������ ���� ���������� �ٸ����� ħ���ߴ��� Ȯ��
		
		//�ش�Ǵ°� ������ �ݴ��ʹ������� �׸�ŭ �̵�
		
		//���⼭ �̵��� ���� �� ��ġ�°��ִٸ� �׳� ȸ���� ���ϰ� �ع�����.

		int distance = 0;
		int distance2 = 999;
		for(int i=0; i<4; i++) {
			if(temp_y[i] > 20*Multi_Data.myBlockSize) { //3. �ǹ��� �Ѿ�� �ȵǴ°Ŵ�.
				return;
			}else if(temp_x[i] < 0) { //1. ���ʸ��� ������� Ȯ��
				if(distance < (-1 * temp_x[i])) //�󸶳� ��������� Ȯ���ؼ� �׸�ŭ �̵��Ÿ��� ����
					distance = -1 * temp_x[i];
			}else if(temp_x[i] > 9*Multi_Data.myBlockSize) { //2. �����ʸ��� ������� Ȯ��
				if(distance2 > -1 * (temp_x[i] - 9*Multi_Data.myBlockSize))
					distance2 = -1 * (temp_x[i] - 9*Multi_Data.myBlockSize); //�󸶳� ��������� Ȯ���ؼ� �׸�ŭ �̵��Ÿ��� ����
			}else if(Battle_room.Map_object.map[temp_x[i]/Multi_Data.myBlockSize][temp_y[i]/Multi_Data.myBlockSize] != 0) {
				try {
					if(this.current_shape[0][i] > temp_x[i]) { //������ġ�� x��ǥ�� ��������ġ�� x��ǥ���� ũ�İ� �����ִ�.
						//ũ�ٸ� ���ʿ��� ���������̴�.
						int count = 0;
						for(int j=0; j<4; j++) {
							//�׷��� ���������� ���鼭 �˻縦�ؾߵȴ�.
							if(Battle_room.Map_object.map[temp_x[i]/Multi_Data.myBlockSize + j][temp_y[i]/Multi_Data.myBlockSize] != 0) {
								count++;
							}
						}
						if(distance < count * Multi_Data.myBlockSize)
							distance = count * Multi_Data.myBlockSize;
					} else {
						int count = 0;
						for(int j=0; j<4; j++) {
							if(Battle_room.Map_object.map[temp_x[i]/Multi_Data.myBlockSize - j][temp_y[i]/Multi_Data.myBlockSize] != 0) {
								count++;
							}
						}
						if(distance2 > -1 * count * Multi_Data.myBlockSize)
							distance2 = -1 * count * Multi_Data.myBlockSize;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					//�ε����������� �����Ҳ���. ������ �������� �ű��ȵȴٴ¶��̴� �ٸ���ȭ ���൵�ɰ� ����.
				}
			}
		}
		if(distance2 != 999)
			distance = distance2;
		if(distance != 0) 
			for(int i=0; i<4; i++)
				temp_x[i] += distance;

		boolean final_check = true; 
		for(int i=0; i<4; i++) {
			if(temp_x[i] > 9*Multi_Data.myBlockSize || temp_x[i] < 0 ||
					Battle_room.Map_object.map[temp_x[i]/Multi_Data.myBlockSize][temp_y[i]/Multi_Data.myBlockSize] != 0)
				final_check = false;
		}
		
		if(final_check) {
			for(int i=0; i<4; i++) {
				this.current_shape[0][i] = temp_x[i];
				this.current_shape[1][i] = temp_y[i];
			}
		}
		
	}
	
	public void move(String direct) {
		if(direct.equals("left")) {
			//���Ͽ����� ��ǥ�� 0�̴�. �� current_block�� ����� 0���ϳ��� ������ �����̵��Ұ�.
			boolean move_possible = true;
			for(int i=0; i<4; i++) {
				if(this.current_shape[0][i] == 0) {
					move_possible = false;
					break;
				//���� �ƴѾֵ� �߿��� �̵��Ϸ��� ���� ���� ������ �ȵǴϱ� �װ͵� �ɷ���ߵȴ�.
				}else if(Battle_room.Map_object.map[(this.current_shape[0][i]-Multi_Data.myBlockSize)/Multi_Data.myBlockSize]
						[this.current_shape[1][i]/Multi_Data.myBlockSize] != 0) {
					move_possible = false;
					break;
				}
			}
			//�̵��� �����ϴٸ� ?
			if(move_possible) {
				for(int i=0; i<4; i++) 
					this.current_shape[0][i] -= Multi_Data.myBlockSize;
			}
		} else if(direct.equals("right")) {
			boolean move_possible = true;
			for(int i=0; i<4; i++) {
				if(this.current_shape[0][i] == 9*Multi_Data.myBlockSize) {
					move_possible = false;
					break;
				}else if(Battle_room.Map_object.map[(this.current_shape[0][i]+Multi_Data.myBlockSize)/Multi_Data.myBlockSize]
						[this.current_shape[1][i]/Multi_Data.myBlockSize] != 0) {
					move_possible = false;
					break;
				}
			}
			//�̵��� �����ϴٸ� ?
			if(move_possible) {
				for(int i=0; i<4; i++) 
					this.current_shape[0][i] += Multi_Data.myBlockSize;
			}
		} 
	}
	
	public void down_Block() {
		if(this.currrent_block == 9) { //���� ����9��
			newBlock();
		} else {
			int max_y = 0; //y��ǥ �ִ밪�� ���ϰ�
			for(int i=0; i<4; i++) 
				if(this.current_shape[1][i] > max_y) 
					max_y = this.current_shape[1][i];
		
			for(int i=0; i<4; i++) { //���� y��ǥ�� �� �������� �ִ밪, ��, ����ؿ��ִ� �����鿡 ���ؼ�
				if(this.current_shape[1][i] == max_y) {
					if(max_y == 20*Multi_Data.myBlockSize) { //�� �ٴ��̶� y��ǥ�� ������ map�� �� ���� �����ϰ�, current_block�� 9�� �����ؼ� newBlock()�Լ� ȣ��.
						this.currrent_block = 9;
						Battle_room.Map_object.load_map();
						Battle_room.Map_object.check_complete();
						return;
					} 
				}
				if(Battle_room.Map_object.map[this.current_shape[0][i]/Multi_Data.myBlockSize]
						[(this.current_shape[1][i] + Multi_Data.myBlockSize)/Multi_Data.myBlockSize] != 0) {
					this.currrent_block = 9;
					Battle_room.Map_object.load_map();
					Battle_room.Map_object.check_complete();
					return;
				}
			}
			
			for(int i=0; i<4; i++)
				this.current_shape[1][i] += Multi_Data.myBlockSize;	
		}
	}
	
	private void setting_Block() { //current_blcok�� �ް��� �׿����� ������ Data�� �����Ѵ�.
		for(int i=0; i<4; i++) {
			this.current_shape[0][i] = (Multi_Data.block_X[this.currrent_block][i] + 4) * Multi_Data.myBlockSize;
			this.current_shape[1][i] = Multi_Data.block_Y[this.currrent_block][i] * Multi_Data.myBlockSize;
		}
		this.color = this.currrent_block + 1; //�÷��� 0���� ��������Ѵ�
		this.spin_index = this.currrent_block;
		
		try {
			F.pw.println("transmit");
			F.pw.flush();
			String value = "";
			for(int i=0; i<10; ++i) 
				for(int j=0; j<21; ++j) 
					value += Battle_room.Map_object.map[i][j] + " ";
			F.pw.println(value);
			F.pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//���⼭ gameover �Ǻ��� �ؾ߰ڴ�.
		int max_y = 0; //y��ǥ �ִ밪�� ���ϰ�
		for(int i=0; i<4; i++) 
			if(this.current_shape[1][i] > max_y) 
				max_y = this.current_shape[1][i];
		for(int i=0; i<4; i++) { //���� y��ǥ�� �� �������� �ִ밪, ��, ����ؿ��ִ� �����鿡 ���ؼ�
			if(this.current_shape[1][i] == max_y) {
				if(Battle_room.Map_object.map[this.current_shape[0][i]/Multi_Data.myBlockSize]
						[(this.current_shape[1][i] + Multi_Data.myBlockSize)/Multi_Data.myBlockSize] != 0) {
					Battle_room.Map_object.load_map();
					Multi_Data.check = false;
					Multi_Data.status = "gameover";
					break;
				}
			}
		}
	}
	
	public void full_down() {
		//���� �������� Single_Data.current_shape�� ������.
		while(true) { //down���� �̸� ���ѷ��������� �������� �������� ��쿡�� ȭ�鿡 ����ϰԲ� �ߴ�
			int max_y = 0; //y��ǥ �ִ밪�� ���ϰ�
			for(int i=0; i<4; i++) 
				if(this.current_shape[1][i] > max_y) 
					max_y = this.current_shape[1][i];
		
			for(int i=0; i<4; i++) { //���� y��ǥ�� �� �������� �ִ밪, ��, ����ؿ��ִ� �����鿡 ���ؼ�
				if(this.current_shape[1][i] == max_y) {
					if(max_y == 20*Multi_Data.myBlockSize) { //�� �ٴ��̶� y��ǥ�� ������ map�� �� ���� �����ϰ�, current_block�� 9�� �����ؼ� newBlock()�Լ� ȣ��.
						this.currrent_block = 9;
						Battle_room.Map_object.load_map();
						Battle_room.Map_object.check_complete();
						return;
					} 
				}
				if(Battle_room.Map_object.map[this.current_shape[0][i]/Multi_Data.myBlockSize]
						[(this.current_shape[1][i] + Multi_Data.myBlockSize)/Multi_Data.myBlockSize] != 0) {
					this.currrent_block = 9;
					Battle_room.Map_object.load_map();
					Battle_room.Map_object.check_complete();
					return;
				}
			}
			
			for(int i=0; i<4; i++)
				this.current_shape[1][i] += Multi_Data.myBlockSize;	
		}
		
	}
}
