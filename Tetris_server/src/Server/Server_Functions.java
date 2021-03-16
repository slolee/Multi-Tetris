package Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;

public class Server_Functions extends Thread {
	Socket socket = null;
	Socket socket2 = null;
	PrintWriter pw = null;
	BufferedReader br = null;
	PrintWriter pw2 = null;
	String call = null;
	String login_id = null;

	public Server_Functions(Socket socket, Socket socket2) {
		this.socket = socket;
		this.socket2 = socket2;

		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())));
			br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			pw2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket2.getOutputStream())));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String query = null;

		try {
			conn = Server_DB_Connect.getConnection(); // DB연동상태를 유지

			while ((call = br.readLine()) != null) {
				String[] temp = call.split(" ");
				switch (temp[0]) {
				case "signup":
					query = "insert into users values" + "(?, ?, ?, ?, ?, ?, ?)"; // 유저 테이블에 회원가입한 정보를 추가한다
					// 1:ID, 2:PW, 3:NAME, 4:HIGH_SCORE
					// 5:TOTAL_WIN, 6:TOTAL_LOSE, 7:TOTAL_RATING
					pstm = conn.prepareStatement(query);
					pstm.setString(1, temp[1]);
					pstm.setString(2, temp[2]);
					pstm.setString(3, temp[3]);
					pstm.setInt(4, 0);
					pstm.setInt(5, 0);
					pstm.setInt(6, 0);
					pstm.setInt(7, 0);
					pstm.executeUpdate();

					query = "create table " + temp[1] + "(" + "id varchar2(15) primary key,"
							+ "opponent varchar2(15) not null," + "win number," + "lose number," + "rating number)";
					pstm = conn.prepareStatement(query);
					pstm.executeQuery();

					break;
				case "overlap":
					// temp[1] = id
					query = "select * from users where id=?";
					pstm = conn.prepareStatement(query);
					pstm.setString(1, temp[1]);
					rs = pstm.executeQuery();
					if (rs.next())
						pw.println("fail");
					else
						pw.println("success");
					pw.flush();
					break;
				case "login":
					// "login " + login_id + " " + login_pw
					query = "select id, pw from users where id=?";
					pstm = conn.prepareStatement(query);
					pstm.setString(1, temp[1]);
					rs = pstm.executeQuery();
					if (rs.next()) {
						if (temp[2].equals(rs.getString(2))) {
							if(Server_OpenSocket.login_users.size() == 8) {
								pw.println("over");
							}else if(Server_OpenSocket.login_users.containsKey(temp[1])){
								pw.println("already_login");
							}else {
								pw.println("success");
								synchronized (this) {
									Server_OpenSocket.login_users.put(temp[1], pw);
									Server_OpenSocket.login_users2.put(temp[1], pw2);
								}
							}
						} else
							pw.println("pw_discord");
					} else
						pw.println("id_discord");
					pw.flush();
					login_id = temp[1];
					break;
				case "logout":
					synchronized (this) {
						Server_OpenSocket.login_users.remove(login_id);
						Server_OpenSocket.login_users2.remove(login_id);
					}
					break;
				case "single_score":
					query = "select high_score from users where id=?";
					pstm = conn.prepareStatement(query);
					pstm.setString(1, login_id);
					rs = pstm.executeQuery();
					while (rs.next()) {
						if (Integer.parseInt(temp[1]) > Integer.parseInt(rs.getString(1))) {
							query = "update users set high_score=? where id=?";
							pstm = conn.prepareStatement(query);
							pstm.setInt(1, Integer.parseInt(temp[1]));
							pstm.setString(2, login_id);
							pstm.executeUpdate();
						}
					}
					break;
				case "rank_search":
//					1. temp[1]에 저장되어 있는 ID의 정보를 먼저 select 후 보낸다.
//					2. 랭킹이 1~5인 정보를 select 후 String에 예쁘게 담아 보낸다.
					query = "select rn, high_score from("
							+ "select id, high_score, rank() over(order by high_score desc) as rn " + "from users)"
							+ "where id=?";
					pstm = conn.prepareStatement(query);
					pstm.setString(1, login_id);
					rs = pstm.executeQuery();
					while (rs.next()) {
						String t = "my_rank " + rs.getString(1) + " " + rs.getString(2);
						this.pw.println(t);
						this.pw.flush();
					}

					query = "select id, high_score, rn from(select id, high_score, rank() over(order by high_score desc) as rn from users) where rownum <= 5";
					pstm = conn.prepareStatement(query);
					rs = pstm.executeQuery();

					String t = "top5_rank ";
					while (rs.next()) {
						t += (rs.getString(1) + "/" + rs.getString(2) + " ");
					}
					this.pw.println(t);
					this.pw.flush();
					this.pw.println("end");
					this.pw.flush();
					break;
				case "user_list":
					synchronized (this) {
						StringBuilder id_list = new StringBuilder("user_list");
						for(Object id_temp:Server_OpenSocket.login_users.keySet().toArray()) {
							id_list.append(" " + id_temp);
						}
						pw.println(id_list.toString());
						pw.flush();
						
						StringBuilder room_list = new StringBuilder("room_list");
						for(int i=0; i<7; ++i) {
							for(int j=0; j<2; ++j) {
								room_list.append(" " + Server_OpenSocket.battle_room[i][j]);
							}
						}
						pw.println(room_list.toString());
						pw.flush();
						
						pw.println("end");
						pw.flush();
					}
					break;
				case "select_user":
					synchronized (this) {
						query = "select total_win, total_lose, total_rating from users where id=?";
						pstm = conn.prepareStatement(query);
						pstm.setString(1, temp[1]);
						rs = pstm.executeQuery();
						if(rs.next()) 
							pw.println("success " + rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getDouble(3));
						else 
							pw.println("failed");
						pw.flush();
					}
					break;
					// "send_message " + login_user_id[click_user_index] + " " + Message;
				case "send_message":
					synchronized (this) {
						String message_content = login_id + " ";
						for(int i=2; i<temp.length; ++i)
							message_content += temp[i] + " ";
						
						Server_OpenSocket.login_users2.get(temp[1]).println("message " + message_content);
						Server_OpenSocket.login_users2.get(temp[1]).flush();
					}
					break;
				case "create_room":
					synchronized (this) {
						if(Server_OpenSocket.room_count > 6) {
							pw.println("full_room");
							pw.flush();
						}else {
							Server_OpenSocket.battle_room[Server_OpenSocket.room_count][0] = login_id;
							Server_OpenSocket.room_count++;
							pw.println("success");
							pw.flush();
						}
					}
					break;
				case "join_room":
					synchronized (this) {
						if(Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][0] == null) 
							pw.println("empty_room");
						else if(Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][1] != null) 
							pw.println("already");
						else {
							Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][1] = login_id;
							pw.println("success " + Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][0]);
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][0]).println("oppo_join " + Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][1]);
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[Integer.parseInt(temp[1])][0]).flush();
						}
						pw.flush();
						pw.println("end");
						pw.flush();
					}
					break;
				case "exit_battle_room":
					synchronized (this) {
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
								if(Server_OpenSocket.battle_room[i][1] == null) {
									Server_OpenSocket.battle_room[i][0] = null;
									Server_OpenSocket.room_count--;
									for(int j=i; j<Server_OpenSocket.room_count; ++j) {
										Server_OpenSocket.battle_room[j][0] = Server_OpenSocket.battle_room[j + 1][0];
										Server_OpenSocket.battle_room[j][1] = Server_OpenSocket.battle_room[j + 1][1];
										Server_OpenSocket.battle_room[j + 1][0] = null;
										Server_OpenSocket.battle_room[j + 1][1] = null;
										Server_OpenSocket.battle_ready[j][0] = Server_OpenSocket.battle_ready[j + 1][0];
										Server_OpenSocket.battle_ready[j][1] = Server_OpenSocket.battle_ready[j + 1][1];
										Server_OpenSocket.battle_ready[j + 1][0] = false;
										Server_OpenSocket.battle_ready[j + 1][1] = false;
									}
								}else {
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("oppo_exit " + Server_OpenSocket.battle_room[i][1]);
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
									Server_OpenSocket.battle_room[i][0] = Server_OpenSocket.battle_room[i][1];
									Server_OpenSocket.battle_room[i][1] = null;
									Server_OpenSocket.battle_ready[i][0] = Server_OpenSocket.battle_ready[i][1];
									Server_OpenSocket.battle_ready[i][1] = false;
								}
								break;
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("oppo_exit " + Server_OpenSocket.battle_room[i][1]);
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
								Server_OpenSocket.battle_room[i][1] = null;
								Server_OpenSocket.battle_ready[i][1] = false;
								break;
							}
						}
					}
					break;
				case "ready":
					synchronized (this) {
						//temp[1]이 아이디
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
								if(Server_OpenSocket.battle_ready[i][1]) {
									pw.println("start");
									pw.flush();
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("start");
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
								}else {
									Server_OpenSocket.battle_ready[i][0] = true;
									pw.println("wait");
									pw.flush();
								}
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								if(Server_OpenSocket.battle_ready[i][0]) {
									pw.println("start");
									pw.flush();
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("start");
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
								}else {
									Server_OpenSocket.battle_ready[i][1] = true;
									pw.println("wait");
									pw.flush();
								}
							}
						}
					}
					break;
				case "ready_off":
					synchronized (this) {
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
								Server_OpenSocket.battle_ready[i][0] = false;
								break;
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								Server_OpenSocket.battle_ready[i][1] = false;
								break;
							}
						}
					}
					break;
				case "gameover":
					synchronized (this) {
						String win_user = null, lose_user = null;
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
								win_user = Server_OpenSocket.battle_room[i][1];
								lose_user = Server_OpenSocket.battle_room[i][0];
								Server_OpenSocket.battle_ready[i][0] = false;
								Server_OpenSocket.battle_ready[i][1] = false;
								Server_OpenSocket.login_users2.get(win_user).println("gameover");
								Server_OpenSocket.login_users2.get(win_user).flush();
								Server_OpenSocket.login_users2.get(win_user).println("win");
								Server_OpenSocket.login_users2.get(win_user).flush();
								Server_OpenSocket.login_users2.get(lose_user).println("lose");
								Server_OpenSocket.login_users2.get(lose_user).flush();
								break;
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								win_user = Server_OpenSocket.battle_room[i][0];
								lose_user = Server_OpenSocket.battle_room[i][1];
								Server_OpenSocket.battle_ready[i][0] = false;
								Server_OpenSocket.battle_ready[i][1] = false;
								Server_OpenSocket.login_users2.get(win_user).println("gameover");
								Server_OpenSocket.login_users2.get(win_user).flush();
								Server_OpenSocket.login_users2.get(lose_user).println("lose");
								Server_OpenSocket.login_users2.get(lose_user).flush();
								Server_OpenSocket.login_users2.get(win_user).println("win");
								Server_OpenSocket.login_users2.get(win_user).flush();
								break;
							}
						}
						query = "update users set total_win = total_win + 1 where id = ?";
						pstm = conn.prepareStatement(query);
						pstm.setString(1, win_user);
						pstm.executeUpdate();
						
						query = "update users set total_lose = total_lose + 1 where id = ?";
						pstm = conn.prepareStatement(query);
						pstm.setString(1, lose_user);
						pstm.executeUpdate();
						
						query = "update users set total_rating = total_win / (total_win + total_lose) * 100 where not (total_win + total_lose) = 0";
						pstm = conn.prepareStatement(query);
						pstm.executeUpdate();
					}
					break;
				case "transmit":
					synchronized (this) {
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]) != null && login_id.equals(Server_OpenSocket.battle_room[i][0])) {
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("transmit");
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println(br.readLine());
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
								break;
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("transmit");
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println(br.readLine());
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
								break;
							}
						} 
					}
					break;
				case "attack":
					synchronized (this) {
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("attack " + temp[1]);
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
								break;
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("attack " + temp[1]);
								Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
								break;
							}
						}
					}
					break;
				case "chat":
					synchronized (this) {
						String chat_content = login_id + ": ";
						for(int i=1; i<temp.length; ++i)
							chat_content += temp[i] + " ";
						for(int i=0; i<Server_OpenSocket.room_count; ++i) {
							if(login_id.equals(Server_OpenSocket.battle_room[i][0])) {
								if(Server_OpenSocket.battle_room[i][1] != null) {
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("chat " + chat_content);
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
									break;
								}
							}else if(login_id.equals(Server_OpenSocket.battle_room[i][1])) {
								if(Server_OpenSocket.battle_room[i][0] != null) {
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("chat " + chat_content);
									Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
									break;
								}
							}
						}
					}
					break;
				}
			}
		} catch(SocketException e) {
			synchronized (this) {
				Server_OpenSocket.login_users.remove(login_id);
				Server_OpenSocket.login_users2.remove(login_id);
				for(int i=0; i<Server_OpenSocket.room_count; ++i) {
					if(Server_OpenSocket.battle_room[i][0].equals(login_id)) {
						if(Server_OpenSocket.battle_room[i][1] == null) {
							Server_OpenSocket.battle_room[i][0] = null;
							Server_OpenSocket.room_count--;
							for(int j=i; j<Server_OpenSocket.room_count; ++j) {
								Server_OpenSocket.battle_room[j][0] = Server_OpenSocket.battle_room[j + 1][0];
								Server_OpenSocket.battle_room[j][1] = Server_OpenSocket.battle_room[j + 1][1];
								Server_OpenSocket.battle_room[j + 1][0] = null;
								Server_OpenSocket.battle_room[j + 1][1] = null;
								Server_OpenSocket.battle_ready[j][0] = Server_OpenSocket.battle_ready[j + 1][0];
								Server_OpenSocket.battle_ready[j][1] = Server_OpenSocket.battle_ready[j + 1][1];
								Server_OpenSocket.battle_ready[j + 1][0] = false;
								Server_OpenSocket.battle_ready[j + 1][1] = false;
							}
						}else {
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("oppo_exit " + Server_OpenSocket.battle_room[i][1]);
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("gameover");
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).println("win");
							Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][1]).flush();
							
							Server_OpenSocket.battle_room[i][0] = Server_OpenSocket.battle_room[i][1];
							Server_OpenSocket.battle_room[i][1] = null;
							Server_OpenSocket.battle_ready[i][0] = Server_OpenSocket.battle_ready[i][1];
							Server_OpenSocket.battle_ready[i][1] = false;
						}
						break;
					}else if(login_id != null && login_id.equals(Server_OpenSocket.battle_room[i][1])) {
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("oppo_exit " + Server_OpenSocket.battle_room[i][1]);
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("gameover");
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).println("win");
						Server_OpenSocket.login_users2.get(Server_OpenSocket.battle_room[i][0]).flush();
						Server_OpenSocket.battle_room[i][1] = null;
						Server_OpenSocket.battle_ready[i][1] = false;
						break;
					}
				}
			}
			System.out.println("===== Client 종료 =====");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
