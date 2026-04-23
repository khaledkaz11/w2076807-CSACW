package com.khaledfaraj.csacw.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Map<String, Object> getApiInfo() {
        Map<String, Object> response = new HashMap<>();

        response.put("name", "Smart Campus Sensor Room Management API");
        response.put("version", "v1");
        response.put("adminContact", "wxxxxxxxx@westminster.ac.uk");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");

        response.put("links", links);

        return response;
    }
}