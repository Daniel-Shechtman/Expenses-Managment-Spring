package com.ilandaniel.project.exceptions;


public class ProjectException extends Exception {
    public ProjectException(String msg) {
        super(msg);
    }

    public ProjectException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
