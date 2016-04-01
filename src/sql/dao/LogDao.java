package sql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.entity.Log;

import com.mysql.jdbc.Connection;

public interface LogDao {
	public void save(Connection conn,Log logger) throws SQLException;
	
	public void delete(Connection conn,Log logger) throws SQLException;
	
	public ResultSet get(Connection conn) throws SQLException;

}
