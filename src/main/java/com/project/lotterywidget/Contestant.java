package com.project.lotterywidget;

public class Contestant {

    private String contestantName;
    private Integer pickedNumber;

    /**
     * No args constructor for use in serialization
     */
    public Contestant() {
    }

    public Contestant(String contestantName, Integer pickedNumber) {
        super();
        this.contestantName = contestantName;
        this.pickedNumber = pickedNumber;
    }

    public String getContestantName() {
        return contestantName;
    }

    public Integer getPickedNumber() {
        return pickedNumber;
    }
}