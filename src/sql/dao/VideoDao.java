package sql.dao;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import java.sql.ResultSet;

import sql.entity.Video;

public interface VideoDao {
	public void save(Connection conn, Video video) throws SQLException;

	public void delete(Connection conn, Video video) throws SQLException;

	public ResultSet get(Connection conn) throws SQLException;
}
