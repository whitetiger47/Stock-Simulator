package com.project.stocktrading.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author abhishekuppe
 */
public interface IDatabaseUtils {

    Connection getConnection();

    void closeConnection(Connection conn);

    void closeStatement(PreparedStatement statement);

    void closeResultSet(ResultSet resultSet);
}
