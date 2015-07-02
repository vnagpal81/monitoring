package com.adobe.aod.monitoring.api;

/**
 *
 */
public interface Transmitter<T> {

    void transmit(T t);
    void addReceiver(Receiver<T> receiver);
}
