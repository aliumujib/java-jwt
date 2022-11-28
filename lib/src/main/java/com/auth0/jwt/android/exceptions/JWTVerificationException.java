package com.auth0.jwt.android.exceptions;

/**
 * Parent to all the exception thrown while verifying a JWT.
 */
public class JWTVerificationException extends RuntimeException {
    public JWTVerificationException(String message) {
        this(message, null);
    }

    public JWTVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
