package com.project.stocktrading.dao.Watchlist;


import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raj_Valand
 */


@Component
public class WatchListRepository extends NewBuySell implements IWatchListActions {

    private static final String queryUpdateBuySellCount = "UPDATE STOCK SET buy_count = ?, sell_count = ? WHERE id = ?";
    private final String queryCreateWatchlistTable = "CREATE TABLE WATCHLIST " +
            "(id int NOT NULL PRIMARY KEY, name varchar(100))";
    private final String queryCreateBuyShellTable = "CREATE TABLE BUY_SELL (id int NOT NULL AUTO_INCREMENT, stock_id int," +
            " is_buy_sell int, is_regular_intraday varchar(10), is_limit_market varchar(10), stoploss float, target float," +
            " quantity int, price decimal(65,20), PRIMARY KEY (id), FOREIGN KEY(stock_id) REFERENCES STOCK(id) )";
    private final String queryCreateWatchlistValueTable = "CREATE TABLE WATCHLIST_VALUE " +
            "(watchlist_id int, stock_id int, PRIMARY KEY(watchlist_id, stock_id), FOREIGN KEY(watchlist_id) REFERENCES " +
            "WATCHLIST(id), FOREIGN KEY(stock_id) REFERENCES STOCK(id))";
    private final String queryAddWatchlist = "INSERT INTO WATCHLIST_VALUE(watchlist_id, stock_id) VALUES (?,?)";
    private final String queryGetStockID = "SELECT stock_id FROM WATCHLIST_VALUE WHERE watchlist_id = ?";
    private final String queryForStock = "SELECT * FROM STOCK WHERE id = ?";
    private final String queryForPrice = "SELECT * FROM STOCK_PRICE WHERE stock_id =?";
    private final String queryCreateWatchLists = "INSERT INTO WATCHLIST(id, name) VALUES (?,?)";
    private final String queryCheckIfWatchListExists = "SELECT count(*) FROM WATCHLIST WHERE id = ?";
    private final String queryAddBuySell = "INSERT INTO BUY_SELL(stock_id, is_buy_sell, is_regular_intraday, is_limit_market," +
            " stoploss, target, quantity, price) VALUES (?,?,?,?,?,?,?,?)";
    private final String queryGetLimitStocks = "SELECT * FROM BUY_SELL WHERE is_limit_market = 'Limit' ";
    private final String queryUpdateLimit = "UPDATE BUY_SELL SET is_limit_market = 'Market' WHERE id = ?";
    String queryIsTableExists = "SELECT count(*) from information_schema.tables where table_name = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createTable(String name, String createTableQuery) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, name, null);

            if (resultSet.next()) {
                return 1;
            } else {
                preparedStatement = connection.prepareStatement(createTableQuery);
                preparedStatement.execute();
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
    }

    public int createWathchlistTable() {
        return createTable("WATCHLIST", queryCreateWatchlistTable);
    }

    public int createBuySellTable() {
        return createTable("BUY_SELL", queryCreateBuyShellTable);
    }

    public void createWatchLists() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            for (int i = 1; i < 5; i++) {
                try {
                    preparedStatement = connection.prepareStatement(queryCreateWatchLists);
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, "Watchlist #" + i);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
    }


    public int addWatchList(int watchlist_id, int stock_id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryAddWatchlist);
            preparedStatement.setInt(1, watchlist_id);
            preparedStatement.setInt(2, stock_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public List<Integer> getStockIdfromWatchId(int watchlist_id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Integer> newStockList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetStockID);
            preparedStatement.setInt(1, watchlist_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                newStockList.add(resultSet.getInt("stock_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return newStockList;
    }


    public int addBuyStockIntoBuySell(Integer stock_id, Integer is_buy_sell, String is_regular_intraday, String is_limit_market, Float stoploss,
                                      Float target, Integer quantity, BigDecimal price) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryAddBuySell);
            preparedStatement.setInt(1, stock_id);
            preparedStatement.setInt(2, is_buy_sell);
            preparedStatement.setString(3, is_regular_intraday);
            preparedStatement.setString(4, is_limit_market);
            if (stoploss == null && target == null) {
                preparedStatement.setFloat(5, 0);
                preparedStatement.setFloat(6, 0);
            } else {
                preparedStatement.setFloat(5, stoploss);
                preparedStatement.setFloat(6, target);
            }
            preparedStatement.setInt(7, quantity);
            preparedStatement.setBigDecimal(8, price);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public List<IBuySell> getLimitStock() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<IBuySell> newBuySellList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetLimitStocks);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                newBuySellList.add(this.getNewBuySell(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return newBuySellList;
    }


    public int updateLimitBuySell(int id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryUpdateLimit);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int updateStockBuyCount(IStock stock) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryUpdateBuySellCount);
            preparedStatement.setObject(1, stock.getBuyCount());
            preparedStatement.setObject(2, stock.getSellCount());
            preparedStatement.setInt(3, stock.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

}
