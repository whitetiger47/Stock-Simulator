package com.project.stocktrading.database;

import java.sql.*;

/**
 * @author abhishekuppe
 */

public class DatabaseUtils implements IDatabaseUtils {

    private static Connection connection = null;
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_10_PRODUCTION",
                        "CSCI5308_10_PRODUCTION_USER",
                        "Ailee6gaJogah0Ie");
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            if (connection == null) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement == null) {
                return;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet == null) {
                return;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
