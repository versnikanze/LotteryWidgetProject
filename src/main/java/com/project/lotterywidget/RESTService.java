package com.project.lotterywidget;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.json.JsonArray;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * This class represents the REST api
 */
@Path("/rest")
public class RESTService {

    private static final String GET_WINNER_API_LINK = "https://celtra-lottery.herokuapp.com/api/getLotteryNumber";
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    private final DatabaseService databaseService;
    private final DatabaseListener databaseListener;
    private final Thread dbListenerThread;

    /**
     * Default constructor.
     *
     * @throws SQLException           if the connection can't be established
     * @throws ClassNotFoundException if the PostgresSQL driver can't be found
     */
    public RESTService() throws SQLException, ClassNotFoundException {
        this.databaseService = new DatabaseService();
        this.databaseListener = new DatabaseListener();
        this.dbListenerThread = new Thread(this.databaseListener);
        this.dbListenerThread.start();
    }

    /**
     * Returns a JsonArray of the last five winning numbers and
     * a concatenated String of all the winners of that raffle.
     *
     * @return last five winners
     * @throws SQLException if the connection can't be established
     */
    @GET
    @Path("/getlastfivewinners")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getLastFiveWinners() throws SQLException {
        return this.databaseService.getLastFiveWinners();
    }

    /**
     * Returns a JsonArray of the last five winning numbers and
     * a concatenated String of all the winners of that raffle.
     * But only returns a response when new winners are inserted into the database.
     */
    @GET
    @Path(value = "/updatewinners")
    public void hello(@Suspended final AsyncResponse asyncResponse) {
        executorService.submit(() -> {
            try {
                CountDownLatch waitForSignal = new CountDownLatch(1);
                databaseListener.setCountDownLatch(waitForSignal);
                waitForSignal.await();
                asyncResponse.resume(databaseService.getLastFiveWinners());
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Retrieves the winning number of the current raffle from a remote API.
     * And returns it
     *
     * @return winning number of current raffle
     * @throws IOException if the connection can't be established
     */
    public WinningNumber getWinnerData() throws IOException {
        URLConnection request = new URL(GET_WINNER_API_LINK).openConnection();
        request.connect();
        InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());
        JsonObject root = new JsonParser().parse(inputStreamReader).getAsJsonObject();
        int winningNumber = root.get("lotteryNumber").getAsInt();
        String createdAtTime = root.get("createdAt").getAsString();
        return new WinningNumber(createdAtTime, winningNumber);
    }

    /**
     * Receives a JSON request of a new contestant from the clients and saves them to the database.
     *
     * @param jsonRequest contestant to be inserted
     * @return request that contestant was registered
     * @throws SQLException if the connection can't be established
     */
    @POST
    @Path("/registercontestant")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response receiveContestants(Contestant jsonRequest) throws SQLException {
        this.databaseService.insertContestant(jsonRequest);
        return Response.ok("Contestant registered").build();
    }

}
