package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.status.Status;

/**
 *  {@link com.adobe.aod.monitoring.api.status.Status} of a {@link Task}
 */
public class TaskStatus extends Status {

    /**
     * printable task representation
     */
    private TaskInfo taskInfo;

    public TaskInfo taskInfo() {
        return taskInfo;
    }

    public TaskStatus taskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        return this;
    }

    @Override
    public TaskStatus value(String value) {
        return (TaskStatus) super.value(value);
    }

    @Override
    public TaskStatus utc(long utc) {
        return (TaskStatus) super.utc(utc);
    }

    public TaskStatus() {}

    public TaskStatus(Status status) {
        this.value(status.value()).utc(status.utc());
    }
}
