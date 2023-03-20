package com.s3procore.core.converter;

import java.util.Collection;

/**
 * Base interface for converting object from one class to another.
 */
public interface Converter<SOURCE, TARGET> {

    /**
     * Converts source object to target object. Target must be created.
     *
     * @param source - object to convert
     * @return converted object of target class
     */
    TARGET convert(SOURCE source);

    /**
     * Converts source object to target object.
     *
     * @param source - object to convert
     * @param target - target object where result will be stored
     */
    void convert(SOURCE source, TARGET target);

    /**
     * Converts Collection of source objects to Collection target objects.
     * @param source - object to convert
     * @return converted object of target class
     */
    Collection<TARGET> convert(Collection<SOURCE> source);
}
