package com.adobe.aod.monitoring.api.status;

/**
 *
 */
public interface AsyncStatusWriter extends StatusWriter {

    void write(Status status, boolean async);
}
