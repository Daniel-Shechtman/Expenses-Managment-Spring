package com.ilandaniel.project.interfaces;

/**
 * All classes that using the validate method needs to implement this interface
 */
public interface IValidator {
    /**
     * Validate method that receives an object and return all the errors that occurs.
     */
    String validate(Object object);
}
