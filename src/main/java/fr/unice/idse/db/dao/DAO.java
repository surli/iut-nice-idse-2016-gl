package fr.unice.idse.db.dao;

import com.mysql.jdbc.Connection;

public abstract class DAO<T> {
	protected Connection conn;

	public abstract boolean create(T obj);

	public abstract boolean delete(T obj);

	public abstract boolean update(T obj);

	public abstract T selectAll();

	public abstract T find(int id);
}
