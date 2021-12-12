package com.project.lotterywidget;

public class WinningNumber {

    private final String createdAtTime;
    private final int winningNumber;

    public WinningNumber(String createdAtTime, int winningNumber) {
        this.createdAtTime = createdAtTime;
        this.winningNumber = winningNumber;
    }

    public String getCreatedAtTime() {
        return createdAtTime;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

}
