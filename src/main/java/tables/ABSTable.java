package tables;

import db.IDBConnector;
import db.MySQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ABSTable {
    private String tableName;
    private Map<String, String> columns;
    IDBConnector db;

    public ABSTable(String tableName) {
        this.tableName = tableName;
        db = new MySQLConnector();
    }

    public void create(Map<String, String> columns) {
        this.columns = columns;
        String sqlRequest = String.format("CREATE TABLE %s (%s)", this.tableName, convertMapColumnsToString());
        db.executeRequest(sqlRequest);
    }

    private String convertMapColumnsToString() {
        final String result = columns.entrySet().stream()
                .map((Map.Entry entry) -> String.format("%s %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","));
        return result;
    }

    public void selectAll() {
        final String sqlRequest = String.format("SELECT * FROM %s", tableName);
        ResultSet resultSet = db.executeRequestWithAnswer(sqlRequest);
        try {
            int columns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.println(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }
}
