package dao;
	import java.util.*;
	import java.sql.*;
	import vo.*;
	import util.DBUtil;
	import java.sql.*;
	public class FilmListDao {

		// 페이지 설정 및 목록 나오게 하기
		public ArrayList<FilmList> selectFilmListListByPage(int beginRow, int rowPerPage) {
			Connection conn = null;
			PreparedStatement  stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection(); 		// getConnection을 static으로 썼기때문에 객체없이 사용이 가능하다.
			String sql = "SELECT FID fid, title, description, category, price, length, rating, actors  FROM film_list ORDER BY FID LIMIT ?, ?";
			ArrayList<FilmList> list = new ArrayList<FilmList>();		//다형성
			try {
				stmt = conn.prepareStatement(sql);
				System.out.println("sql selectFilmListListByPage : " + stmt);	//디버깅
				// ?값
				stmt.setInt(1, beginRow);
				stmt.setInt(2, rowPerPage);
				rs = stmt.executeQuery();
				// 데이터변환(가공)
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
		public int selectFilmListTotalRow() {
			int row = 0;
			Connection conn = null;
			PreparedStatement  stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection();
			String sql = "SELECT COUNT(*) cnt FROM film_list";
			try {
				stmt = conn.prepareStatement(sql);
				System.out.println("sql selectFilmListListRow : " + stmt);	//디버깅
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