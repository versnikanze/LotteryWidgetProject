package com.project.lotterywidget;

import jakarta.servlet.http.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MainWorker extends HttpServlet {

    private static final int INITIAL_DELAY = 0;
    private static final int EXECUTION_PERIOD = 5;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void init()
    {
        getHandleWinner();
    }

    public void getHandleWinner() {
        final Runnable beeper = () -> System.out.println("beep");
        final ScheduledFuture<?> winnerHandle =
                scheduler.scheduleAtFixedRate(beeper, INITIAL_DELAY, EXECUTION_PERIOD, SECONDS);
    }

}
