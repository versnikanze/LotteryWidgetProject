package com.project.lotterywidget;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lottery_widget_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private final Statement statement;

    public DatabaseService() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        this.statement = connection.createStatement();
    }

    public void insertWinningNumber(WinningNumber winningNumber) throws SQLException {
        String sql_statement = "INSERT INTO raffles (created_at, lottery_number)" +
                "VALUES ('" + winningNumber.getCreatedAtTime() + "', " + winningNumber.getWinningNumber() + ")";
        this.statement.executeUpdate(sql_statement);
    }

}
