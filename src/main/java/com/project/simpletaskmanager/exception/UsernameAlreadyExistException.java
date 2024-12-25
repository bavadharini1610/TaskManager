package com.project.simpletaskmanager.exception;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException(String message){
        super(message);
    }
}
