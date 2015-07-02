package com.adobe.aod.monitoring.api;

import com.adobe.aod.monitoring.api.status.Status;

import java.util.List;

/**
 *   A trackable entity represents an object in flux and always has a {@link com.adobe.aod.monitoring.api.task.TaskStatus} and an optional activity chain.
 */
public interface Trackable {

    /**
     * Current state of the object being tracked
     *
     * @return TaskStatus
     */
    Status getCurrentStatus();
    List<Status> getActivity();
}
