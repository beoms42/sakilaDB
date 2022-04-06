package dao;

import vo.*;

import java.sql.*;
import java.util.*;

import util.DBUtil;

public class CategoryDao{
	public List<Category> selectCategoryList() throws Exception{
		List<Category> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		String sql = "SELECT category_id categoryId, name FROM category";
		
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			Category c = new Category();
			c.setCategoryId(rs.getInt("categoryId"));
			c.setName(rs.getString("name"));
			
			list.add(c);
		}
		rs.close();
		stmt.close();
		conn.close();
		return list;
	}
}
