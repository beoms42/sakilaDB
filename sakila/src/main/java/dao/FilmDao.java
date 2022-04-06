package dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;
import vo.FilmList;

public class FilmDao {
		public List<FilmList> selectFilmListSearch(int beginRow, int rowPerPage, String category, String rating, double price, int length, String title, String actor) {		
			List<FilmList> list = new ArrayList<FilmList>();
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection();
			try {
				// ��������
				// �⺻ ���� = category  / title / actor
				// �б� > 3���� �Ұǵ� 1, 0 / 0, 1 / 0, 0 / 1,1 4���� ����� ���� ����
				
				String sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE title LIKE ? AND actors LIKE ? AND category LIKE ?";
				if(price==-1 && length==-1 && rating.equals("")) { // category / title / actor ��ø���� ����
					sql += " ORDER BY fid LIMIT ?, ?"; // category / title / actor ÷���� �����̱⋚���� ���� �б����� �ʾƵ� �� , 1 ���ý�
					stmt = conn.prepareStatement(sql);
					System.out.println(category + "<-------------------- category");
					stmt.setString(1, "%"+title+"%"); 
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(price==-1 && length!=-1 && rating.equals("")) { // length�� �����Ұ�� ,2
					if(length == 0) {
						sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(rating.equals("") && price!=-1 && length==-1) { // price�� �����Ұ��, 3
					sql += " AND price=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(!rating.equals("") && price==-1 && length==-1) { // rating�� �����Ұ��  ,4
					sql += " AND rating=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setString(4, rating);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if (price!=-1 && length!=-1 && rating.equals("")) { // length + price ,5
					if(length == 0) {
						sql += " AND length<60 AND price=? ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND length>=60 AND price=? ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if (price==-1 && length!=-1 && !rating.equals("")) { // length + rating ,6
					if(length == 0) {
						sql += " AND length<60 AND rating=? ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND length>=60 AND rating=? ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setString(4, rating);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if (price!=-1 && length==-1 && !rating.equals("")) { // rating + price ,7
					sql += " AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setString(4, rating);
					stmt.setDouble(5, price);
					stmt.setInt(6, beginRow);
					stmt.setInt(7, rowPerPage);
					
				} else if (price!=-1 && length!=-1 && !rating.equals("")) { // rating + price + length ,8 ��� ����� �� 2 * 2 * 2 = 8
					if(length == 0) {
						sql += " AND length<60 AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND length>=60 AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, "%"+category+"%");
					stmt.setString(4, rating);
					stmt.setDouble(5, price);
					stmt.setInt(6, beginRow);
					stmt.setInt(7, rowPerPage);
				}
				
				rs = stmt.executeQuery();
				while(rs.next()) {
					FilmList f = new FilmList();
					f.setFid(rs.getInt("fid"));
					f.setTitle(rs.getString("title"));
					f.setDescription(rs.getString("description"));
					f.setCategory(rs.getString("category"));
					f.setPrice(rs.getDouble("price"));
					f.setLength(rs.getInt("length"));
					f.setRating(rs.getString("rating"));
					f.setActors(rs.getString("actors"));
					list.add(f);
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
		
	}
		
		//���� ��ü���� �ο츦 ���ϴ� �޼���
		public int totalRow(int beginRow, int rowPerPage, String category, String rating, double price, int length, String title, String actor) {
			List<FilmList> list = new ArrayList<FilmList>();
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection();
			int count = -1;
			try {
			// ��������
			// �⺻����= category  / title / actor
			// �б�> 3�����Ұǵ�1, 0 / 0, 1 / 0, 0 / 1,1 4��������Ǽ�������

			String sql = "SELECT count(*) as count FROM film_list WHERE title LIKE ? AND actors LIKE ? AND category LIKE ?";
			if(price==-1 && length==-1 && rating.equals("")) { // category / title / actor ��ø���ð���
			sql += " "; // category / title / actor ÷���Ͱ����̱⋚�������̺б������ʾƵ���, 1 ���ý�
			stmt = conn.prepareStatement(sql);
			System.out.println(category + "<-------------------- category");
			stmt.setString(1, "%"+title+"%"); 
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
			} else if(price==-1 && length!=-1 && rating.equals("")) { // length�������Ұ��,2
			if(length == 0) {
			sql += " AND length<60 ";
			} else if(length == 1) {
			sql += " AND length>=60 ";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
			} else if(rating.equals("") && price!=-1 && length==-1) { // price�������Ұ��, 3
			sql += " AND price=? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setDouble(4, price);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			} else if(!rating.equals("") && price==-1 && length==-1) { // rating�������Ұ�� ,4
			sql += " AND rating=? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setString(4, rating);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			} else if (price!=-1 && length!=-1 && rating.equals("")) { // length + price ,5
			if(length == 0) {
			sql += " AND length<60 AND price=? ";
			} else if(length == 1) {
			sql += " AND length>=60 AND price=? ";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setDouble(4, price);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			} else if (price==-1 && length!=-1 && !rating.equals("")) { // length + rating ,6
			if(length == 0) {
			sql += " AND length<60 AND rating=? ";
			} else if(length == 1) {
			sql += " AND length>=60 AND rating=? ";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setString(4, rating);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			} else if (price!=-1 && length==-1 && !rating.equals("")) { // rating + price ,7
			sql += " AND rating=? AND price=? ";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setString(4, rating);
			stmt.setDouble(5, price);
			stmt.setInt(6, beginRow);
			stmt.setInt(7, rowPerPage);

			} else if (price!=-1 && length!=-1 && !rating.equals("")) { // rating + price + length ,8 ������Ǽ�2 * 2 * 2 = 8
			if(length == 0) {
			sql += " AND length<60 AND rating=? AND price=? ";
			} else if(length == 1) {
			sql += " AND length>=60 AND rating=? AND price=? ";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+title+"%");
			stmt.setString(2, "%"+actor+"%");
			stmt.setString(3, "%"+category+"%");
			stmt.setString(4, rating);
			stmt.setDouble(5, price);
			stmt.setInt(6, beginRow);
			stmt.setInt(7, rowPerPage);
			}

			rs = stmt.executeQuery();
			while(rs.next()) {
			count = (rs.getInt("count"));
			}
			} catch(SQLException e) {
			e.printStackTrace();
			}
				return count;
			}
		
		
	public List<Double> selectFilmPriceDistinctList() {
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection(); 		
		String sql = "SELECT DISTINCT price FROM film_list ORDER BY price";
		ArrayList<Double> list = new ArrayList<Double>();	
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectFilmListListByPage : " + stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getDouble("price"));
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
		return list;
	}
	public Map<String, Object> filmInStockCall(int filmId, int storeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = null;
		// PreparedStatement : ������ ����
		// CallableStatement : ���ν����� ���� 
		CallableStatement stmt = null;
		ResultSet rs = null;
		// select inventory_id .... 
		List<Integer> list = new ArrayList<>();
		// select count(inventroy_id) ....
		Integer count = 0;
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{call film_in_stock(?, ?, ?)}");
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
			stmt.registerOutParameter(3, Types.INTEGER);
			rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); // rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); // ���ν��� 3��° out���� ��
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
	public Map<String, Object> filmNotInStockCall(int filmId, int storeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = null;
		// PreparedStatement : ������ ����
		// CallableStatement : ���ν����� ���� 
		CallableStatement stmt = null;
		ResultSet rs = null;
		// select inventory_id .... 
		List<Integer> list = new ArrayList<>();
		// select count(inventroy_id) ....
		Integer count = 0;
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{call film_not_in_stock(?, ?, ?)}");
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
			stmt.registerOutParameter(3, Types.INTEGER);
			rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt("inventory_id")); // rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); // ���ν��� 3��° out���� ��
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
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