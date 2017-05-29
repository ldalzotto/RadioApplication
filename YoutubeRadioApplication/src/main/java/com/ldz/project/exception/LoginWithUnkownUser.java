package com.ldz.project.exception;

/**
 * Created by Loic on 29/05/2017.
 */
public class LoginWithUnkownUser extends RuntimeException{

    public LoginWithUnkownUser(String message, Throwable cause){
        super(message, cause);
    }

}
