package com.project.lotterywidget;

public class WinningNumber {

    private String createdAtTime;
    private int winningNumber;

    public WinningNumber(String createdAtTime, int winningNumber) {
        this.createdAtTime = createdAtTime;
        this.winningNumber = winningNumber;
    }

    public String getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(String createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(int winningNumber) {
        this.winningNumber = winningNumber;
    }
}
