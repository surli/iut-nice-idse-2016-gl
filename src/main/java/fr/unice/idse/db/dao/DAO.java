package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {
	protected Connection conn;
	
	public abstract boolean create(T obj) throws SQLException;

	public abstract boolean delete(T obj) throws SQLException;
	
	public abstract boolean update(T obj) throws SQLException;

	public abstract T find(int id);
}
