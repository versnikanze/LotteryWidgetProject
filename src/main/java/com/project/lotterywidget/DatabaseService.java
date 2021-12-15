package com.project.lotterywidget;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implements AutoCloseable. This class is used for retrieving and manipulating data in out database.
 */
public class DatabaseService implements AutoCloseable {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost/loterry_widget_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final String NO_WINNERS_MESSAGE = "No lucky contestants";
    private int lastInsertedId = 0;
    private final Statement statement;
    private final Connection connection;

    /**
     * Default constructor
     *
     * @throws SQLException           if the connection can't be established
     * @throws ClassNotFoundException if the PostgresSQL driver can't be found
     */
    public DatabaseService() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        this.statement = connection.createStatement();
    }

    /**
     * Inserts winning number into the database. Collects its inserted primary key
     * and updates the value of a class variable with it
     *
     * @param winningNumber winning number to be inserted into the database
     * @throws SQLException if the connection can't be established
     */
    public void insertWinningNumber(WinningNumber winningNumber) throws SQLException {
        String sqlInsertStatement = "INSERT INTO raffles (created_at, lottery_number)" +
                "VALUES ('" + winningNumber.getCreatedAtTime() + "', " + winningNumber.getWinningNumber() + ") " +
                "RETURNING raffle_id";
        ResultSet resultSet = this.statement.executeQuery(sqlInsertStatement);
        resultSet.next();
        this.lastInsertedId = resultSet.getInt("raffle_id");
    }

    /**
     * Inserts a contestant into the database.
     *
     * @param contestant contestant to be inserted into the database
     * @throws SQLException if the connection can't be established
     */
    public void insertContestant(Contestant contestant) throws SQLException {
        String sqlInsertStatement = "INSERT INTO contestants (contestant_name, picked_number)" +
                "VALUES ('" + contestant.getContestantName() + "', " + contestant.getPickedNumber() + ")";
        this.statement.executeUpdate(sqlInsertStatement);
    }

    /**
     * Collects all current contestants from the database and returns them in an
     * ArrayList of the class Contestant.
     *
     * @return a list of the current contestants
     * @throws SQLException if the connection can't be established
     */
    public ArrayList<Contestant> getCurrentContestants() throws SQLException {
        ArrayList<Contestant> currentContestants = new ArrayList<>();
        String sqlGetWinners = "SELECT contestant_name, picked_number " +
                "FROM contestants";
        ResultSet resultSet = this.statement.executeQuery(sqlGetWinners);
        String columnContestantName = resultSet.getMetaData().getColumnName(1);
        String columnPickedNumber = resultSet.getMetaData().getColumnName(2);
        while (resultSet.next()) {
            currentContestants.add(new Contestant(resultSet.getString(columnContestantName), resultSet.getInt(columnPickedNumber)));
        }
        return currentContestants;
    }

    /**
     * Inserts an ArrayList of the Contestant class which represents the
     * winning contestants into the database.
     *
     * @param winningContestants a list of the winning contestants
     * @throws SQLException if the connection can't be established
     */
    public void insertWinningContestants(ArrayList<Contestant> winningContestants) throws SQLException {
        String sqlInsertWinner = "INSERT INTO winners (raffle_id, contestant_name)" +
                "VALUES (?, ?)";
        PreparedStatement insertWinners = this.connection.prepareStatement(sqlInsertWinner);
        insertWinners.setInt(1, this.lastInsertedId);
        for (Contestant winner : winningContestants) {
            insertWinners.setString(2, winner.getContestantName());
            insertWinners.addBatch();
        }
        if (winningContestants.isEmpty()) {
            insertWinners.setString(2, NO_WINNERS_MESSAGE);
            insertWinners.addBatch();
        }
        insertWinners.executeBatch();
    }

    /**
     * Returns a JsonArray of the last five winning numbers and
     * a concatenated String of all the winners of that raffle.
     *
     * @return the last five winning numbers and their winners
     * @throws SQLException if the connection can't be established
     */
    public JsonArray getLastFiveWinners() throws SQLException {
        String sqlGetWinners = "SELECT raffles.lottery_number, string_agg(winners.contestant_name, ', ') as winners " +
                "FROM raffles " +
                "LEFT JOIN winners ON winners.raffle_id = raffles.raffle_id " +
                "GROUP BY raffles.raffle_id " +
                "ORDER BY raffles.raffle_id DESC LIMIT 5";
        ResultSet resultSet = this.statement.executeQuery(sqlGetWinners);
        JsonArrayBuilder jsonLastWinner = Json.createArrayBuilder();
        String columnWinningNumber = resultSet.getMetaData().getColumnName(1);
        String columnWinnerNames = resultSet.getMetaData().getColumnName(2);
        while (resultSet.next()) {
            String returnedWinners = resultSet.getString(2);
            jsonLastWinner.add(Json.createObjectBuilder()
                    .add(columnWinningNumber, resultSet.getInt(columnWinningNumber))
                    .add(columnWinnerNames, (returnedWinners == null) ? "" : returnedWinners));
        }
        return jsonLastWinner.build();
    }


    /**
     * Implements close function. Closes the database connection on class destruction.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
