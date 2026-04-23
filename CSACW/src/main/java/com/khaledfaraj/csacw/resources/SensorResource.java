package com.khaledfaraj.csacw.resources;

import com.khaledfaraj.csacw.data.DataStore;
import com.khaledfaraj.csacw.exceptions.LinkedResourceNotFoundException;
import com.khaledfaraj.csacw.models.ErrorResponse;
import com.khaledfaraj.csacw.models.Room;
import com.khaledfaraj.csacw.models.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = new ArrayList<>(DataStore.sensors.values());

        if (type == null || type.isBlank()) {
            return sensors;
        }

        List<Sensor> filteredSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return filteredSensors;
    }

    @GET
    @Path("/{sensorId}")
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Sensor not found"))
                    .build();
        }

        return Response.ok(sensor).build();
    }

    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Sensor body is required"))
                    .build();
        }

        if (sensor.getId() == null || sensor.getId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Sensor id is required"))
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "roomId is required"))
                    .build();
        }

        if (DataStore.sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(409, "Conflict", "Sensor with this id already exists"))
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Referenced room does not exist");
        }

        if (sensor.getStatus() == null || sensor.getStatus().isBlank()) {
            sensor.setStatus("ACTIVE");
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        if (!room.getSensorIds().contains(sensor.getId())) {
            room.getSensorIds().add(sensor.getId());
        }

        return Response.created(URI.create("/api/v1/sensors/" + sensor.getId()))
                .entity(sensor)
                .build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}