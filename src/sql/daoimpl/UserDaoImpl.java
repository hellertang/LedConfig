package sql.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import sql.dao.UserDao;
import sql.entity.User;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(Connection conn, User user) throws SQLException {
		String sql = "INSERT INTO User(name,ip,port,size,method,state) VALUES(?,?,?,?,?,?)";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, user.getName());
		ps.setString(2, user.getIp());
		ps.setString(3, user.getPort());
		ps.setString(4, user.getSize());
		ps.setString(5, user.getMethod());
		ps.setString(6, user.getState());
		ps.execute();
	}

	@Override
	public void delete(Connection conn, User user) throws SQLException {
		String sql = "DELETE FROM User WHERE id=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setLong(1, user.getId());
		// System.out.println("==========================================================================================================>>>>>>");
		ps.execute();
	}

	@Override
	public ResultSet get(Connection conn) throws SQLException {
		String sql = "SELECT * FROM User ";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	@Override
	public ResultSet getName(Connection conn) throws SQLException {
		String sql = "SELECT distinct name FROM User ";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	@Override
	public ResultSet getPort(Connection conn, String name) throws SQLException {
		String sql = "SELECT name,ip,port FROM User WHERE name=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		return rs;

	}

}
