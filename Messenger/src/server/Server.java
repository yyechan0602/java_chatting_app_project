package server;

//입출력 (서버와 클라이언트 통신)
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//다른 컴퓨터와 연결
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import function.Function;

public class Server implements Runnable {
	private ServerSocket ss;
	private final int PORT = 1120;
	private Vector<Client> waitVc = new Vector<Client>();

	Database db = null;

	String room_id, isPublic, number_Of_People, pos;
	// 쓰레드에서 동기화 프로그램

	// 서버 가동
	public Server() {
		db = new Database();
		try {
			ss = new ServerSocket(PORT);
			/*
			 * bind() => IP, PORT 묶어주는 역할 ==> 핸드폰 (개통) listen() => 대기상태(클라이언트가 접속하기 전까지 대기)
			 */
			System.out.println("Server Start...");
		} catch (Exception ex) {
		}
	}

	// 클라이언트가 접속을 했을 경우 => 각자 통신이 가능하게 쓰레드와 연결
	@Override
	public void run() {
		try {
			while (true) {
				Socket s = ss.accept();
				// accept() : 특수한 메소드 => 클라이언트가 접속시에만 호출되는 메소드
				Client client = new Client(s);
				client.start(); // 클라이언트와 통신 승인
			}
		} catch (Exception ex) {
		}
	}

	public static void main(String[] args) {
		// 서버가동
		Server server = new Server();
		new Thread(server).start();
		// Server 클래스에 있는 run을 호출

	}

	// 내부 클래스 => Server가 가지고 있는 데디터 쉽게 공유가 가능하게 만든다
	class Client extends Thread {
		// 클라이언트와 연결
		Socket s;
		// 클라이언트로부터 요청을 받는다
		BufferedReader in;
		// 요청을 처리한 다음에 결과를 응답
		OutputStream out;
		String id, password, name, sex;
		Boolean online = false;

		public Client(Socket s) {
			try {
				this.s = s;
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				// 클라이언트 요청한 메소드를 읽어오는 메모리 공간
				out = s.getOutputStream();
			} catch (Exception ex) {
			}
		}

		// 통신 ? => 기능(요청처리)
		public void run() {
			try {
				while (true) {
					// 클라이언트가 요청한 내용 받는다
					String msg = in.readLine();

					System.out.println("==============");
					System.out.println(msg);
					StringTokenizer st = new StringTokenizer(msg, "|");
					int protocol = Integer.parseInt(st.nextToken());
					// function|id|대화명|성별
					switch (protocol) {
					// LogIn|Id|password

					// 로그인처리
					case (Function.LOGIN): {
						// 데이터 값 받기
						id = st.nextToken();
						password = st.nextToken();
						if (db.LogIn(id, password)) {
							name = db.getName(id);
							sex = db.getSex(id);
							online = true;
							// 접속한 사람의 정보를 저장
							waitVc.add(this);
							// 로그인창에서 => 대기창으로 변경
							messageTo(Function.PERMIT_LOGIN + "|" + id);

							// 모든 사람에게 접속 멤버 보내기
							message_Reset_Members();
							message_Members();
							System.out.println(" LOGIN " + id);
							
							// 대기방으로 들어가기
							if (!db.can_Enter_Room("대기방", id, "NA")) {
								db.go_Out(id);
							}
							
							messageTo(Function.PERMIT_ENTER_ROOM + "|" + "대기방");
							db.enter_Room("대기방", id);
							// 모든 사람에게 현재 방 보내기
							message_Reset_Rooms();
							message_Rooms();

						} else {
							// 로그인 거절
							messageTo_Offline(Function.REJECT_LOGIN + "|" + "is_Not_Correct_Error");
						}
						break;
					}

					// 로그아웃 처리
					case (Function.LOGOUT): {
						id = st.nextToken();
						for (Client user : waitVc) {
							if (id.equals(user.id)) {
								user.online = false;
							}
						}
						db.go_Out(id);

						// 모든 사람에게 접속 멤버 보내기
						message_Reset_Members();
						message_Members();
						
						message_Reset_Rooms();
						message_Rooms();
						
						System.out.println(" LOGOUT " + id);
						break;
					}
					// 회원가입 처리
					case (Function.SIGNUP): {
						id = st.nextToken();
						password = st.nextToken();
						name = st.nextToken();
						sex = st.nextToken();
						// 중복 ID 확인
						if (db.SignUp(id)) {
							db.insert_Client(id, password, name, sex);
							// 회원가입 성공!
							messageTo(Function.PERMIT_SIGNUP + "|" + id);

							System.out.println(" signup " + id);
						} else {
							// 회원가입 거절
							messageTo_Offline(Function.REJECT_SIGNUP + "|" + "is_Exist_Id_Error");
						}
						break;
					}

					// 채팅받고 보내기
					case (Function.CHATTING): {
						id = st.nextToken();
						msg = st.nextToken();

						// 채팅보낸사람의 위치 확인
						room_id = db.getRoom_Id(id);
						
						// 위치가 같은사람에게 메세지 보내기
						for (Client user : waitVc) {
							if (db.getRoom_Id(user.id).equals(room_id)) {
								user.messageTo(Function.CHATTING + "|" + id + "|" + msg);
							}
						}
						// 채팅로그에 채팅 넣기
						db.insert_Chat_Log(room_id, id, msg);
						break;
					}
					// 방만든다는 요청이 들어오면
					case (Function.MAKEROOM): {
						room_id = st.nextToken();
						// 방이 존재하는지 확인해서 없으면
						if (!db.Exist_Room(room_id)) {
							//방 생성
							password = st.nextToken();
							isPublic = st.nextToken();
							number_Of_People = st.nextToken();
							db.make_Room(room_id, password, isPublic, number_Of_People);
							
							// 만든 사람에게 방이 만들어졌다는 메시지 출력
							messageTo(Function.PERMIT_MAKE_ROOM + "|" + room_id);
							// 모든 사람의 방 목록 초기화후, 다시 방 목록 보내기
							
							message_Reset_Rooms();
							message_Rooms();
							
							// 방이 있으면 거절하기
						} else {
							messageTo(Function.REJECT_MAKE_ROOM + "|" + "is_Exist_Room_Error");
						}
						break;
					}

					// 방 들어간다는 요청 사항 발생시
					case (Function.ENTERROOM): {
						room_id = st.nextToken();
						id = st.nextToken();
						password = st.nextToken();
						// 방이 존재하고
						if (db.Exist_Room(room_id)) {
							// 방에 들어갈수 있으면 들어가기
							if (db.can_Enter_Room(room_id, id, password)) {
								messageTo(Function.PERMIT_ENTER_ROOM + "|" + room_id);
								db.go_Out(id);
								db.enter_Room(room_id, id);
							} else {
								// 방에 사람이 꽉 차거나/ 패스워드가 다르면 발생
								messageTo(Function.REJECT_ENTER_ROOM + "|" + "is_No_More_Space_Error");
							}
						} else {
							// 방이 존재하지 않으면 발생
							messageTo(Function.REJECT_ENTER_ROOM + "|" + "not_Exist_Error");
						}
						break;
					}
					}
				}
			} catch (Exception e) {

			}

		}

		// 모든사람에게 접속중인 멤버 보내기
		public void message_Members() {
			for (Client user : waitVc) {
				if (user.online == true) {
					messageAll(Function.MEMBERS + "|" + user.id + "|" + user.name + "|" + user.sex + "|" + "Message_Members");
				}
			}
		}

		// 모든 멤버 목록 리셋하기
		public void message_Reset_Members() {
			messageAll(Function.RESET_MEMBERS + "|" + "Reset_Members");
		}

		// 모든 사람에게 방목록 보내기
		public void message_Rooms() {
			String sql = ("select room_id, password, isPublic, number_Of_People from chat_room;");
			try {
				db.rs = db.stmt.executeQuery(sql);
				while (db.rs.next()) {
					room_id = db.rs.getString("room_id");
					password = db.rs.getString("password");
					isPublic = db.rs.getString("isPublic");
					number_Of_People = db.rs.getString("number_Of_People");

					messageAll(Function.ROOMS + "|" + room_id + "|" + isPublic + "|" + number_Of_People + "|" + "Message_Rooms");
				}
			} catch (SQLException e) {
				e.getMessage();
			}
			// messageAll(Function.ROOMS + "|" + user. + "|" + user.name + "|" + user.sex);

		}

		// 모든 사람 방목록 초기화하기
		public void message_Reset_Rooms() {
			messageAll(Function.RESET_ROOMS + "|" + "Reset_Rooms");
		}

		// 응답처리
		public synchronized void messageTo(String msg) {
			// synchronized
			/*
			 * 쓰레드는 default : 비동기화 synchronized => 동기화
			 */
			try {
				if (online == true) {
					out.write((msg + "\n").getBytes()); // 데이터를 1명한테만 보내는 거
				}
				// 인코딩 ==> 디코딩
			} catch (Exception e) {
			}
		}

		public synchronized void messageTo_Offline(String msg) {
			// synchronized
			/*
			 * 쓰레드는 default : 비동기화 synchronized => 동기화
			 */
			try {
				out.write((msg + "\n").getBytes()); // 데이터를 1명한테만 보내는 거
				// 인코딩 ==> 디코딩
			} catch (Exception e) {
			}
		}

		// 전체적으로 응답
		public synchronized void messageAll(String msg) {
			try {
				for (Client user : waitVc) {
					user.messageTo(msg);
				}
			} catch (Exception e) {
			}
		}
	}

}
