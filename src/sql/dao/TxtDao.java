package sql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.entity.Txt;

import com.mysql.jdbc.Connection;

public interface TxtDao {
	public void save(Connection conn ,Txt txt) throws SQLException;
	
	public void delete(Connection conn ,Txt txt) throws SQLException;
	
	public ResultSet get(Connection conn) throws SQLException;

}
