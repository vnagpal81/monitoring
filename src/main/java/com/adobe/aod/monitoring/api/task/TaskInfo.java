package com.adobe.aod.monitoring.api.task;

/**
 *  Print friendly task representation for display/logging purposes
 */
public class TaskInfo {

    /**
     * Name of the task
     */
    private String name;

    /**
     * Payload's String representation
     */
    private String payload;

    public String name() {
        return name;
    }

    public TaskInfo name(String name) {
        this.name = name;
        return this;
    }

    public String payload() {
        return payload;
    }

    public TaskInfo payload(String payload) {
        this.payload = payload;
        return this;
    }
}
