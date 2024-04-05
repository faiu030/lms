package com.lms.uploadusers.exception;


public class UserManagementException extends RuntimeException {
    public UserManagementException() {
        super();
    }

    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserManagementException(Throwable cause) {
        super(cause);
    }
}
