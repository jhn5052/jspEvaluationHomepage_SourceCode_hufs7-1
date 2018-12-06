package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class EvaluationDAO {
	public int write(EvaluationDTO evaluationDTO) {
		String SQL = "INSERT INTO EVALUATION VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, evaluationDTO.getUserID().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, evaluationDTO.getLectureName().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, evaluationDTO.getProfessorName().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5, evaluationDTO.getSemesterDivide().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(6, evaluationDTO.getLectureDivide().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(7, evaluationDTO.getEvaluationTitle().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(8, evaluationDTO.getEvaluationContent().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(9, evaluationDTO.getTotalScore().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(10, evaluationDTO.getComfortableScore().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(11, evaluationDTO.getMidterm().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(12, evaluationDTO.getFinalterm().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(13, evaluationDTO.getHomework().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(14, evaluationDTO.getCountry().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			pstmt.setString(15, evaluationDTO.getSchool().replace("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));;
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//데이터베이스 오류
	}
	
	public ArrayList<EvaluationDTO> getList (String lectureDivide, String searchType, String search, int pageNumber){
		//로고쪽에 검색창 기능 구현 -> BBS에도 추가 한페이지당 다섯개씩 출력
		if(lectureDivide.equals("전체")) {
			lectureDivide = "";
		}
		ArrayList<EvaluationDTO> evaluationList = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if (searchType.equals("최신순")) {
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(country, school, lectureName, professorName, evaluationTitle, evaluationContent) LIKE " +
						"? ORDER BY evaluationID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			} else if (searchType.equals("추천순")){
				SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(country, school, lectureName, professorName, evaluationTitle, evaluationContent) LIKE " +
						"? ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			}
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + lectureDivide + "%");//%는 SQL 문법 like가 포함된 것 찾는 것
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();//조회
			evaluationList = new ArrayList<EvaluationDTO>();
			while(rs.next()) {
				EvaluationDTO evaluation = new EvaluationDTO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getString(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(17)
				);//모든 결과가 출력되게
				evaluationList.add(evaluation);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return evaluationList;//EvaluationDTO list에 담긴 정보들을 반환
	}	
	
	public int like(String evaluationID) {
		String SQL = "UPDATE EVALUATION SET likeCount = likeCount + 1 WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//데이터 베이스오류
	}
	
	public int delete(String evaluationID) {
		String SQL = "DELETE FROM EVALUATION WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//데이터 베이스오류
	}
	
	public String getUserID(String evaluationID) {
		String SQL = "SELECT userID FROM EVALUATION WHERE evaluationID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(evaluationID));
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
		return null;//존재하지 않는 경우
	}
}
