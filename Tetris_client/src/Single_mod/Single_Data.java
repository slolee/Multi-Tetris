package Single_mod;

import java.awt.Image;

public class Single_Data {
	public static String status = "out"; // stop/start/gameover
	public static Image[] icon_array = new Image[7]; //각 블럭의 image를 담는다 
	
	public final static int[][] block_X ={
			{0,1,0,1},{2,1,1,0},{0,1,1,2},{0,1,1,2},{2,2,1,0},{0,0,1,2,},{-1,0,1,2},//0-6 
			{1,1,2,2},{2,1,1,0},{0,0,1,1}, //7~9
			{1,0,1,0},{0,1,1,2},{0,-1,0,-1}, //10~12
			{1,1,2,1},{0,1,2,1},{0,1,1,1}, //13~15
			{1,1,1,2},{0,1,2,0},{0,1,1,1}, //16~18
			{1,2,1,1},{0,1,2,2},{1,1,1,0}, //19~21
			{1,1,1,1},{-1,0,1,2},{0,0,0,0} //22~24
	};
	public final static int[][] block_Y={
			{0,0,1,1},{0,0,1,1},{0,0,1,1,},{1,1,0,1},{0,1,1,1},{0,1,1,1},{0,0,0,0},//0~6
			{0,1,1,2},{1,1,2,2},{0,1,1,2}, //7~9
			{0,1,1,2},{1,1,2,2},{0,1,1,2}, //10~12
			{0,1,1,2},{1,1,1,2},{1,0,1,2}, //13~15
			{0,1,2,2},{1,1,1,2},{0,0,1,2}, //16~18
			{0,0,1,2},{1,1,1,2},{0,1,2,2}, //19~21
			{0,1,2,3},{1,1,1,1},{0,1,2,3} //22~24
	};
	
	public final static int BlockSize = 17;
	public static int[] next_block = new int[3]; //다음블럭의 index를 담는다 0~6

	public static int score = 0;
	public static int level = 0;

}
