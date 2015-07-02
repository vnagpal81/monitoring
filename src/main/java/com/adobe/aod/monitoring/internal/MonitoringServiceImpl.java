package com.adobe.aod.monitoring.internal;

import com.adobe.aod.monitoring.api.MonitoringService;
import com.adobe.aod.monitoring.api.jcr.JCRPathPayload;
import com.adobe.aod.monitoring.api.status.Status;
import com.adobe.aod.monitoring.api.status.StatusWriter;
import com.adobe.aod.monitoring.api.task.Task;
import com.adobe.aod.monitoring.api.task.TaskStatus;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

/**
 *
 */
@Component
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Reference
    private StatusWriter statusWriter;

    @Reference
    private TaskFactory taskFactory;


    public void logStatus(Status status) {
        /*TaskStatus taskStatus = (TaskStatus)status;
        Task task = taskFactory.buildTask(TaskType.ADHOC, taskStatus.taskInfo().name(), new JCRPathPayload(taskStatus.taskInfo().payload()), statusWriter);
        task.setStatus(taskStatus);*/

        statusWriter.write(status);
    }
}
