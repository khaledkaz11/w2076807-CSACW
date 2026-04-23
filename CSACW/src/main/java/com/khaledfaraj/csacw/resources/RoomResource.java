package com.khaledfaraj.csacw.resources;

import com.khaledfaraj.csacw.data.DataStore;
import com.khaledfaraj.csacw.exceptions.RoomNotEmptyException;
import com.khaledfaraj.csacw.models.ErrorResponse;
import com.khaledfaraj.csacw.models.Room;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(DataStore.rooms.values());
        return Response.ok(rooms).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Room body is required"))
                    .build();
        }

        if (room.getId() == null || room.getId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Room id is required"))
                    .build();
        }

        if (room.getName() == null || room.getName().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(400, "Bad Request", "Room name is required"))
                    .build();
        }

        if (DataStore.rooms.containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(409, "Conflict", "Room with this id already exists"))
                    .build();
        }

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        DataStore.rooms.put(room.getId(), room);

        return Response.created(URI.create("/api/v1/rooms/" + room.getId()))
                .entity(room)
                .build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Room not found"))
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Room not found"))
                    .build();
        }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room cannot be deleted because it still has sensors assigned");
        }

        DataStore.rooms.remove(roomId);

        return Response.ok(new ErrorResponse(200, "OK", "Room deleted successfully")).build();
    }
}