package com.ldz.project.exception;

/**
 * Created by Loic on 29/05/2017.
 */
public class AlreadyRegistered extends RuntimeException {

    public AlreadyRegistered(String message, Throwable cause){
        super(message, cause);
    }

}
