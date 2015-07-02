package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.status.NoStatus;
import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.status.Status;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class BaseTask implements Task {

    public BaseTask(String name, Payload payload) {
        this.name = name;
        this.payload = payload;
    }

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private TaskStatus status = new NoStatus();
    private boolean active = false;
    private List<Status> activity = new ArrayList<Status>() {{
        this.add(status);
    }};

    public TaskStatus getCurrentStatus() {
        return (TaskStatus)status;
    }

    public List<Status> getActivity() {
        return activity;
    }

    public boolean isRunning() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStatus(TaskStatus status) {
        onStatusChange(this.status, status);
        this.status = status;
        activity.add(status);
    }

    public abstract void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus);

    public TaskInfo getInfo() {
        return new TaskInfo().name(this.name).payload((String)getPayload().value());
    }
}
