package com.project.lotterywidget;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbNillable;
import jakarta.json.bind.annotation.JsonbProperty;


/**
 * This class represents a registered contestant in the lottery
 */
@JsonbNillable
public class Contestant {

    private final String contestantName;
    private final Integer pickedNumber;

    /**
     * @param contestantName the name of the contestant
     * @param pickedNumber   the number the contestant picked
     */
    @JsonbCreator
    public Contestant(@JsonbProperty("contestantName") String contestantName, @JsonbProperty("pickedNumber") Integer pickedNumber) {
        super();
        this.contestantName = contestantName;
        this.pickedNumber = pickedNumber;
    }

    /**
     * @return contestants name
     */
    public String getContestantName() {
        return contestantName;
    }

    /**
     * @return contestants picked number
     */
    public Integer getPickedNumber() {
        return pickedNumber;
    }
}