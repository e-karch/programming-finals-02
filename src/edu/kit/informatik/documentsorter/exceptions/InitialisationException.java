package edu.kit.informatik.documentsorter.exceptions;
/**
 * This exception is thrown when there is a problem while initialising a document file.
 *
 * @author uexnb
 * @version 1.0
 */
public class InitialisationException extends Exception {
/**
     * Unique serialVersionUID.
     */
    private static final long serialVersionUID = 12453333357825L;

    /**
     * Constructs a new {@code InitialisationException} with an error message.
     *
     * @param message detailed error message
     */
    public InitialisationException(final String message) {
        super(message);
    }
}
