package com.adobe.aod.monitoring.internal;

import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.status.StatusWriter;
import com.adobe.aod.monitoring.api.task.Task;

/**
 *
 */
public interface TaskFactory {

    Task buildTask(TaskType taskType, String taskName, Payload payload, StatusWriter statusWriter);
}
