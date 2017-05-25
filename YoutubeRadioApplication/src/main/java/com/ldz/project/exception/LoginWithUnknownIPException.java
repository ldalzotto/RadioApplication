package com.ldz.project.exception;

/**
 * Created by Loic on 25/05/2017.
 */
public class LoginWithUnknownIPException extends RuntimeException {

    public LoginWithUnknownIPException(String message, Throwable cause){
        super(message, cause);
    }

}
