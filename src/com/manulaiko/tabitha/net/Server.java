package com.manulaiko.tabitha.net;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.time.Duration;
import java.time.Instant;

import java.util.HashMap;

import com.manulaiko.tabitha.Console;

/**
 * Server class.
 *
 * Used to start a socket server.
 *
 * The constructor accepts as parameter a short integer that is the
 * port on which the server will listen for connections.
 *
 * When extending this class you must override the method
 * `run` that will handle the connections.
 *
 * To add a new socket connection use the method `addConnection` that
 * accepts as parameter an object of type `com.manulaiko.tabitha.net.Connection`.
 *
 * The method `getConnections` returns all connections.
 *
 * The method `start` starts the server.
 * The method `stop` stops the server
 * the method `showStatus` shows the status of the server
 *
 * Example:
 *
 *     public class TestServer extends Server
 *     {
 *         public TestServer()
 *         {
 *             super((short)8080);
 *         }
 *
 *         public void run()
 *         {
 *             while(true) {
 *                 Connection c = this.acceptConnection();
 *
 *                 Console.println("Connection received!");
 *
 *                 this.addConnection(c);
 *             }
 *         }
 *     }
 *
 *     TestServer server = new TestServer();
 *
 *     server.start();
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Server implements Runnable
{
    /**
     * Server port.
     */
    protected short _port;

    /**
     * Whether server is running or not.
     */
    protected boolean _isRunning = false;

    /**
     * Start time.
     */
    protected Instant _startTime;

    /**
     * Stop time.
     */
    protected Instant _stopTime;

    /**
     * Server socket.
     */
    protected ServerSocket _server;

    /**
     * Thread instance.
     */
    protected Thread _thread;

    /**
     * Connections array.
     */
    protected HashMap<Integer, Connection> _connections = new HashMap<>();

    /**
     * Constructor.
     *
     * @param port Port on which server will listen.
     */
    public Server(short port)
    {
        this._port   = port;
        this._thread = new Thread(this);
    }

    /**
     * Starts the server.
     *
     * @throws IOException If the server failed to start.
     */
    public void start() throws IOException
    {
        this._server    = new ServerSocket(this._port);
        this._isRunning = true;

        this._thread.start();

        this._startTime = Instant.now();
    }

    /**
     * Stops the server.
     *
     * @throws IOException If the server failed to stop.
     */
    public void stop() throws IOException
    {
        if(this._server != null) {
            this._server.close();
        }

        this._isRunning = false;
        this._stopTime  = Instant.now();
    }

    /**
     * Shows the status of the server.
     */
    public void showStatus()
    {
        if(this._isRunning) {
            Console.println("Server is running.");
        } else {
            Console.println("Server is not running.");
        }

        if(this._startTime != null) {
            Instant now = this._stopTime;
            if(now == null) {
                now = Instant.now();
            }

            Console.println("Running for "+ Duration.between(this._startTime, now).toString());
        }

        Console.println("There are "+ this._connections.size() +" connections to this server.");
    }

    /**
     * Accepts a connection.
     *
     * @return The socket of the connection.
     *
     * @throws IOException If failed to return the socket.
     */
    public Socket acceptConnection() throws IOException
    {
        return this._server.accept();
    }

    /**
     * Adds a connection to the array.
     *
     * @param connection Connection to add.
     *
     * @return Key of the connection on the array.
     */
    public int addConnection(Connection connection)
    {
        int id = this._connections.size();

        this._connections.put(id, connection);

        connection.id = id;

        return id;
    }

    /**
     * Returns all connections.
     */
    public HashMap<Integer, Connection> getAllConnections()
    {
        return this._connections;
    }

    /**
     * Checks whether the thread is running or not.
     */
    public void run()
    {
        while(this._isRunning) {
            this.onRunning();
        }
    }

    /**
     * Performs the action of the thread.
     */
    public abstract void onRunning();

    /**
     * Closes a connection.
     *
     * @param id Connection.
     */
    public abstract void finishConnection(int id);
}
