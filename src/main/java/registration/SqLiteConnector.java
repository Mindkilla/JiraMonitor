package registration;

import java.sql.*;

/**
 * @author Andrey Smirnov
 * @date 14.02.2018
 */
class SqLiteConnector {

    private static Connection conn;
    private static Statement statement;
    private static ResultSet resultSet;

    private static void conect() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + System.getenv("CATALINA_HOME") + "/" + "conf" + "/" + "jiramonitor.db");
    }

    private static void writeRegCode(Integer userId, String login, String code) throws SQLException {
        statement = conn.createStatement();
        statement.execute(String.format("INSERT INTO 'REGISTRATION_CODES' ('USER_ID', 'LOGIN', 'CODE') VALUES (%d, '%s', '%s'); ", userId, login, code));
    }

    private static void delete(Integer userId, String code) throws SQLException {
        statement = conn.createStatement();
        statement.execute(String.format("DELETE FROM 'REGISTRATION_CODES' WHERE USER_ID=%d and CODE='%s'; ", userId, code));
    }

    private static String readCode(Integer userId, String code) throws ClassNotFoundException, SQLException {
        statement = conn.createStatement();
        resultSet = statement.executeQuery(String.format("SELECT * FROM REGISTRATION_CODES WHERE USER_ID=%d and CODE='%s'", userId, code));
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("LOGIN");
        }
        return result;
    }

    private static void registerUser(Integer userId, String login) throws SQLException {
        statement = conn.createStatement();
        statement.execute(String.format("INSERT INTO 'USERS' ('USER_ID', 'LOGIN') VALUES (%d, '%s'); ", userId, login));
    }

    private static String checkUser(Integer userId) throws ClassNotFoundException, SQLException {
        statement = conn.createStatement();
        resultSet = statement.executeQuery(String.format("SELECT * FROM USERS WHERE USER_ID=%d", userId));
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("LOGIN");
        }
        return result;
    }


    static void connAndWrite(Integer userId, String login, String code) throws ClassNotFoundException, SQLException {
        conect();
        writeRegCode(userId, login, code);
        closeConn();
    }

    static String connAndRead(Integer userId, String code) throws ClassNotFoundException, SQLException {
        conect();
        String result = readCode(userId, code);
        deleteCode(userId, code);
        return result;
    }

    private static void deleteCode(Integer userId, String code) throws ClassNotFoundException, SQLException {
        statement.close();
        if (resultSet != null)
            resultSet.close();
        delete(userId, code);
        closeConn();
    }

    static void connAndRegister(Integer userId, String login) throws ClassNotFoundException, SQLException {
        conect();
        registerUser(userId, login);
        closeConn();
    }

    static String connAndCheck(Integer userId) throws ClassNotFoundException, SQLException {
        conect();
        String result = checkUser(userId);
        closeConn();
        return result;
    }

    private static void closeConn() throws ClassNotFoundException, SQLException {
        statement.close();
        if (resultSet != null)
            resultSet.close();
        conn.close();
    }

    private SqLiteConnector() {
    }
}
