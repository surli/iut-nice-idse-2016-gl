package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.unice.idse.db.dao.object.PlayerObject;

// TODO : Rethink about the architecture of the objects
// TODO : Search about DAO framework
// TODO : Speack with the db team (why Stack need match and turn id)
// TODO : Add more abstract methods?
// TODO : Too much copy-past between each DAO if need special method then rework mock test

/**
 * Abstract class for the DAO with the basic CRUD method Create, Read, Update, Delete
 * @param <T>
 */
public abstract class DAO<T> {
	protected Connection conn;
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public abstract boolean create(T obj) throws SQLException;

	public abstract boolean delete(T obj) throws SQLException;
	
	public abstract boolean update(T obj) throws SQLException;

	public abstract T find(int id) throws SQLException;

	public abstract List<PlayerObject> findsByGameId(int id) throws SQLException;

}
