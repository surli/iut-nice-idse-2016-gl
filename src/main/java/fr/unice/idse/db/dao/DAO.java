package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {
	protected Connection conn;

	protected void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public abstract boolean create(T obj) throws SQLException;

	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);

	public abstract T find(int id);
}
