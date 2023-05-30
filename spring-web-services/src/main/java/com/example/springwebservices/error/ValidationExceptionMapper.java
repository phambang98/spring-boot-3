package com.example.springwebservices.error;

import com.example.springwebservices.model.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LoggerFactory.getLogger(ValidationExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        log.info("Building response for Restful exception");
        Response.Status status = Response.Status.OK;
        String errorMessage = "";

        if (exception instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> set = ((ConstraintViolationException) exception).getConstraintViolations();

            for (ConstraintViolation<?> aSet : set) {
                errorMessage += aSet.getMessage() + " | ";
            }
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(errorMessage);
            return Response.status(status).header("content-type", MediaType.APPLICATION_XML).entity(errorResponse)
                    .build();
        }
        return Response.status(status).header("content-type", MediaType.APPLICATION_XML)
                .build();
    }

}
