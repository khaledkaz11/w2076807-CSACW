package com.khaledfaraj.csacw;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class NewMain {

    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static HttpServer startServer() {
        ResourceConfig config = new ResourceConfig()
                .packages("com.khaledfaraj.csacw")
                .register(JacksonFeature.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
        System.out.println("Server running at " + BASE_URI);
        System.out.println("Press Enter to stop...");
        System.in.read();
        server.shutdownNow();
    }
}