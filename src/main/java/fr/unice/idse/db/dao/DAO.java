package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
	protected Connection conn;

	public abstract boolean create(T obj);

	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);

	public abstract T find(int id);
}
