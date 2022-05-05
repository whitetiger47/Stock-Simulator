package com.project.stocktrading.dao.User;


import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 * @author Raj_Valand
 */

@Component
public class UserRepository extends NewUser implements IUserActions {

    private static final String registerUser = "INSERT INTO USER (firstName, lastName, email, password, residentialId, dateOfBirth, funds) VALUES (?,?,?,?,?,?,?)";
    private static final String getPassword = "SELECT password from USER where email = ?";
    private static final String updateFunds = "UPDATE USER SET funds = ? WHERE email = ?";
    private static final String getAdminPassword = "SELECT password from ADMIN where email = ?";
    private static final String getUserWithEmail = "SELECT id, firstName, lastName, email, residentialId, dateOfBirth, funds from USER where email = ?";
    private static final String checkIfUsernameAlreadyExists = "SELECT EXISTS(SELECT 1 FROM USER WHERE email = ?)";
    private static final String checkIfResidentialIdAlreadyExists = "SELECT EXISTS(SELECT 1 FROM USER WHERE residentialId = ?)";
    private static final String getUserFunds = "SELECT funds FROM USER where id = ?";
    private static final String withdrawFunds = "update USER set funds = ? where id = ?";
    private static final String depositFunds = "update USER set funds = ? where id = ?";
    private static final String queryGetUserLogin = "SELECT * from USER where is_user_login=1";
    private static final String queryUserLogout = "UPDATE USER SET is_user_login=0 where id = ?";
    private static final String queryUserLogin = "UPDATE USER SET is_user_login=1 where email = ?";
    private final String queryCreateWatchlistValueTable = "CREATE TABLE WATCHLIST_VALUE " +
            "(watchlist_id int, stock_id int, PRIMARY KEY(watchlist_id, stock_id), FOREIGN KEY(watchlist_id) REFERENCES " +
            "WATCHLIST(id), FOREIGN KEY(stock_id) REFERENCES STOCK(id))";
    private final String queryCreateUserTable = "CREATE TABLE USER " +
            "(firstName varchar(45), lastName varchar(45), email varchar(45), password varchar(45), " +
            "residentialId varchar(45), dateOfBirth date, funds decimal(64,25), reset_password_token varchar(30)" +
            "isUserLogin int)";
    public String token;
    //Methods for forgot/reset password
    User user = new User();
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

    public int createUserTable() {
        return createTable("USER", queryCreateUserTable);
    }

    public int registerUser(String firstName, String lastName, String email, String password, String residentialId, Date dateOfBirth, BigDecimal funds) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(registerUser);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, residentialId);
            preparedStatement.setDate(6, new java.sql.Date(dateOfBirth.getTime()));
            preparedStatement.setBigDecimal(7, funds);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }


    public boolean checkUserAlreadyExists(String email) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Boolean hadResultset = false;
        int h = 0;
        try {
            preparedStatement = connection.prepareStatement(checkIfUsernameAlreadyExists);
            preparedStatement.setString(1, email);
            hadResultset = preparedStatement.execute();
            if (hadResultset) {
                resultSet = preparedStatement.getResultSet();
                return !resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return true;
    }

    public boolean checkResidentialIdAlreadyExists(String email) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Boolean hadResultset = false;
        int h = 0;
        try {
            preparedStatement = connection.prepareStatement(checkIfResidentialIdAlreadyExists);
            preparedStatement.setString(1, email);
            hadResultset = preparedStatement.execute();
            if (hadResultset) {
                resultSet = preparedStatement.getResultSet();
                return !resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return true;
    }


    public String getUserPasswordFromDb(String email) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String userPassword = null;
        try {
            preparedStatement = connection.prepareStatement(getPassword);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userPassword = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return userPassword;
    }

    public String getAdminPasswordFromDb(String email) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String adminPassword = null;
        try {
            preparedStatement = connection.prepareStatement(getAdminPassword);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            adminPassword = resultSet.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return adminPassword;
    }


    public IUser getUserLoggedIn() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        IUser iUser = null;
        try {
            preparedStatement = connection.prepareStatement(queryGetUserLogin);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                iUser = this.getNewUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return iUser;
    }


    public int setUserLoggedIn(String username) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryUserLogin);
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;

    }

    public boolean userLogout() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryUserLogout);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return true;
    }


    public IUser getUserFromDatabase(String username) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        IUser iUser = null;
        try {
            preparedStatement = connection.prepareStatement(getUserWithEmail);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                iUser = this.getNewUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return iUser;
    }

    public int updateFunds(IUser user) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(updateFunds);
            preparedStatement.setBigDecimal(1, user.getFunds());
            preparedStatement.setString(2, user.getEmail());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return 0;
    }

    /**
     * @author Smit_Thakkar
     */

    public BigDecimal getFunds() {
        BigDecimal userFunds = new BigDecimal("0.001");

        try {
            userFunds = jdbcTemplate.queryForObject(getUserFunds, BigDecimal.class, this.getUserLoggedIn().getId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return userFunds;
    }

    public BigDecimal withdrawUserFunds(User user) {
        BigDecimal fundsToDeposit = user.getFunds();
        BigDecimal availableFunds = getFunds();
        BigDecimal updatedFunds = availableFunds.subtract(fundsToDeposit);
        try {
            jdbcTemplate.update(depositFunds, BigDecimal.class, updatedFunds);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return updatedFunds;
    }

    public BigDecimal depositUserFunds(User user) {
        BigDecimal fundsToWithdraw = user.getFunds();
        BigDecimal availableFunds = getFunds();
        BigDecimal updatedFunds = availableFunds.add(fundsToWithdraw);
        try {
            jdbcTemplate.update(depositFunds, BigDecimal.class, updatedFunds);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return updatedFunds;
    }

    public User findByEmail(String email) {
        return user;
    }

    public User findByResetPasswordToken(String token) {
        return user;
    }

}


//User Repo -> 2
// WatchlistRepo -> create watchlist table
