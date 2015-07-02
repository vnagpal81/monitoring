package com.adobe.aod.monitoring.api.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class BaseAsyncStatusWriter implements AsyncStatusWriter, Runnable {
    private List<Status> statusList = Collections.synchronizedList(new ArrayList<Status>());

    public void write(Status status) {
        statusList.add(status);
    }

    private boolean writing;
    private int sleepTime;
    private int waitTime;

    private Thread writer = new Thread(this);

    protected void init() {
        writing = true;
        writer.start();
    }

    public void run() {
        while(writing) {
            if(statusList.isEmpty()) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    writing = false;
                }
            }
            else {
                Status status = statusList.remove(0);
                doWrite(status);
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    writing = false;
                }
            }
        }
    }

    public void write(Status status, boolean async) {
        if(async) {
            write(status);
        }
        else {
            doWrite(status);
        }
    }

    public abstract void doWrite(Status status);

    public boolean isWriting() {
        return writing;
    }

    public void setWriting(boolean writing) {
        this.writing = writing;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
