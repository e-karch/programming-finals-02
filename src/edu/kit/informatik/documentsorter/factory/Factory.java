package edu.kit.informatik.documentsorter.factory;

import edu.kit.informatik.documentsorter.exceptions.InitialisationException;

/**
 * Interface for factories of classes.
 *
 *
 * @param <T> the type of the class of which an object is created
 * @author uexnb
 * @version 1.0
 */
public interface Factory<T> {

    /**
     * Creates an object.
     *
     * @param arguments the arguments to create the object with
     * @throws InitialisationException if there is a problem creating the object
     * @return the object that is created
     */
    T create(String[] arguments) throws InitialisationException;
}
