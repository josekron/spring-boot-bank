package com.jaherrera.springbootbank.exception;

/**
 * StarlingAPIException: extends from RuntimeException because it will handle unchecked exceptions
 * from Starling API such as bad responses.
 */
public class StarlingAPIException extends RuntimeException{

    private int statusCode;
    private String message;
    private String description;

    public StarlingAPIException(int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public StarlingAPIException(int statusCode, String message, String description) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }

    // Getters:

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
