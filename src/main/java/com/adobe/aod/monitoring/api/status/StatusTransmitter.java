package com.adobe.aod.monitoring.api.status;

import com.adobe.aod.monitoring.api.Receiver;
import com.adobe.aod.monitoring.api.Transmitter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class StatusTransmitter implements Transmitter<Status> {

    List<Receiver<Status>> receivers = new ArrayList<Receiver<Status>>();

    public void addReceiver(Receiver<Status> receiver) {
        receivers.add(receiver);
    }

    public void transmit(Status status) {
        for(Receiver<Status> receiver : receivers) {
            receiver.receive(status);
        }
    }

    public abstract Status prepareStatus(Object o);
}
