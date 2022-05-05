package com.project.stocktrading.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author abhishekuppe
 */
public class DatabaseFactory extends DatabaseAbstractFactory {

    private IDatabaseUtils databaseUtils;

    @Override
    public IDatabaseUtils createDatabaseUtils() {
        if (databaseUtils == null) {
            databaseUtils = new DatabaseUtils();
        }
        return databaseUtils;
    }

    @Override
    public void closeConnections(Connection connection, IDatabaseUtils iDatabaseUtils,
                                 PreparedStatement preparedStatement, ResultSet resultSet) {
        iDatabaseUtils.closeConnection(connection);
        iDatabaseUtils.closeStatement(preparedStatement);
        iDatabaseUtils.closeResultSet(resultSet);
    }
}
