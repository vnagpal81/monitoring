package com.adobe.aod.monitoring.api.osgi;

import com.adobe.aod.monitoring.api.status.StatusTransmitter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 */
public abstract class OSGiEventStatusTransmitterHandler extends StatusTransmitter implements EventHandler {

    protected ServiceRegistration serviceRegistration;

    public OSGiEventStatusTransmitterHandler(BundleContext bundleContext, String[] topics) {
        Dictionary props = new Hashtable();
        props.put(EventConstants.EVENT_TOPIC, topics);
        serviceRegistration = bundleContext.registerService(EventHandler.class.getName(), this, props);
    }

    public void handleEvent(Event event) {
        transmit(prepareStatus(event));
    }

}
