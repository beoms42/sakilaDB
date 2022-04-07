package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import util.DBUtil;

public class RentalDao {
	public List<Map<String, Object>> selectRentalList(int storeId, String customerName, String beginDate, String endDate, int beginRow,int rowPerPage) throws Exception {
		//�ֻ��� ��̸���Ʈ list
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// �׹ؿ� �ؽø� �� : ��̸���Ʈ�� �������� �߰��ؼ� ��ƾ���.. ��� �̰Ŷ��� �����
		HashMap<String, Object> map = null;
		
		 /* SQL ���� 
		    WHERE s.store_id=2 AND CONCAT(c.first_name,' ',c.last_name) LIKE '%JOEL%'
			AND r.rental_date BETWEEN STR_TO_DATE('2005-07-01','%Y-%m-%d') AND STR_TO_DATE('2005-07-30','%Y-%m-%d');
			
			** SQL
			* SELECT rental_id rentalId, rental_date rentalDate, inventory_id inventoryId, customer_id customerId, return_date returnDate, staff_id staffId, last_update lastUpdate, cName, storeId, filmId, title from rental_customer
		 */
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "";
		//�⺻ sql
		sql += "SELECT rental_id rentalId, rental_date, inventory_id inventoryId, customer_id customerId, return_date returnDate, staff_id staffId, last_update lastUpdate, cName, storeId, filmId, title from rental_customer";
		//���ϵ�ī��� �����ϳ� ���� > cname �б� X ������ ����
		sql += " WHERE cName LIKE ?";
		
		// ���� ���� �ȵ�������
		if(storeId == -1) {
			
		} else {
			sql += " AND storeId = ?";
		}
		
		// �� ģ���� ���� �ȵ��ü��� �����ϱ� �б�
		// sql += "AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')"; << �̰Ż��
		if(!beginDate.equals("") && !endDate.equals("")) { // �Ѵ� ���� ��������쿡
			sql += " AND rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
		}
		
		if(!beginDate.equals("") && endDate.equals("")) { // 1���� ���� ���ö�
			sql += " AND rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE('2025-12-12','%Y-%m-%d')";
		}
		
		if(beginDate.equals("") && !endDate.equals("")) { // 2���� ���� ���ö�
			sql += " AND rental_date BETWEEN STR_TO_DATE('1990-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
		}
		
		// �������� �߰��� stmt
		sql += " ORDER BY rentalId LIMIT ?, ? ";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%"+customerName+"%");
		System.out.println(sql);
		// ���� ���� , ��¥ ����
		if(storeId == -1 && beginDate.equals("") && endDate.equals("")) { // ���� ����, ��¥ 12 ����
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
		} else if (storeId == -1 && !beginDate.equals("") && !endDate.equals("")){ //���� ����, ���� 12 ����
			stmt.setString(2, beginDate);
			stmt.setString(3, endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		} else if (storeId == -1 && !beginDate.equals("") && endDate.equals("")){ //���� ����, ���� 1 ���� 2����
			stmt.setString(2, beginDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		} else if (storeId == -1 && beginDate.equals("") && !endDate.equals("")){ //���� ����, ���� 2 ���� 1����
			stmt.setString(2, endDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		} else if (storeId != -1  && !beginDate.equals("") && !endDate.equals("")){   //���� ����, ��¥ 1 2����
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setString(4, endDate);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
		}  else if (storeId != -1  && !beginDate.equals("") && endDate.equals("")){   //���� ����, ��¥ 1���� 2����
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}  else if (storeId != -1  && beginDate.equals("") && !endDate.equals("")){   //���� ����, ��¥ 2���� 1����
			stmt.setInt(2, storeId);
			stmt.setString(3, endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}  else if (storeId != -1  && beginDate.equals("") && endDate.equals("")){   //���� ����, ��¥ �Ѵ� ����
			stmt.setInt(2, storeId);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		} 
		
		
		
		rs = stmt.executeQuery();
	
		while(rs.next()) {
			map = new HashMap<String, Object>();
			map.put("rentalId", rs.getInt("rentalId"));
			map.put("rentalDate", rs.getString("rental_date"));
			map.put("inventoryId", rs.getInt("inventoryId"));
			map.put("customerId", rs.getInt("customerId"));
			map.put("returnDate", rs.getString("returnDate"));
			map.put("staffId", rs.getInt("staffId"));
			map.put("lastUpdate", rs.getString("lastUpdate"));
			map.put("cName", rs.getString("cName"));
			map.put("storeId", rs.getInt("storeId"));
			map.put("title", rs.getString("title"));
			
			list.add(map);
		}
		
		return list;
	}
}
