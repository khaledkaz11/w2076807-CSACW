package com.khaledfaraj.csacw.resources;

import com.khaledfaraj.csacw.data.DataStore;
import com.khaledfaraj.csacw.exceptions.SensorUnavailableException;
import com.khaledfaraj.csacw.models.ErrorResponse;
import com.khaledfaraj.csacw.models.Sensor;
import com.khaledfaraj.csacw.models.SensorReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getAllReadings() {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Sensor not found"))
                    .build();
        }

        List<SensorReading> readings = DataStore.sensorReadings.get(sensorId);
        if (readings == null) {
            readings = new ArrayList<>();
        }

        return Response.ok(readings).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Sensor not found"))
                    .build();
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(
                    "Sensor is currently under maintenance and cannot accept new readings"
            );
        }

        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Reading body is required"))
                    .build();
        }

        if (reading.getId() == null || reading.getId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Reading id is required"))
                    .build();
        }

        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        List<SensorReading> readings = DataStore.sensorReadings.get(sensorId);
        if (readings == null) {
            readings = new ArrayList<>();
            DataStore.sensorReadings.put(sensorId, readings);
        }

        for (SensorReading existing : readings) {
            if (existing.getId() != null && existing.getId().equalsIgnoreCase(reading.getId())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse(409, "Conflict", "Reading with this id already exists"))
                        .build();
            }
        }

        readings.add(reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.created(URI.create("/api/v1/sensors/" + sensorId + "/readings/" + reading.getId()))
                .entity(reading)
                .build();
    }
}