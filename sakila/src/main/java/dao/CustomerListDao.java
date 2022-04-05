package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;
import vo.*;

public class CustomerListDao {
	public List<CustomerList> selectCustomerInfoListByPage(int beginRow, int rowPerPage) {
		List<CustomerList> list = new ArrayList<CustomerList>();
		Connection conn = null;
		CustomerList c = null;
		
		// getConnection�� static���� ��⶧���� ��ü���� ����� �����ϴ�.
		conn = DBUtil.getConnection();
		
		/*
		 * sql��
			
			SELECT
			id,	
			NAME,
			address,
			zip_code zipCode,
			phone,
			city,
			country,
			notes,
			sid
			FROM customer_list;
		 */
		String sql = "SELECT id, NAME, address, zip_code zipCode, phone, city, country, notes, sid FROM customer_list ORDER BY id LIMIT ?, ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			
			 while(rs.next()) {
				 c = new CustomerList();
	                
	                c.setId(rs.getInt("id"));
	                c.setName(rs.getString("NAME"));
	                c.setAddress(rs.getString("address"));
	                c.setZipCode(rs.getString("zipCode"));
	                c.setPhone(rs.getString("phone"));
	                c.setCity(rs.getString("city"));
	                c.setCountry(rs.getString("country"));
	                c.setNotes(rs.getString("notes"));
	                
	                list.add(c);
	            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); stmt.close(); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(list);
		return list;
	}
}
