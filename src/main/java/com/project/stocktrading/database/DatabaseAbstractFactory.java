package com.project.stocktrading.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author abhishekuppe
 */
public abstract class DatabaseAbstractFactory {

    private static DatabaseAbstractFactory databaseAbstractFactory = null;

    public static DatabaseAbstractFactory getInstance() {
        if (databaseAbstractFactory == null) {
            databaseAbstractFactory = new DatabaseFactory();
        }
        return databaseAbstractFactory;
    }

    public abstract IDatabaseUtils createDatabaseUtils();

    public abstract void closeConnections(Connection connection, IDatabaseUtils iDatabaseUtils,
                                          PreparedStatement preparedStatement, ResultSet resultSet);
}
