package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.HandPlayerObject;

public class HandDAO extends DAO<HandPlayerObject>{
	private Logger logger = LoggerFactory.getLogger(GameDAO.class);
	
	public HandDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HandPlayerObject find(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
