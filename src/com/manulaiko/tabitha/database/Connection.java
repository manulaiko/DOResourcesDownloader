package com.manulaiko.tabitha.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connection class.
 *
 * This class establish a connection to a MySQL server
 * and provides methods for executing queries.
 *
 * The constructor accepts as parameters:
 *  * A string being the hostname/ip of the MySQL server.
 *  * A short integer being the port on which the server is running.
 *  * A string being the username.
 *  * A string being the password.
 *  * A string being the name of the database.
 *
 * Example:
 *
 *     try {
 *         Connection connection = new Connection("localhost", (short)3306, "root", "", "tabitha");
 *     } catch(ConnectionFailed e) {
 *         Console.println("Couldn't connect to database server!");
 *     }
 *
 * @author Manuliako <manulaiko@gmail.com>
 */
public class Connection
{
    /**
     * Connection object.
     */
    private java.sql.Connection _connection;

    /**
     * Constructor.
     *
     * @param host     Server host.
     * @param port     Server port.
     * @param username Authentication user name.
     * @param password Authentication password.
     * @param database Database name.
     *
     * @throws Exception If couldn't connect to the server.
     */
    public Connection(String host, short port, String username, String password, String database) throws Exception
    {
        this._connection = DriverManager.getConnection("jdbc:mysql://"+ host +":"+ port +"/"+ database, username, password);
    }

    /**
     * Returns a statement object.
     *
     * @param query Query to execute.
     *
     * @return Query result.
     *
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException
    {
        Statement st = this._connection.createStatement();

        return st.executeQuery(query);
    }

    /**
     * Executes an UPDATE query.
     *
     * @param query Query to execute.
     *
     * @return Affected rows.
     *
     * @throws SQLException If something goes wrong.
     */
    public int update(String query) throws SQLException
    {
        Statement st = this._connection.createStatement();

        return st.executeUpdate(query);
    }

    /**
     * Returns a prepared statement object.
     *
     * @param query Query to prepare.
     * @param flags Statement flags.
     *
     * @return Prepared statement.
     *
     * @throws SQLException If something goes wrong.
     */
    public PreparedStatement prepare(String query, int flags) throws SQLException
    {
        PreparedStatement st = this._connection.prepareStatement(query, flags);

        return st;
    }

    /**
     * Returns a prepared statement object.
     *
     * @param query Query to prepare.
     *
     * @return Prepared statement.
     *
     * @throws SQLException If something goes wrong.
     */
    public PreparedStatement prepare(String query) throws SQLException
    {
        return this.prepare(query, 0);
    }

    /**
     * Returns database connection.
     *
     * @return Database connection.
     */
    public java.sql.Connection getConnection()
    {
        return this._connection;
    }
}
