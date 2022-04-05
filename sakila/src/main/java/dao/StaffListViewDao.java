package dao;
import java.util.*;
import java.sql.*;
import vo.*;
import util.DBUtil;
import java.sql.*;

public class StaffListViewDao {

	// 페이지 설정 및 목록 나오게 하기
	public ArrayList<StaffListView> selectStaffListViewListByPage(int beginRow, int rowPerPage) {
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection(); 		// getConnection을 static으로 썼기때문에 객체없이 사용이 가능하다.
		String sql = "SELECT ID, name, address, zip_code zipCode, phone, city, country, SID sid  FROM staff_list ORDER BY ID LIMIT ?, ?";
		ArrayList<StaffListView> list = new ArrayList<StaffListView>();		//다형성
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectStaffListViewListByPage : " + stmt);	//디버깅
			// ?값
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터변환(가공)
			while(rs.next()) {
				StaffListView s = new  StaffListView();
				s.setId(rs.getInt("ID"));
				s.setName(rs.getString("name"));
				s.setAddress(rs.getString("address"));
				s.setZipCode(rs.getString("zipCode"));
				s.setPhone(rs.getLong("phone"));
				s.setCity(rs.getString("city"));
				s.setCountry(rs.getString("country"));
				s.setSid(rs.getInt("sid"));
				list.add(s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("예외발생");
		} finally {
			try {
				rs.close(); stmt.close(); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	// 총합 구하기 및 라스트 페이지 설정
	public int selectStaffListViewListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM staff_list";
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectStaffListViewTotalRow : " + stmt);	//디버깅
			rs = stmt.executeQuery();
			if(rs.next()) {
			row = rs.getInt("cnt");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("예외발생");
		}finally {
			try {
				rs.close(); stmt.close(); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
}
