package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.Trackable;

/**
 *   Contract of what a unit of work looks like
 */
public interface Task extends Trackable {

    boolean isRunning();

    /**
     * A task needs to operate on something
     *
     * @return the payload which the task is running on
     */
    Payload getPayload();

    /**
     * Print friendly task representation
     *
     * @return basic info about the task
     */
    TaskInfo getInfo();

    void setStatus(TaskStatus status);
}
