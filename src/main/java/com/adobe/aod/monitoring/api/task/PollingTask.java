package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.Payload;

/**
 *
 */
public abstract class PollingTask extends BaseTask implements Runnable {

    private boolean polling = false;

    private int intervalMs;

    public PollingTask(String name, Payload payload, int pollingInterval) {
        super(name, payload);
        this.intervalMs = pollingInterval;
    }

    public void run() {
        while(polling) {
            //set active
            //set status
            poll();
            try {
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                polling = false;
            }
        }
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = intervalMs;
    }

    public void startPolling() {
        polling = true;
        new Thread(this).start();
    }

    public void stopPolling() {
        polling = false;
    }

    public abstract void poll();
}
