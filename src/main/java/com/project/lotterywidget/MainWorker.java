package com.project.lotterywidget;

import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toCollection;

/**
 * Implements HttpServlet. This class is started on load up of the server.
 * It is used for gathering the winning numbers and updating the current
 * winners from the contestants pool.
 */
public class MainWorker extends HttpServlet {

    private static final int INITIAL_DELAY = 0;
    private static final int EXECUTION_PERIOD = 3000;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private RESTService restService;
    private DatabaseService databaseService;

    /**
     * Called in the initialization of the object. Works as the default constructor.
     */
    public void init() {
        try {
            this.restService = new RESTService();
            this.databaseService = new DatabaseService();
            getHandleWinner();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schedules the tasks to be performer at a fixed interval.
     */
    private void getHandleWinner() {
        final Runnable handler = this::scheduledTasks;
        scheduler.scheduleAtFixedRate(handler, INITIAL_DELAY, EXECUTION_PERIOD, SECONDS);
    }

    /**
     * Contains the scheduled tasks to be performed. Gathering the winning number.
     * Inserting it into the database. Gathering current contestants. Picking and
     * inserting the current winners. Deleting the current contestants.
     */
    private void scheduledTasks() {
        try {
            WinningNumber winningNumber = restService.getWinnerData();
            this.databaseService.insertWinningNumber(winningNumber);
            ArrayList<Contestant> currentContestants = this.databaseService.getCurrentContestants();
            this.databaseService.insertWinningContestants(getWinningContestants(currentContestants, winningNumber.getWinningNumber()));
            //delete current contestants
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Picks the winners from the current contestants depending on the winning number.
     *
     * @param currentContestants current contestants in the raffle
     * @param winningNumber      winning number of the current raffle
     * @return list of the winning contestants
     */
    private ArrayList<Contestant> getWinningContestants(ArrayList<Contestant> currentContestants, int winningNumber) {
        return currentContestants.stream().filter(contestant -> contestant.getPickedNumber() == winningNumber).collect(toCollection(ArrayList::new));
    }

}
