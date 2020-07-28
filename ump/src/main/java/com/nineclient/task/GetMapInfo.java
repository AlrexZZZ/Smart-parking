package com.nineclient.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.PreparedStatement;
import com.nineclient.qycloud.wcc.dto.TDormbulid;
import com.nineclient.qycloud.wcc.util.DbUtil;
public class GetMapInfo {

	
	
	
	
	public static List<TDormbulid> getInfo(){
		Connection con = null;
		TDormbulid td = null;
		List<TDormbulid> list = new ArrayList<TDormbulid>();
		try {
			con =  new  DbUtil().getCon();
			String sql = "select * from t_dormbuild ";
			java.sql.PreparedStatement pstmt=con.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				td = new TDormbulid(rs.getString("dormBuildName"), rs.getString("dormBuildDetail"), rs.getDouble("lng"), rs.getDouble("lat"));
				list.add(td);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
