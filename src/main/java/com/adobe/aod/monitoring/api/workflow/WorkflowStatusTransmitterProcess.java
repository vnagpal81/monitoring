package com.adobe.aod.monitoring.api.workflow;

import com.adobe.aod.monitoring.api.status.StatusTransmitter;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

/**
 *
 */
public abstract class WorkflowStatusTransmitterProcess extends StatusTransmitter implements WorkflowProcess {

    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        transmit(prepareStatus(session));
    }

}
