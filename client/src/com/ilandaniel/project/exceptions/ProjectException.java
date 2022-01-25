package com.ilandaniel.project.exceptions;

/**
 * custom app exception
 */
public class ProjectException extends Exception {
    public ProjectException(String msg) {
        super(msg);
    }

    public ProjectException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
