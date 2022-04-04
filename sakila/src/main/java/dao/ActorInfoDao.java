package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.ActorInfo;

public class ActorInfoDao {
	public List<ActorInfo> selectActorInfoListByPage(int beginRow, int rowPerPage) {
		List<ActorInfo> list = new ArrayList<ActorInfo>();
		Connection conn = null;
		
		// getConnection을 static으로 썼기때문에 객체없이 사용이 가능하다.
		conn = DBUtil.getConnection();
		
		/*
		 * sql문
			SELECT 
				actor_id actorId,
				first_name firstName,
				last_name lastName,
				film_info filmInfo
			FROM actor_info;
		 */
		String sql = "SELECT actor_id actorId, first_name firstName, last_name lastName, film_info filmInfo FROM actor_info ORDER BY actor_id LIMIT ?, ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			
			 while(rs.next()) {
	                ActorInfo a = new ActorInfo();
	                
	                a.setActorId(rs.getInt("actorId"));
	                a.setFirstName(rs.getString("firstName"));
	                a.setLastName(rs.getString("lastName"));
	                a.setFilmInfo(rs.getString("filmInfo"));
	                list.add(a);
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
}