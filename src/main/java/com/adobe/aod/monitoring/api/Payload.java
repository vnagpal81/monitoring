package com.adobe.aod.monitoring.api;

/**
 *   Wrapper around an {@link Object} to mark payload of a {@link com.adobe.aod.monitoring.api.task.Task}
 */
public interface Payload<T> {

    /**
     * @return Value of the payload as type defined by class definition
     */
    T value();
}
