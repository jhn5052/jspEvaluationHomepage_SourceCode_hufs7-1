package bbs;

import util.DatabaseUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import evaluation.EvaluationDTO;

public class BbsDAO {//jdk가 (웹호스팅)cafe 24에서 제공하는 jdk보다 버전이 높을때 오류가 날 수 있어서 다운그래에드 해줘야함 properties->java facets -> java 1.7로 낮추기
	public String getDate() {
		String SQL = "SELECT NOW()";//현재 시간 갖고오는 것
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return "";//데이터베이스 오류
	}
	
	public int getNext() {//글 번호 부여 하는 것
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;//마지막으로 쓴 번호에 1을 더해야 추가된 게시글의 번호가 된다.
			}
			return 1;//현재가 첫번째 게시글
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return -1;//데이터베이스 오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return -1;//데이터베이스 오류
	}

	public ArrayList<BbsDTO> getList(int pageNumber){//총 열개의 글목록이 한페이지
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";//정렬하고 10개 숫자 갖고오기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BbsDTO> list = new ArrayList<BbsDTO>();//bbs 담을 리스트
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//getNext는 다음에 쓸 번호
			rs = pstmt.executeQuery();
			while(rs.next()) {//결과가 나올때마다
				BbsDTO bbs = new BbsDTO();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {//10개로 끊기지 않을때, 페이징처리
		String SQL = "SELECT * FROM BBS WHERE bbsID < ?";//정렬하고 10개 숫자 갖고오기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//getNext는 다음에 쓸 번호
			rs = pstmt.executeQuery();
			if(rs.next()) {//하나라도 있으면 페이지 추가
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return false;
	}
	
	public BbsDTO getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);//getNext는 다음에 쓸 번호
			rs = pstmt.executeQuery();
			if(rs.next()) {//하나라도 있으면 페이지 추가
				BbsDTO bbs = new BbsDTO();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();//0이상의 값
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); }
			catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) {pstmt.close();}
			}catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) {rs.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return -1;//데이터베이스 오류
	}
	
	public int changeBbsID(int bbsID) {
		String SQL = "UPDATE BBS SET bbsID = bbsID-1 WHERE bbsID > ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();//0이상의 값
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//데이터베이스 오류
	}
	
	public int delete(int bbsID) {
		String SQL = "DELETE FROM BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();//0이상의 값
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//데이터베이스 오류
	}
	
}
