package db;

import java.sql.ResultSet;

public interface IDBConnector {
    void executeRequest(String response);
    ResultSet executeRequestWithAnswer(String response);
    int executeInsert(String query);
    void close();
}
