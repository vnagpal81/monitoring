package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.jcr.RepositoryStatusWriter;
import com.adobe.aod.monitoring.api.status.StatusWriter;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Map;

/**
 *
 */
public class AdHocTask extends BaseTask {

    private StatusWriter statusWriter;

    public AdHocTask(String name, Payload payload, StatusWriter statusWriter) {
        super(name, payload);
        this.statusWriter = statusWriter;
    }

    @Override
    public void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus) {
        statusWriter.write(newStatus);
    }
}
