package sql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.entity.Filer;

import com.mysql.jdbc.Connection;

public interface FileDao {
	public void save(Connection conn, Filer file) throws SQLException;

	public void delete(Connection conn, Filer file) throws SQLException;

	public ResultSet get(Connection conn) throws SQLException;
}
