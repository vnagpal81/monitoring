package com.adobe.aod.monitoring.internal;

import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.status.Status;
import com.adobe.aod.monitoring.api.status.StatusWriter;
import com.adobe.aod.monitoring.api.task.*;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 *
 */
@Component(metatype = false)
@Service
public class TaskFactoryImpl implements TaskFactory {

    public Task buildTask(TaskType taskType, String taskName, Payload payload, StatusWriter statusWriter) {
        Task task = null;
        switch (taskType) {
            case ADHOC:
                task = new AdHocTask(taskName, payload, statusWriter);
                break;
            case POLL:
                /*task = new PollingTask() {
                    @Override
                    public void poll() {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }*/
                break;
            case OBSERVE_JCR:
                /*task = new ObservingTask() {
                    @Override
                    public void receive(Status status) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }*/
                break;
            case OBSERVE_OSGI:
                /*task = new ObservingTask() {
                    @Override
                    public void receive(Status status) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }*/
                break;
            case PROXY_SERVICE:
                /*task = new ProbeTask() {
                    @Override
                    public void onStatusChange(TaskStatus oldStatus, TaskStatus newStatus) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }*/
                break;
        }
        return task;
    }
}
