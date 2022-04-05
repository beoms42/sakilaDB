package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.DBUtil;

public class RewordDao {
	public Map<String, Object> rewordsReport(int min_monthly_purchases, int min_dollar_amount_purchased) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = null;
		// PreparedStatement : ������ ����
		// CallableStatement : ���ν����� ���� 
		CallableStatement stmt = null;
		ResultSet rs = null;
		// ���� ū �� �� ��̸���Ʈ �� �ؽø�
		HashMap<String, Object> maps = null;
		//���� �ؽø��� ������ ��̸���Ʈ 
		//������ :  Map<String, Object> �� ArrayList<HashMap<String, Object>> �� HashMap<String, Object> maps
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		// select count(inventroy_id) ....
		Integer count = 0;
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{call rewards_report(?, ?, ?)}");
			stmt.setInt(1, min_monthly_purchases);
			stmt.setInt(2, min_dollar_amount_purchased);
			stmt.registerOutParameter(3, Types.INTEGER);
			rs = stmt.executeQuery();
			while(rs.next()) {
				maps = new HashMap<String, Object>();
				
				/*
				maps.put("customerId", rs.getInt("1"));
				maps.put("storeId", rs.getInt("2"));
				maps.put("firstName", rs.getString("3"));
				maps.put("lastName", rs.getString("4"));
				maps.put("email", rs.getString("5"));
				maps.put("addressId", rs.getString("6"));
				maps.put("active", rs.getString("7"));
				maps.put("createDate", rs.getString("8"));
				maps.put("lastUpdate", rs.getString("9"));
				*/
				

				maps.put("customerId", rs.getInt("customer_id"));
				System.out.println(rs.getInt("customer_id") + "<---------customerId?");
				maps.put("storeId", rs.getInt("store_id"));
				System.out.println(rs.getInt("store_id") + "<---------store_id?");
				maps.put("firstName", rs.getString("first_name"));
				maps.put("lastName", rs.getString("last_name"));
				maps.put("email", rs.getString("email"));
				maps.put("addressId", rs.getString("address_id"));
				maps.put("active", rs.getString("active"));
				maps.put("createDate", rs.getString("create_date"));
				maps.put("lastUpdate", rs.getString("last_update"));

				list.add(maps);
			}
			count = stmt.getInt(3); // ���ν��� 3��° out���� �� : count_rewardees
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println(list + "<---------����Ʈ?");
		}
		
		map.put("list", list);
		map.put("count", count);
		return map;
	}
}
