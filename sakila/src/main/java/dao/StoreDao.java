package dao;
import java.util.*;

import util.DBUtil;

import java.sql.*;
public class StoreDao {
	
	public List<Integer> selectStoreIdList() throws Exception {
		
		List<Integer> list = new ArrayList<Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "select store_id storeId from store";
		
		stmt = conn.prepareStatement(sql);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			list.add(rs.getInt("storeId"));
		}
		return list;	
	}
	
	public List<Map<String, Object>> selectStoreList() {
		List<Map<String, Object>> list = new ArrayList<>(); // 다형성
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DBUtil.getConnection();
			
			/*
				SELECT 
					s1.store_id storeId,
					s1.manager_staff_id staffId, 
					concat(s2.first_name,' ',s2.last_name) staffName,
					s1.address_id addressId,
					CONCAT(a.address, IFNULL(a.address2, ' '), district) staffAddress,
					s1.last_update lastUpdate
				FROM store s1 
					INNER JOIN staff s2
					INNER JOIN address a
				ON s1.manager_staff_id = s2.staff_id 
				AND s1.address_id = a.address_id
			 */
			String sql = "SELECT"
					+ "		s1.store_id storeId,"
					+ "		s1.manager_staff_id staffId,"
					+ "		concat(s2.first_name,' ',s2.last_name) staffName,"
					+ "		s1.address_id addressId,"
					+ "		CONCAT(a.address, IFNULL(a.address2, ' '), district) staffAddress,"
					+ "		s1.last_update lastUpdate"
					+ " FROM store s1"
					+ " INNER JOIN staff s2"
					+ " INNER JOIN address a"
					+ " ON s1.manager_staff_id = s2.staff_id"
					+ " AND s1.address_id = a.address_id;";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>(); // 다형성
				map.put("storeId", rs.getInt("storeId"));
				map.put("staffId", rs.getInt("staffId"));
				map.put("staffName", rs.getString("staffName"));
				map.put("addressId", rs.getInt("addressId"));
				map.put("staffAddress", rs.getString("staffAddress"));
				map.put("lastUpdate", rs.getString("lastUpdate"));
				list.add(map);
			}
		} catch (Exception e) { // ClassNotFoundException, SQLException두개의 예외를 부모타입 Exception으로 처리 -> 다형성
			e.printStackTrace();
			System.out.println("예외발생");
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
}