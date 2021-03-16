package Single_mod;

public class Block {
	public int currrent_block = 9; //일단 아직 지정안되어져 있고, game start버튼 클릭시 next_block[0]이 일로오고 한칸씩 당긴다
	//현재 움직이고 있는 블럭에 대한 정보
	public int color = 0; //노, 초, 빨, 보, 파, 주, 하
	public int[][] current_shape = new int[2][4];  //0(X좌표 4개), 1(Y좌표 4개)
	public int spin_index = 0; //도형이 어떤 모양을 가지고있는지 좌표가 아니라, 넘버링으로 가지고있는 숫자.
	//회전했을 때에 다음 shape로 바꾸기 위해 존재한다
	
	public void newBlock() {
		this.currrent_block = Single_Data.next_block[0];
		Single_Data.next_block[0] = Single_Data.next_block[1];
		Single_Data.next_block[1] = Single_Data.next_block[2];
		while(true) {
			Single_Data.next_block[2] = (int) (Math.random() * 7);
			if(Single_Data.next_block[1] != Single_Data.next_block[2])
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
		
		//여기서 돌려도되는지 예외처리해줘야된다!! 이거부터시작
		//돌려본정보를 temp_x와 temp_y에 저장
		int[] temp_x = new int[4];
		int[] temp_y = new int[4];
		for(int i=0; i<4; i++) {
			int move_x = this.current_shape[0][i]/Single_Data.BlockSize - Single_Data.block_X[past_spin][i];
			int move_y = this.current_shape[1][i]/Single_Data.BlockSize - Single_Data.block_Y[past_spin][i];
			
			temp_x[i] = (Single_Data.block_X[this.spin_index][i] + move_x) * Single_Data.BlockSize;
			temp_y[i] = (Single_Data.block_Y[this.spin_index][i] + move_y) * Single_Data.BlockSize;
		}
		
		//돌려본 블럭이 왼쪽맵을 벗어났는지 확인(Clear)
		//돌려본 블럭이 오른쪽맵을 벗어났는지 확인(Clear)
		//돌려본 블럭이 맨밑에를 벗어났는지 확인(Clear)
		//돌려본 블럭이 왼쪽으로 다른블럭을 침범했는지 확인
		//돌려본 블릭이 오른쪽으로 다른블럭을 침범했는지 확인
		
		//해당되는게 있으면 반대쪽방향으로 그만큼 이동
		
		//여기서 이동한 블럭에 또 겹치는게있다면 그냥 회전을 못하게 해버린다.

		int distance = 0;
		int distance2 = 999;
		for(int i=0; i<4; i++) {
			if(temp_y[i] > 20*Single_Data.BlockSize) { //3. 맨밑을 넘어가면 안되는거다.
				return;
			}else if(temp_x[i] < 0) { //1. 왼쪽맵을 벗어났는지 확인
				if(distance < (-1 * temp_x[i])) //얼마나 벗어났는지를 확인해서 그만큼 이동거리에 저장
					distance = -1 * temp_x[i];
			}else if(temp_x[i] > 9*Single_Data.BlockSize) { //2. 오른쪽맵을 벗어났는지 확인
				if(distance2 > -1 * (temp_x[i] - 9*Single_Data.BlockSize))
					distance2 = -1 * (temp_x[i] - 9*Single_Data.BlockSize); //얼마나 벗어났는지를 확인해서 그만큼 이동거리에 저장
			}else if(Single.Map_object.map[temp_x[i]/Single_Data.BlockSize][temp_y[i]/Single_Data.BlockSize] != 0) {
				try {
					if(this.current_shape[0][i] > temp_x[i]) { //원래위치의 x좌표가 움직인위치의 x좌표보다 크냐고 묻고있다.
						//크다면 왼쪽에서 겹쳐진것이다.
						int count = 0;
						for(int j=0; j<4; j++) {
							//그러면 오른쪽으로 가면서 검사를해야된다.
							if(Single.Map_object.map[temp_x[i]/Single_Data.BlockSize + j][temp_y[i]/Single_Data.BlockSize] != 0) {
								count++;
							}
						}
						if(distance < count * Single_Data.BlockSize)
							distance = count * Single_Data.BlockSize;
					} else {
						int count = 0;
						for(int j=0; j<4; j++) {
							if(Single.Map_object.map[temp_x[i]/Single_Data.BlockSize - j][temp_y[i]/Single_Data.BlockSize] != 0) {
								count++;
							}
						}
						if(distance2 > -1 * count * Single_Data.BlockSize)
							distance2 = -1 * count * Single_Data.BlockSize;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					//인덱스에러나도 무시할꺼다. 어차피 에러나면 옮기면안된다는뜻이니 다른변화 안줘도될거 같다.
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
			if(temp_x[i] > 9*Single_Data.BlockSize || temp_x[i] < 0 ||
					Single.Map_object.map[temp_x[i]/Single_Data.BlockSize][temp_y[i]/Single_Data.BlockSize] != 0)
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
			//제일왼쪽의 좌표는 0이다. 즉 current_block의 요소중 0이하나라도 있으면 왼쪽이동불가.
			boolean move_possible = true;
			for(int i=0; i<4; i++) {
				if(this.current_shape[0][i] == 0) {
					move_possible = false;
					break;
				//벽이 아닌애들 중에서 이동하려는 곳에 블럭이 있으면 안되니까 그것도 걸러줘야된다.
				}else if(Single.Map_object.map[(this.current_shape[0][i]-Single_Data.BlockSize)/Single_Data.BlockSize]
						[this.current_shape[1][i]/Single_Data.BlockSize] != 0) {
					move_possible = false;
					break;
				}
			}
			//이동이 가능하다면 ?
			if(move_possible) {
				for(int i=0; i<4; i++) 
					this.current_shape[0][i] -= Single_Data.BlockSize;
			}
		} else if(direct.equals("right")) {
			boolean move_possible = true;
			for(int i=0; i<4; i++) {
				if(this.current_shape[0][i] == 9*Single_Data.BlockSize) {
					move_possible = false;
					break;
				}else if(Single.Map_object.map[(this.current_shape[0][i]+Single_Data.BlockSize)/Single_Data.BlockSize]
						[this.current_shape[1][i]/Single_Data.BlockSize] != 0) {
					move_possible = false;
					break;
				}
			}
			//이동이 가능하다면 ?
			if(move_possible) {
				for(int i=0; i<4; i++) 
					this.current_shape[0][i] += Single_Data.BlockSize;
			}
		} 
	}
	
	public void down_Block() {
		if(this.currrent_block == 9) { //현재 블럭이9면
			newBlock();
		} else {
			int max_y = 0; //y좌표 최대값을 구하고
			for(int i=0; i<4; i++) 
				if(this.current_shape[1][i] > max_y) 
					max_y = this.current_shape[1][i];
		
			for(int i=0; i<4; i++) { //만약 y좌표가 그 도형에서 최대값, 즉, 가장밑에있는 도형들에 한해서
				if(this.current_shape[1][i] == max_y) {
					if(max_y == 20*Single_Data.BlockSize) { //맨 바닥이랑 y좌표가 같으면 map에 그 색을 저장하고, current_block을 9로 설정해서 newBlock()함수 호출.
						this.currrent_block = 9;
						Single.Map_object.load_map();
						Single.Map_object.check_complete();
						return;
					} 
				}
				if(Single.Map_object.map[this.current_shape[0][i]/Single_Data.BlockSize]
						[(this.current_shape[1][i] + Single_Data.BlockSize)/Single_Data.BlockSize] != 0) {
					this.currrent_block = 9;
					Single.Map_object.load_map();
					Single.Map_object.check_complete();
					return;
				}
			}
			
			for(int i=0; i<4; i++)
				this.current_shape[1][i] += Single_Data.BlockSize;	
		}
	}
	
	private void setting_Block() { //current_blcok을 받고나서 그에대한 정보를 Data상에 저장한다.
		for(int i=0; i<4; i++) {
			this.current_shape[0][i] = (Single_Data.block_X[this.currrent_block][i] + 4) * Single_Data.BlockSize;
			this.current_shape[1][i] = Single_Data.block_Y[this.currrent_block][i] * Single_Data.BlockSize;
		}
		this.color = this.currrent_block + 1; //컬러만 0번에 흑백으로한다
		this.spin_index = this.currrent_block;
		
		//여기서 gameover 판별을 해야겠다.
		int max_y = 0; //y좌표 최대값을 구하고
		for(int i=0; i<4; i++) 
			if(this.current_shape[1][i] > max_y) 
				max_y = this.current_shape[1][i];
		for(int i=0; i<4; i++) { //만약 y좌표가 그 도형에서 최대값, 즉, 가장밑에있는 도형들에 한해서
			if(this.current_shape[1][i] == max_y) {
				if(Single.Map_object.map[this.current_shape[0][i]/Single_Data.BlockSize]
						[(this.current_shape[1][i] + Single_Data.BlockSize)/Single_Data.BlockSize] != 0) {
					Single.Map_object.load_map();
					Single_Data.status = "gameover";
					break;
				}
			}
		}
	}
	
	public void full_down() {
		//현재 블럭정보가 Single_Data.current_shape에 들어가있지.
		while(true) { //down블럭을 미리 무한루프돌려서 마지막에 도착했을 경우에만 화면에 출력하게끔 했다
			int max_y = 0; //y좌표 최대값을 구하고
			for(int i=0; i<4; i++) 
				if(this.current_shape[1][i] > max_y) 
					max_y = this.current_shape[1][i];
		
			for(int i=0; i<4; i++) { //만약 y좌표가 그 도형에서 최대값, 즉, 가장밑에있는 도형들에 한해서
				if(this.current_shape[1][i] == max_y) {
					if(max_y == 20*Single_Data.BlockSize) { //맨 바닥이랑 y좌표가 같으면 map에 그 색을 저장하고, current_block을 9로 설정해서 newBlock()함수 호출.
						this.currrent_block = 9;
						Single.Map_object.load_map();
						Single.Map_object.check_complete();
						return;
					} 
				}
				if(Single.Map_object.map[this.current_shape[0][i]/Single_Data.BlockSize]
						[(this.current_shape[1][i] + Single_Data.BlockSize)/Single_Data.BlockSize] != 0) {
					this.currrent_block = 9;
					Single.Map_object.load_map();
					Single.Map_object.check_complete();
					return;
				}
			}
			
			for(int i=0; i<4; i++)
				this.current_shape[1][i] += Single_Data.BlockSize;	
		}
		
	}
}
