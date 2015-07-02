package com.adobe.aod.monitoring.api;

/**
 *
 */
public interface Receiver <T> {

    void receive(T t);
}
