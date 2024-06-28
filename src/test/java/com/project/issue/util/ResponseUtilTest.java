package com.project.issue.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseUtilTest {

    private final ResponseUtil responseUtil = new ResponseUtil();

    @Test
    public void testGenerateResponse() {
        String message = "Success";
        HttpStatus status = HttpStatus.OK;
        Object data = "Test Data";

        ResponseEntity<Object> responseEntity = responseUtil.generateResponse(message, status, data);

        assertEquals(status, responseEntity.getStatusCode());

        ResponseUtil.ApiResponse apiResponse = (ResponseUtil.ApiResponse) responseEntity.getBody();
        assert apiResponse != null;
        assertEquals(message, apiResponse.getMessage());
        assertEquals(status, apiResponse.getStatus());
        assertEquals(data, apiResponse.getData());
    }

    @Test
    public void testGenerateErrorResponse() {
        String message = "Error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object data = "Error Details";

        ResponseEntity<Object> responseEntity = responseUtil.generateResponse(message, status, data);

        assertEquals(status, responseEntity.getStatusCode());

        ResponseUtil.ApiResponse apiResponse = (ResponseUtil.ApiResponse) responseEntity.getBody();
        assert apiResponse != null;
        assertEquals(message, apiResponse.getMessage());
        assertEquals(status, apiResponse.getStatus());
        assertEquals(data, apiResponse.getData());
    }
}
