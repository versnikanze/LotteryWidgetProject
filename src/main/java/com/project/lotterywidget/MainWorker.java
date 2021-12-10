package com.project.lotterywidget;

import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MainWorker extends HttpServlet {

    private static final int INITIAL_DELAY = 0;
    private static final int EXECUTION_PERIOD = 30;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private RESTService restService;
    private DatabaseService databaseService;

    public void init() {
        try {
            this.restService = new RESTService();
            this.databaseService = new DatabaseService();
            getHandleWinner();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getHandleWinner() {
        final Runnable handler = this::scheduledTasks;
        scheduler.scheduleAtFixedRate(handler, INITIAL_DELAY, EXECUTION_PERIOD, SECONDS);
    }

    private void scheduledTasks() {
        try {
            WinningNumber winningNumber = restService.getWinnerData();
            this.databaseService.insertWinningNumber(winningNumber);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
