package sql.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import sql.dao.VideoDao;
import sql.entity.Video;

public class VideoDaoImpl implements VideoDao {

	@Override
	public void save(Connection conn, Video video) throws SQLException {
		String sql = "INSERT INTO videosession(userName,filename,filepath) VALAUES(?,?,?)";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, video.getUserName());
		ps.setString(2, video.getFilename());
		ps.setString(3, video.getFilepath());
		ps.execute();

	}

	@Override
	public void delete(Connection conn, Video video) throws SQLException {
		String sql = "Delete from videoSession where userName=?";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ps.setString(1, video.getUserName());
		ps.execute();

	}

	@Override
	public ResultSet get(Connection conn) throws SQLException {
		String sql = "Select * From VideoSession";
		PreparedStatement ps = (PreparedStatement) conn.prepareCall(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
