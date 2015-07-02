package com.adobe.aod.monitoring.api;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.resource.*;

/**
 *  Monitors the progress of {@link Work} and is saved at path
 */
public class Monitor {

    public static final String ROOT_PATH = "/var/monitoring/";
    public static final String JCR_PROPERTY_NAME = "monitor";

    private Work[] work;
    private String path;
    private ResourceResolver resourceResolver;

    public Work[] getWork() {
        return work;
    }

    public void setWork(Work... work) {
        this.work = work;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Monitor startMonitoring(String payload) {
        try {
            Resource monitorRes = ResourceUtil.getOrCreateResource(resourceResolver,
                    path,
                    JcrConstants.NT_UNSTRUCTURED,
                    JcrConstants.NT_UNSTRUCTURED,
                    true);
            ModifiableValueMap mvm = monitorRes.adaptTo(ModifiableValueMap.class);
            mvm.put("payload", payload);
            resourceResolver.commit();
        } catch (PersistenceException e) {

        }
        return this;
    }
    public Monitor stopMonitoring() {
        return this;
    }
    public Monitor pauseMonitoring() {
        return this;
    }

    public Monitor(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }
}