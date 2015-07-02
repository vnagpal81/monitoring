package com.adobe.aod.monitoring.api;

import com.adobe.aod.monitoring.api.status.NoStatus;
import com.adobe.aod.monitoring.api.status.Status;
import com.adobe.aod.monitoring.api.task.Task;

import java.util.*;

/**
 *   Represents a quantum of Work internally structured as a sequence of {@link com.adobe.aod.monitoring.api.task.Task}s
 */
public abstract class Work extends ArrayList<Task> implements Trackable {

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Work() {}

    public Work(Payload payload) {
        this.payload = payload;
    }

    public Work addTask(Task task) {
        add(task);
        return this;
    }

    public Work addTasks(List<Task> tasks) {
        addAll(tasks);
        return this;
    }

    public void setTasks(List<Task> tasks) {
        clear();
        addAll(tasks);
    }

    public Status getCurrentStatus() {
        for(Task task : this) {
            if(task.isRunning()) {
                return task.getCurrentStatus();
            }
        }
        return new NoStatus();
    }

    public List<Status> getActivity() {
        List<Status> activity = new ArrayList<Status>();
        for(Task task : this) {
            activity.addAll(task.getActivity());
        }
        Collections.sort(activity);
        return activity;
    }
}
