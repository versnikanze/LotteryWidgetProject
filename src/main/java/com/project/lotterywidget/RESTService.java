package com.project.lotterywidget;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

@Path("/rest")
public class RESTService {

    private static final String GET_WINNER_API_LINK = "https://celtra-lottery.herokuapp.com/api/getLotteryNumber";

    private final ArrayList<Contestant> contestants;

    public RESTService() {
        this.contestants = new ArrayList<>();
    }

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

    @POST
    @Path("/registercontestant")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response receiveContestants(Contestant jsonRequest) {
        this.contestants.add(jsonRequest);
        return Response.ok("Contestant guess received").build();
    }

    public ArrayList<Contestant> getWinningContestants(int winningNumber) {
        return this.contestants.stream().filter(contestant -> contestant.getPickedNumber() == winningNumber).collect(toCollection(ArrayList::new));
    }
}
