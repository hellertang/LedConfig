package sql.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import sql.dao.FileDao;
import sql.entity.Filer;

public class FileDaoImpl implements FileDao {

	@Override
	public void save(Connection conn, Filer file) throws SQLException {
		String sql="INSERT INTO filesession(filename,filepath) VALUES(?,?)";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, file.getFilename());
		ps.setString(2, file.getFilepath());
		ps.execute();
		
	}

	@Override
	public void delete(Connection conn, Filer file) throws SQLException {
		String sql = "Delete from fileSession Where userName=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, file.getUserName());
		ps.execute();
		
	}

	@Override
	public ResultSet get(Connection conn) throws SQLException {
		String sql="SELECT * FROM FileSession";
		PreparedStatement ps=(PreparedStatement) conn.prepareCall(sql);
		ResultSet rs=ps.executeQuery();
		return rs;
		
	}

}
