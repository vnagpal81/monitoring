package com.adobe.aod.monitoring.api.task;


import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.Receiver;
import com.adobe.aod.monitoring.api.status.Status;

/**
 *
 */
public abstract class ObservingTask extends BaseTask implements Receiver<Status> {

    public ObservingTask(String name, Payload payload) {
        super(name, payload);
    }

    //set active
    //set status
    public abstract void receive(Status status);

}
