package com.spring.redditclone.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public TokenExpiredException(String exMessage) {
        super(exMessage);
    }
}
