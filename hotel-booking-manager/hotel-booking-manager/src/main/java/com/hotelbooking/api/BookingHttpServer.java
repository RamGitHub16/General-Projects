package com.hotelbooking.api;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.hotelbooking.model.Booking;
import com.hotelbooking.service.BookingManager;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookingHttpServer {
    private static final int PORT = 8000;
    private static final BookingManager bookingManager = new BookingManager(10);
    private static final Logger logger = Logger.getLogger(BookingHttpServer.class.getName());

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/storeBooking", new StoreBookingHandler());
        server.createContext("/findAvailableRooms", new FindAvailableRoomsHandler());
        server.createContext("/findBookingsByGuest", new FindBookingsByGuestHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
        logger.info("Server started on port " + PORT);
    }

    static class StoreBookingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if ("POST".equals(exchange.getRequestMethod())) {
                    String query = new String(exchange.getRequestBody().readAllBytes());
                    String[] params = query.split("&");
                    String guestName = getValue(params, "guestName");
                    int roomNumber = Integer.parseInt(getValue(params, "roomNumber"));
                    Date date = parseDate(getValue(params, "date"));

                    boolean success = bookingManager.storeBooking(guestName, roomNumber, date);
                    String response = success ? "Booking stored successfully" : "Room is already booked";
                    sendResponse(exchange, success ? 200 : 400, response);
                } else {
                    sendResponse(exchange, 405, "Method Not Allowed");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error handling store booking request", e);
                sendResponse(exchange, 500, "Internal Server Error");
            }
        }
    }

    static class FindAvailableRoomsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if ("GET".equals(exchange.getRequestMethod())) {
                    String query = exchange.getRequestURI().getQuery();
                    Date date = parseDate(getValue(query.split("&"), "date"));

                    List<Integer> availableRooms = bookingManager.findAvailableRooms(date);
                    String response = availableRooms.stream()
                                                    .map(String::valueOf)
                                                    .collect(Collectors.joining(","));
                    sendResponse(exchange, 200, response);
                } else {
                    sendResponse(exchange, 405, "Method Not Allowed");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error handling find available rooms request", e);
                sendResponse(exchange, 500, "Internal Server Error");
            }
        }
    }

    static class FindBookingsByGuestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if ("GET".equals(exchange.getRequestMethod())) {
                    String query = exchange.getRequestURI().getQuery();
                    String guestName = getValue(query.split("&"), "guestName");

                    List<Booking> bookings = bookingManager.findBookingsByGuest(guestName);
                    String response = bookings.stream()
                                              .map(b -> String.format("%s booked room %d on %s", b.getGuestName(), b.getRoomNumber(), b.getDate()))
                                              .collect(Collectors.joining("\n"));
                    sendResponse(exchange, 200, response);
                } else {
                    sendResponse(exchange, 405, "Method Not Allowed");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error handling find bookings by guest request", e);
                sendResponse(exchange, 500, "Internal Server Error");
            }
        }
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format, expected yyyy-MM-dd", e);
        }
    }

    private static String getValue(String[] params, String key) {
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }
        throw new IllegalArgumentException("Missing parameter: " + key);
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

