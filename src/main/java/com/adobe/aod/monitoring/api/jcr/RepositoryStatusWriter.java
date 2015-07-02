package com.adobe.aod.monitoring.api.jcr;

import com.adobe.aod.monitoring.api.status.BaseAsyncStatusWriter;
import com.adobe.aod.monitoring.api.status.Status;
import com.adobe.aod.monitoring.api.task.TaskStatus;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
public class RepositoryStatusWriter extends BaseAsyncStatusWriter {

    private static final Logger log = LoggerFactory.getLogger(RepositoryStatusWriter.class);

    private ResourceResolver resourceResolver;
    private Resource workStatusResource;

    public RepositoryStatusWriter(ResourceResolver resourceResolver, String parentPath, Map<String, String> properties) {
        this.resourceResolver = resourceResolver;
        try {
            workStatusResource = ResourceUtil.getOrCreateResource(resourceResolver,
                    parentPath,
                    JcrConstants.NT_UNSTRUCTURED,
                    JcrConstants.NT_UNSTRUCTURED,
                    true);
            if(properties != null) {
                workStatusResource.adaptTo(ModifiableValueMap.class).putAll(properties);
                resourceResolver.commit();
            }
        } catch (PersistenceException e) {
            log.error("Error creating monitoring resource", e);
        }
        init();
    }

    public void doWrite(Status status) {
        TaskStatus taskStatus = (TaskStatus) status;
        if(workStatusResource != null) {
            try {
                Resource taskResource = ResourceUtil.getOrCreateResource(resourceResolver,
                        workStatusResource.getPath() + "/" + taskStatus.taskInfo().name(),
                        JcrConstants.NT_UNSTRUCTURED,
                        JcrConstants.NT_UNSTRUCTURED,
                        true);
                Resource statusResource = ResourceUtil.getOrCreateResource(resourceResolver,
                        taskResource.getPath() + "/" + taskStatus.utc(),
                        JcrConstants.NT_UNSTRUCTURED,
                        JcrConstants.NT_UNSTRUCTURED,
                        true);
                ModifiableValueMap mvm = statusResource.adaptTo(ModifiableValueMap.class);
                mvm.put("status", taskStatus.value());
                resourceResolver.commit();
            } catch (Exception e) {
                log.error("Error writing status", e);
            }
        }
    }
}
