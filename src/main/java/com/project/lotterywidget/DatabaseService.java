package com.project.lotterywidget;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseService {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lottery_widget_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private int lastInsertedId = 0;
    private final Statement statement;
    private final Connection connection;

    public DatabaseService() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        this.statement = connection.createStatement();
    }

    public void insertWinningNumber(WinningNumber winningNumber) throws SQLException {
        String sqlInsertStatement = "INSERT INTO raffles (created_at, lottery_number)" +
                "VALUES ('" + winningNumber.getCreatedAtTime() + "', " + winningNumber.getWinningNumber() + ")";
        String sqlGetInsertedId = "SELECT LAST_INSERT_ID()";
        this.statement.executeUpdate(sqlInsertStatement);
        ResultSet resultSet = this.statement.executeQuery(sqlGetInsertedId);
        resultSet.next();
        this.lastInsertedId = resultSet.getInt("LAST_INSERT_ID()");
    }

    public void insertWinningContestants(ArrayList<Contestant> winningContestants) throws SQLException {
        String sqlInsertWinner = "INSERT INTO winners (raffle_id, contestant_name)" +
                "VALUES (?, ?)";
        PreparedStatement insertWinners = this.connection.prepareStatement(sqlInsertWinner);
        for (Contestant winner : winningContestants) {
            insertWinners.setInt(1, this.lastInsertedId);
            insertWinners.setString(2, winner.getContestantName());
            insertWinners.addBatch();
        }
        insertWinners.executeBatch();
    }

}
