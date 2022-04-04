package dao;

import java.sql.Connection;
import java.util.*;
import util.DBUtil;
import vo.ActorInfo;

public class ActorInfoDao {
	
	public List<ActorInfo> selectActorListByPage(int beginRow, int rowPerPage) {
		
		Connection conn = DBUtil.getConnection();
		
		return null;
	}
	
}