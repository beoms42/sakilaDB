package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import util.DBUtil;

public class RentalDao {
	public List<Map<String, Object>> selectRentalList(int storeId, String customerName, String beginDate, String endDate, int beginRow,int rowPerPage) throws Exception {
		//최상위 어레이리스트 list
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 그밑에 해시맵 널 : 어레이리스트에 쓸때마다 추가해서 담아야함.. 기억 이거때매 고생함
		HashMap<String, Object> map = null;
		
		 /* SQL 조건 
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
		//기본 sql
		sql += "SELECT rental_id rentalId, rental_date, inventory_id inventoryId, customer_id customerId, return_date returnDate, staff_id staffId, last_update lastUpdate, cName, storeId, filmId, title from rental_customer";
		//와일드카드로 변수하나 제거 > cname 분기 X 무조건 포함
		sql += " WHERE cName LIKE ?";
		
		// 비디오 값이 안들어왔을때
		if(storeId == -1) {
			
		} else {
			sql += " AND storeId = ?";
		}
		
		// 이 친구는 값이 안들어올수도 있으니까 분기
		// sql += "AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')"; << 이거사용
		if(!beginDate.equals("") && !endDate.equals("")) { // 둘다 값이 들어왔을경우에
			sql += " AND rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
		}
		
		if(!beginDate.equals("") && endDate.equals("")) { // 1번만 값이 들어올때
			sql += " AND rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE('2025-12-12','%Y-%m-%d')";
		}
		
		if(beginDate.equals("") && !endDate.equals("")) { // 2번만 값이 들어올때
			sql += " AND rental_date BETWEEN STR_TO_DATE('1990-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
		}
		
		// 마지막에 추가할 stmt
		sql += " ORDER BY rentalId LIMIT ?, ? ";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%"+customerName+"%");
		System.out.println(sql);
		// 비디오 비선택 , 날짜 비선택
		if(storeId == -1 && beginDate.equals("") && endDate.equals("")) { // 비디오 비선택, 날짜 12 비선택
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
		} else if (storeId == -1 && !beginDate.equals("") && !endDate.equals("")){ //비디오 비선택, 날자 12 선택
			stmt.setString(2, beginDate);
			stmt.setString(3, endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		} else if (storeId == -1 && !beginDate.equals("") && endDate.equals("")){ //비디오 비선택, 날자 1 선택 2비선택
			stmt.setString(2, beginDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		} else if (storeId == -1 && beginDate.equals("") && !endDate.equals("")){ //비디오 비선택, 날자 2 선택 1비선택
			stmt.setString(2, endDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		} else if (storeId != -1  && !beginDate.equals("") && !endDate.equals("")){   //비디오 선택, 날짜 1 2선택
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setString(4, endDate);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
		}  else if (storeId != -1  && !beginDate.equals("") && endDate.equals("")){   //비디오 선택, 날짜 1선택 2비선택
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}  else if (storeId != -1  && beginDate.equals("") && !endDate.equals("")){   //비디오 선택, 날짜 2선택 1비선택
			stmt.setInt(2, storeId);
			stmt.setString(3, endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}  else if (storeId != -1  && beginDate.equals("") && endDate.equals("")){   //비디오 선택, 날짜 둘다 비선택
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
