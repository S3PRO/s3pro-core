package com.s3procore.core.converter;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Abstract class that provides logic for converting object from one class to another.
 */
public abstract class AbstractConverter<SOURCE, TARGET> implements Converter<SOURCE, TARGET> {

    /**
     * Returns new instance of TARGET class that should be used in TARGET convert method.
     * */
    protected abstract TARGET generateTarget();

    @Override
    public TARGET convert(SOURCE source) {
        if (source == null) {
            return null;
        }
        TARGET result = generateTarget();
        convert(source, result);
        return result;
    }

    @Override
    public List<TARGET> convert(Collection<SOURCE> sources) {
        return sources.stream().map(this::convert).collect(toList());
    }
}
