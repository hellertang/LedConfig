package sql.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import sql.dao.LogDao;
import sql.entity.Log;

public class LogDaoImpl implements LogDao {

	@Override
	public void save(Connection conn, Log logger) throws SQLException {
		String sql = "Insert into logSession (userName ,logName) values(?,?)";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, logger.getUserName());
		ps.setString(2, logger.getLogName());
		ps.execute();
	}

	@Override
	public void delete(Connection conn, Log logger) throws SQLException {
		String sql = "Delete from logSession Where userName=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, logger.getUserName());
		ps.execute();
	}

	@Override
	public ResultSet get(Connection conn) throws SQLException {
		String sql = "SELECT * FROM Logsession";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
