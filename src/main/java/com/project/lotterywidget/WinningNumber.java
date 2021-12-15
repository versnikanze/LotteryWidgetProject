package com.project.lotterywidget;

/**
 * This class represents a winning number in the raffle.
 */
public class WinningNumber {

    private final String createdAtTime;
    private final int winningNumber;

    /**
     * Constructor.
     *
     * @param createdAtTime time the raffle was held
     * @param winningNumber winning number of the raffle
     */
    public WinningNumber(String createdAtTime, int winningNumber) {
        this.createdAtTime = createdAtTime;
        this.winningNumber = winningNumber;
    }

    /**
     * Returns the time at which the raffle was held.
     *
     * @return time the raffle was held
     */
    public String getCreatedAtTime() {
        return createdAtTime;
    }

    /**
     * Returns the winning number of the raffle.
     *
     * @return winning number
     */
    public int getWinningNumber() {
        return winningNumber;
    }

}
