package org.zahid.apps.web.pos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    public static DB db;
    private static Connection connection = null;

    private DB(String server, String port, String service, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + server + ":" + port + "/" + service + "?useSSL=false", connectionProps);
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public static DB getInstance(String server, String port, String service, String username, String password) throws SQLException, ClassNotFoundException {
        if (db == null) {
            db = new DB(server, port, service, username, password);
        }
        return db;
    }
}
