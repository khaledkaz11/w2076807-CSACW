package com.khaledfaraj.csacw.mappers;

import com.khaledfaraj.csacw.exceptions.RoomNotEmptyException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        System.out.println("RoomNotEmptyExceptionMapper was called");

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"status\":409,\"error\":\"Conflict\",\"message\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}