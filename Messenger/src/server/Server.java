package server;

//입출력 (서버와 클라이언트 통신)
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//다른 컴퓨터와 연결
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import function.Function;

public class Server implements Runnable {
	private ServerSocket ss;
	private final int PORT = 1120;
	private Vector<Client> waitVc = new Vector<Client>();
	Database db = null;
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
					StringTokenizer st = new StringTokenizer(msg, "|");
					int protocol = Integer.parseInt(st.nextToken());
					// function|id|대화명|성별

					switch (protocol) {
					// LogIn|Id|password
					case (Function.LOGIN): {
						// 로그인처리
						// 데이터 값 받기
						id = st.nextToken();
						password = st.nextToken();
						if (db.LogIn(id, password)) {
							name = db.getName(id);
							sex = db.getSex(id);
							// 접속한 모든 사용자 => 로그인한 정보를 보내준다
							messageAll(Function.ANOTHER_LOGIN + "|" + id + "|" + name + "|" + sex);
							// 접속한 사람의 정보를 저장
							waitVc.add(this);
							// 로그인창에서 => 대기창으로 변경
							messageTo(Function.PERMIT_LOGIN + "|" + id);
							// 회원 목록 추가
							for (Client user : waitVc) {
								System.out.println(Function.ANOTHER_LOGIN + "|" + user.id + "|" + user.name + "|" + user.sex);
								messageTo(Function.ANOTHER_LOGIN + "|" + user.id + "|" + user.name + "|" + user.sex);
							}
							// 로그인창에서 => 대기창으로 변경
							System.out.println(id + "로그인");
						} else {
							// 로그인 거절
							messageTo(Function.REJECT_LOGIN + "|" + id);
						}
					}
						break;
						
					case (Function.SIGNUP): {
						id = st.nextToken();
						password = st.nextToken();
						name = st.nextToken();
						sex = st.nextToken();
						// 중복 ID 확인
						if (db.SignUp(id)) {
							db.insert_client(id, password, name, sex);
							// 회원가입 성공!
							messageTo(Function.PERMIT_SIGNUP + "|" + id);

							System.out.println(id + "회원가입");
						} else {
							// 회원가입 거절
							messageTo(Function.REJECT_SIGNUP + "|" + id);
						}
					}
					
					case Function.WAITCHAT: {
						messageAll(Function.WAITCHAT + "|[" + name + "] " + st.nextToken());
					}
					}
				}
			} catch (Exception e) {
				
			}

		}

		// 응답처리
		public synchronized void messageTo(String msg) {
			// synchronized
			/*
			 * 쓰레드는 default : 비동기화 synchronized => 동기화
			 */
			try {
				out.write((msg + "\n").getBytes()); // 데이터를 1명한테만 보내는 거
				// 인코딩 ==> 디코딩
			} catch (Exception ex) {
			}
		}

		// 전체적으로 응답
		public synchronized void messageAll(String msg) {
			try {
				for (Client user : waitVc) {
					user.messageTo(msg);
				}
			} catch (Exception ex) {
			}
		}
	}

}
