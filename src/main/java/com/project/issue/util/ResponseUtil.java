package com.project.issue.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        return new ResponseEntity<>(new ApiResponse(message, status, responseObj), status);
    }

    public static class ApiResponse {
        private String message;
        private HttpStatus status;
        private Object data;

        public ApiResponse(String message, HttpStatus status, Object data) {
            this.message = message;
            this.status = status;
            this.data = data;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
