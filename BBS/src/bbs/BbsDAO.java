package bbs;

import util.DatabaseUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import evaluation.EvaluationDTO;

public class BbsDAO {//jdk�� (��ȣ����)cafe 24���� �����ϴ� jdk���� ������ ������ ������ �� �� �־ �ٿ�׷����� ������� properties->java facets -> java 1.7�� ���߱�
	public String getDate() {
		String SQL = "SELECT NOW()";//���� �ð� ������� ��
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
		return "";//�����ͺ��̽� ����
	}
	
	public int getNext() {//�� ��ȣ �ο� �ϴ� ��
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;//���������� �� ��ȣ�� 1�� ���ؾ� �߰��� �Խñ��� ��ȣ�� �ȴ�.
			}
			return 1;//���簡 ù��° �Խñ�
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
		return -1;//�����ͺ��̽� ����
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
		return -1;//�����ͺ��̽� ����
	}

	public ArrayList<BbsDTO> getList(int pageNumber){//�� ������ �۸���� ��������
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";//�����ϰ� 10�� ���� �������
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BbsDTO> list = new ArrayList<BbsDTO>();//bbs ���� ����Ʈ
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//getNext�� ������ �� ��ȣ
			rs = pstmt.executeQuery();
			while(rs.next()) {//����� ���ö�����
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
	
	public boolean nextPage(int pageNumber) {//10���� ������ ������, ����¡ó��
		String SQL = "SELECT * FROM BBS WHERE bbsID < ?";//�����ϰ� 10�� ���� �������
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);//getNext�� ������ �� ��ȣ
			rs = pstmt.executeQuery();
			if(rs.next()) {//�ϳ��� ������ ������ �߰�
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
			pstmt.setInt(1, bbsID);//getNext�� ������ �� ��ȣ
			rs = pstmt.executeQuery();
			if(rs.next()) {//�ϳ��� ������ ������ �߰�
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
			return pstmt.executeUpdate();//0�̻��� ��
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
		return -1;//�����ͺ��̽� ����
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
			return pstmt.executeUpdate();//0�̻��� ��
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//�����ͺ��̽� ����
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
			return pstmt.executeUpdate();//0�̻��� ��
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();} catch(Exception e) {e.printStackTrace();}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {e.printStackTrace();}	
			try {if(rs != null) rs.close();} catch(Exception e) { e.printStackTrace();	}
		}
		return -1;//�����ͺ��̽� ����
	}
	
}
