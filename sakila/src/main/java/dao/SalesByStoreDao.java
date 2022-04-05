package dao;
import java.util.*;
import java.sql.*;
import vo.*;
import util.DBUtil;
import java.sql.*;

public class SalesByStoreDao {
	// 페이지 설정 및 목록 나오게 하기
	public ArrayList<SalesByStore> selectSalesByStoreListByPage(int beginRow, int rowPerPage) {
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection(); 		// getConnection을 static으로 썼기때문에 객체없이 사용이 가능하다.
		String sql = "SELECT store, manager ,total_sales totalSales  FROM sales_by_store ORDER BY store LIMIT ?, ?";
		ArrayList<SalesByStore> list = new ArrayList<SalesByStore>();		//다형성
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectSalesByFilmCategoryListByPage : " + stmt);	//디버깅
			// ?값
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터변환(가공)
			while(rs.next()) {
				SalesByStore s = new  SalesByStore();
				s.setStore(rs.getString("store"));
				s.setManager(rs.getString("manager"));
				s.setTotalSales(rs.getLong("totalSales"));
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
	public int selectSalesByStoreListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM sales_by_store";
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectSalesByStoreTotalRow : " + stmt);	//디버깅
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
