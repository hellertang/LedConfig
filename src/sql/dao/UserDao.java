package sql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.entity.User;
import com.mysql.jdbc.Connection;

public interface UserDao {
	public void save(Connection conn, User user) throws SQLException;

	public void delete(Connection conn, User user) throws SQLException;

	public ResultSet get(Connection conn) throws SQLException;

	public ResultSet getName(Connection conn) throws SQLException;

	public ResultSet getPort(Connection conn, String name) throws SQLException;

}
