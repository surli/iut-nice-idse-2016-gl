package fr.unice.idse.db;

import fr.unice.idse.constante.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;

public class DataBaseOrigin {
	protected Connection con = null;
	protected PreparedStatement ps = null;
	public ResultSet rs = null;
    private static DataBaseOrigin dataBaseOrigin;

	protected DataBaseOrigin() {
        if(dataBaseOrigin == null)
            connect("");
    }
    protected DataBaseOrigin(String connecteur){
        if (dataBaseOrigin == null)
            connect(connecteur);
    }

    public static DataBaseOrigin getInstance() {
        if (dataBaseOrigin == null) {
            dataBaseOrigin = new DataBaseOrigin();
        }
        return dataBaseOrigin;
    }

    public static DataBaseOrigin getInstance(String connecteur) {
        if (dataBaseOrigin == null) {
            dataBaseOrigin = new DataBaseOrigin(connecteur);
        }
        return dataBaseOrigin;
    }

	private void connect(String connector){
		rs = null;
		ps = null;
		con = null;
		try {
			switch (connector){
				case "sqlite":
					Class.forName("org.sqlite.JDBC");
                    System.out.println("");
                    System.out.println("SQLITE");
                    System.out.println("");
					con = DriverManager.getConnection("jdbc:sqlite:uno.db");
                    break;
				default :
					Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("");
                    System.out.println("MYSQL");
                    System.out.println("");
					con = DriverManager.getConnection(Config.url, Config.user, Config.pass);
					break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert the data and return is key
	 * 
	 * @param query
	 *            An Insert query string
	 * @return int The id of the insered query
	 */
	public int insert(String query) {
		int key = -1;
		try {
            System.out.println(">> "+query);
			Statement statement = con.createStatement();
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet res = statement.getGeneratedKeys();
			res.next();
			key = res.getInt(1);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * Execute a query
	 * 
	 * @param query
	 *            An executable query
	 * @return boolean If the satement sucesfully run
	 */
	public boolean exec(String query) {
		boolean run = false;
		try {
			Statement statement = con.createStatement();
			run = statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return run;
	}

	// 1 bot 2 guest 3 member 4 admin
	public boolean isSafeStatut(int statut) {
		return (statut > 0 && statut < 5);
	}

	// 0 banned off 1 bannec on
	public boolean isSafeBanned(int banned) {
		return (banned == 0 || banned == 1);
	}

	public String[] convertObjectArrayToStringArray(Object[] o) {
		String[] s = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			s[i] = o[i].toString();
		}
		return s;
	}

	/**
	 * This fonction is present in the others fonctions who need to interact
	 * with de database. She allow to detect the sort of query and primitive
	 * types.
	 */
	public boolean executeSQL(Object... args) {
		String[] argsToString = convertObjectArrayToStringArray(args);
		// The first argument is always the query
		String query = argsToString[0];
		boolean select = false;
		if (query.indexOf("SELECT") != -1)
			select = true;
		try {
            System.out.println(">> "+query);
			ps = con.prepareStatement(query);
			/*
			 * For each argument, detect if a number or String and put it in the
			 * prepared statement
			 */
			for (int i = 1; i < argsToString.length; i++) {
				if (StringUtils.isNumeric(argsToString[i]))
					ps.setInt(i, Integer.valueOf(argsToString[i]));
				else
					ps.setString(i, argsToString[i]);
			}
			if (select) {
				// for query used a SELECT
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} else {
				// for query used INSERT, DELETE or UPDATE
				if (ps.executeUpdate() > 0)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getCurrentAutoIncrementValueWithTableName(String tableName) {
		String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = uno AND TABLE_NAME = ?";
		if (executeSQL(query, tableName))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}

		return 0;
	}

	public void resetDatabaseSQLite() throws SQLException {
		String s;
		StringBuffer stringBuffer;
        stringBuffer = new StringBuffer();

        try {
            con.close();
            // Destruction de l'ancienne DB
            File file = new File("uno.db");
            file.delete();

            // Recreation du fichier
            this.con = DriverManager.getConnection("jdbc:sqlite:uno.db");

            // Mise en place de la base de donnée via le fichier
            FileReader fr = new FileReader(new File("script/buildingDbSqlite.sql"));
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null) { stringBuffer.append(s); }
			br.close();
			String[] inst = stringBuffer.toString().split(";");
			Statement st = con.createStatement();
			for(int i = 0; i < inst.length; i++) {
				if(!inst[i].trim().equals("")) {
                    st.executeUpdate(inst[i]);
				}
			}
		} catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}