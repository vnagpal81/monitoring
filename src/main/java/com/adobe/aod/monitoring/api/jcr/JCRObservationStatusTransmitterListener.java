package com.adobe.aod.monitoring.api.jcr;

import com.adobe.aod.monitoring.api.status.StatusTransmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

/**
 *
 */
public abstract class JCRObservationStatusTransmitterListener extends StatusTransmitter implements EventListener {

    private static final Logger log = LoggerFactory.getLogger(JCRObservationStatusTransmitterListener.class);

    private ObservationManager observationManager;

    public JCRObservationStatusTransmitterListener(ObservationManager observationManager, int eventTypes, String path) {
        try {
            observationManager.addEventListener(
                    this, //handler
                    eventTypes, //binary combination of event types
                    path, //path
                    true, //is Deep?
                    null, //uuids filter
                    null, //nodetypes filter
                    false);
            this.observationManager = observationManager;
        } catch (RepositoryException e){
            log.error("Error registering event Listener for " + path, e);
        }
    }

    public void onEvent(EventIterator events) {
        transmit(prepareStatus(events));
        try {
            observationManager.removeEventListener(this);
        } catch (RepositoryException e) {
            log.error("Error removing event listener", e);
        }
    }

}
