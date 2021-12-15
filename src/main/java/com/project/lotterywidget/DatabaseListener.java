package com.project.lotterywidget;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import java.sql.*;
import java.util.concurrent.CountDownLatch;

/**
 * Implements Runnable class. Listens to insert events in the database table winners.
 * Forwards these events to classes that implement it.
 */
public class DatabaseListener implements Runnable {

    private final String HOST = "localhost";
    private final int PORT = 5432;
    private final String DATABASE_NAME = "loterry_widget_db";
    private final String USER = "postgres";
    private final String PASSWORD = "1234";
    private final String SQL_LISTEN_STATEMENT = "LISTEN winnersnotify";

    private final PGDataSource dataSource;
    private CountDownLatch countDownLatch;

    /**
     * Default constructor
     */
    public DatabaseListener() {
        this.dataSource = new PGDataSource();
        dataSource.setHost(HOST);
        dataSource.setPort(PORT);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        this.countDownLatch = new CountDownLatch(1);
    }

    /**
     * @param countDownLatch countDownLatch to be set
     */
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    /**
     * Implementation of the run method. Attaches a listener for the database event.
     * Forwards a message to the implementation class with CountDownLatch that the database event
     * has been received. Runs in an endless loop since the connection closes if the method stops.
     */
    @Override
    public void run() {
        PGNotificationListener listener = new PGNotificationListener() {

            @Override
            public void notification(int processId, String channelName, String payload) {
                countDownLatch.countDown();
            }
        };
        try (PGConnection connection = (PGConnection) dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(SQL_LISTEN_STATEMENT);
            statement.close();
            connection.addNotificationListener(listener);
            while (true) {
                Thread.sleep(500);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
    }
}
