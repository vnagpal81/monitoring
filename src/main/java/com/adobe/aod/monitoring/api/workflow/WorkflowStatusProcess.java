package com.adobe.aod.monitoring.api.workflow;

import com.adobe.aod.monitoring.api.Monitor;
import com.adobe.aod.monitoring.api.jcr.RepositoryStatusWriter;
import com.adobe.aod.monitoring.api.status.Status;
import com.adobe.aod.monitoring.api.status.StatusWriter;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.JcrResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

/**
 *
 */
@Component(componentAbstract = true, metatype = false)
public abstract class WorkflowStatusProcess implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(WorkflowStatusProcess.class);

    @Reference
    private JcrResourceResolverFactory jcrResourceResolverFactory;

    protected ResourceResolver getResourceResolver(final Session session) {
        try {
            //Map map = new HashMap();
            //map.put( "user.jcr.session", session);
            return jcrResourceResolverFactory.getResourceResolver(session);
        } catch (Exception e) {
            log.error("Error getting resource resolver", e);
            return null;
        }
    }

    protected Resource getPayloadResource(WorkItem item, WorkflowSession session) {
        ResourceResolver resourceResolver = getResourceResolver(session.getSession());
        return resourceResolver.getResource(item.getWorkflowData().getPayload().toString());
    }

    protected String getMonitorPath(Resource resource) {
        ValueMap vm = resource.adaptTo(ValueMap.class);
        if(vm.containsKey(Monitor.JCR_PROPERTY_NAME)) {
            return vm.get(Monitor.JCR_PROPERTY_NAME, String.class);
        }
        if(resource.getParent() == null) {
            return null;
        }
        return getMonitorPath(resource.getParent());
    }
}
