package com.project.lotterywidget;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Opens a webserver for clients to connect to.
 * Used for transmitting data until next raffle.
 */
public class WebsocketServer extends WebSocketServer {

    private static final int TCP_PORT = 4444;

    private final ScheduledFuture<?> scheduledFuture;

    /**
     * Constructor
     *
     * @param scheduledFuture the scheduler for future raffles
     */
    public WebsocketServer(ScheduledFuture<?> scheduledFuture) {
        super(new InetSocketAddress(TCP_PORT));
        this.scheduledFuture = scheduledFuture;
    }

    /**
     * Is called when a client opens up a new connection
     *
     * @param conn      client connection
     * @param handshake client handshake
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    /**
     * When connection is closed
     *
     * @param conn   client connection
     * @param code   closing code
     * @param reason reason for connection close
     * @param remote is the connection remote
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    /**
     * Called when a client sends a message. After the server receives the ping
     * it sends back the time until the next raffle.
     *
     * @param conn    clients connection
     * @param message clients message
     */
    @Override
    public void onMessage(WebSocket conn, String message) {
        conn.send(String.valueOf(this.scheduledFuture.getDelay(SECONDS)));
    }

    /**
     * Prints the stack trace of the error
     *
     * @param conn client connection
     * @param ex   exception that happened
     */
    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }
}
