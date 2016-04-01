package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	
	private static final ConnectionFactory factory=new ConnectionFactory();
	private static Connection conn=null;
	
	static{
		Properties prop=new Properties();
		try {
			InputStream in=ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("dbConfig.properties");
			prop.load(in);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("≈‰÷√–≈œ¢¥ÌŒÛ");
		}
		driver=prop.getProperty("driver");
		dburl=prop.getProperty("dburl");
		user=prop.getProperty("user");
		password=prop.getProperty("password");
	}
	 private ConnectionFactory(){
		 
	 }
	 public static ConnectionFactory getInstance(){
		 return factory;
	 }
	 public Connection makeConnection(){
		 try {
			Class.forName(driver);
			conn=(Connection) DriverManager.getConnection(dburl,user,password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return conn;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
