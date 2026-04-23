package com.khaledfaraj.csacw.mappers;

import com.khaledfaraj.csacw.exceptions.LinkedResourceNotFoundException;
import com.khaledfaraj.csacw.models.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                422,
                "Unprocessable Entity",
                exception.getMessage()
        );

        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}