package function;

// 클라이언트 기능 설명
public class Function {
	// 회원가입
	public static final int SIGNUP = 100;
	public static final int PERMIT_SIGNUP = 101;
	public static final int REJECT_SIGNUP = 102;

	// 로그인
	public static final int LOGIN = 110;
	public static final int PERMIT_LOGIN = 111;
	public static final int REJECT_LOGIN = 112;

	// 로그아웃
	public static final int LOGOUT = 115;
	
	// 맴버 목록
	public static final int RESET_MEMBERS = 121;
	public static final int MEMBERS = 122;
	
	// 채팅방 목록기능
	public static final int ENTERROOM = 130;
	public static final int MAKEROOM = 131;
	public static final int PERMIT_MAKE_ROOM = 132;
	public static final int REJECT_MAKE_ROOM = 133;
	
	public static final int RESET_ROOMS = 135;
	public static final int ROOMS = 136;

	// 채팅방 기능
	public static final int EXITROOM = 140;
	public static final int INVITE = 141;

	// 채팅방의 범위
	// 1 <= CHATROOM <= 99
	// 대기실 = 0
	public static final int CHATTING = 300;

}
