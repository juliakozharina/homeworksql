package db;

import settings.ISetting;
import settings.PropertiesReader;

import java.sql.*;
import java.util.Map;

public class MySQLConnector implements IDBConnector {
    private static Connection connection = null;
    private static Statement statement = null;

    public MySQLConnector() {
        connect();
    }

    private void connect() {
        ISetting reader = new PropertiesReader();
        Map<String, String> settings = reader.read();
        if (connection == null) {
            try {
                connection = DriverManager
                        .getConnection(settings.get("url") + "/" + settings.get("db_name"),
                                settings.get("db_username"),
                                settings.get("db_password"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement == null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeRequest(String response) {
        try {
            statement.execute(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet executeRequestWithAnswer(String response) {
        try {
            return statement.executeQuery(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeInsert(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
