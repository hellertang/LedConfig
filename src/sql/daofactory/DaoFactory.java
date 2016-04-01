package sql.daofactory;

import sql.dao.FileDao;
import sql.dao.LogDao;
import sql.dao.TxtDao;
import sql.dao.UserDao;
import sql.dao.VideoDao;
import sql.daoimpl.FileDaoImpl;
import sql.daoimpl.LogDaoImpl;
import sql.daoimpl.TxtDaoImpl;
import sql.daoimpl.UserDaoImpl;
import sql.daoimpl.VideoDaoImpl;

public class DaoFactory {
	public static UserDao getUserDAOInstance() {
		return new UserDaoImpl();
	}

	public static FileDao getFileDAOInstance() {
		return new FileDaoImpl();
	}

	public static LogDao getLogDAOInstance() {
		return new LogDaoImpl();
	}

	public static TxtDao getTxtDAOInstance() {
		return new TxtDaoImpl();
	}

	public static VideoDao getVideoDaoInstance(){
		return new VideoDaoImpl();
		
	}
}
