package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.DatabaseUtil;

public class UserDAO {
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();//조회
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;//로그인 성공
				}
				else {
					return 0;//비밀번호 불일치
				}
			}
			return -1;//아이디가 없음
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -2;//데이터베이스 오류
	}
	
	public int join(UserDTO user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, false, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserEmailHash());
			pstmt.setString(5, user.getUserCountry());
			pstmt.setString(6, user.getFind_userID());
			return pstmt.executeUpdate();//성공하면 0이상의 숫자가 반드시 반환. insert, delete이런거
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//회원가입 실패
	}
	
	public String emailOverlap(String userEmail) {
		String SQL = "SELECT userEmail FROM USER WHERE userEmail = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return null;//데이터베이스 오류
	}
	
	public String getUserEmail(String userID) {
		String SQL = "SELECT userEmail FROM USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return null;//데이터 베이스오류
	}
	
	
	public boolean getUserEmailChecked(String userID) {
		String SQL = "SELECT userEmailChecked FROM USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getBoolean(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		
		return false;//데이터 베이스오류
	}
	
	public boolean setUserEmailChecked(String userID) {
		String SQL = "UPDATE USER SET userEmailChecked = true WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return false;//데이터 베이스오류
	}
	public String findUserID(String userEmail, String userCountry) {//아이디 찾는 함수
		String SQL = "SELECT userID FROM USER WHERE userEmail = ? and userCountry = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, userCountry);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return null;//데이터 베이스오류
	}
	
	public String findUserPassword(String Find_userID, String userEmail, String userCountry) {//아이디 찾는 함수
		String SQL = "SELECT userPassword FROM USER WHERE Find_userID = ? and userEmail = ? and userCountry = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, Find_userID);
			pstmt.setString(2, userEmail);
			pstmt.setString(3, userCountry);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return null;//데이터 베이스오류
	}


}
