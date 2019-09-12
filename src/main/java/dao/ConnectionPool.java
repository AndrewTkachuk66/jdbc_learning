package dao;

import com.sun.rowset.CachedRowSetImpl;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;

public class ConnectionPool {
    static BasicDataSource dataSource = new BasicDataSource();
    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/students?useUnicode=true&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("1111");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static void executeUpdate(String query){
        try (Connection connection = getConnection();Statement statement = connection.createStatement()){
                statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) {
        CachedRowSetImpl cachedRowSet = null;
        try (  Connection connection = getConnection();Statement statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(query);){

                cachedRowSet = new CachedRowSetImpl();
                if(resultSet != null){
                    cachedRowSet.populate(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cachedRowSet;
    }
}
