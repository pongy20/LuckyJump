package de.pongy.luckyjump.sql;

import de.pongy.luckyjump.config.SQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    private static SQL instance;

    public static SQL getInstance() {
        if (instance == null)
            instance = new SQL(SQLConfig.host, SQLConfig.port, SQLConfig.database, SQLConfig.user, SQLConfig.password);
        return instance;
    }

    public String host;
    public String port;
    public String database;
    public String username;
    public String password;
    private Connection con;

    private SQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }
    public void close() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected() {
        return (con != null);
    }
    public Connection getConnection() {
        return con;
    }

}
