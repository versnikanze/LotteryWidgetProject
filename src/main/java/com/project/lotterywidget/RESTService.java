package com.project.lotterywidget;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RESTService {

    private static final String GET_WINNER_API_LINK = "https://celtra-lottery.herokuapp.com/api/getLotteryNumber";

    public WinningNumber getWinnerData() throws IOException {

        // creates connection and read
        URLConnection request = new URL(GET_WINNER_API_LINK).openConnection();
        request.connect();
        InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());

        // map to GSON objects and read values
        JsonObject root = new JsonParser().parse(inputStreamReader).getAsJsonObject();
        int winningNumber = root.get("lotteryNumber").getAsInt();
        String createdAtTime = root.get("createdAt").getAsString();

        return new WinningNumber(createdAtTime, winningNumber);

    }

}
