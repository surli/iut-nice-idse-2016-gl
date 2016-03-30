package fr.unice.idse.db.dao;

import com.mysql.jdbc.Connection;

import fr.unice.idse.db.dao.object.UserObject;

public class UserDAO extends DAO<UserObject> {
	public UserDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(UserObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(UserObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(UserObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserObject selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserObject find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
