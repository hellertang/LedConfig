package sql.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import sql.dao.TxtDao;
import sql.entity.Txt;

public class TxtDaoImpl implements TxtDao {

	@Override
	public void save(Connection conn, Txt txt) throws SQLException {
		String sql = "INSERT INTO txtsession(userName,filename,filepath) VALAUES(?,?,?)";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, txt.getUserName());
		ps.setString(2, txt.getFilename());
		ps.setString(3, txt.getFilepath());
		ps.execute();
	}

	@Override
	public void delete(Connection conn, Txt txt) throws SQLException {
		String sql = "Delete from txtsession Where userName=? ";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, txt.getUserName());
		ps.execute();
	}

	@Override
	public ResultSet get(Connection conn) throws SQLException {
		String sql = "SELECT * FROM txtSession";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
