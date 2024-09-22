package com.app.weather.utility.htttp;

public class RequestHandler {

    public static <T> T handleRequest(RequestHandlerInterface<T> handler) {
        try {
            return handler.process();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while processing the request: " + e.getMessage(), e);
        }
    }

}
