package com.adobe.aod.monitoring.api.sling;

import com.adobe.aod.monitoring.api.Monitor;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Component(metatype = false)
@Service
public class MonitorAdapterFactory implements AdapterFactory {

    private static final Logger log = LoggerFactory.getLogger(MonitorAdapterFactory.class);

    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = {
            Monitor.class.getName()
    };

    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = {
            Resource.class.getName()
    };


    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        if (adaptable instanceof Resource) {
            Resource resource = (Resource) adaptable;
            ValueMap vm = resource.adaptTo(ValueMap.class);
            String monitorPath = null;
            if(vm.containsKey(Monitor.JCR_PROPERTY_NAME)) {
                monitorPath = vm.get(Monitor.JCR_PROPERTY_NAME, String.class);
            }
            else {
                Resource child = resource.getChild(JcrConstants.JCR_CONTENT);
                if(child != null) {
                    vm = child.adaptTo(ValueMap.class);
                    if(vm.containsKey(Monitor.JCR_PROPERTY_NAME)) {
                        monitorPath = vm.get(Monitor.JCR_PROPERTY_NAME, String.class);
                    }
                }
            }
            if(monitorPath != null) {
                Monitor monitor = new Monitor(resource.getResourceResolver());
                monitor.setPath(monitorPath);
                return (AdapterType) monitor;
            }
            log.warn("Property 'monitor' missing on ", resource.getPath());
            return null;
        } else {
            log.warn("Unable to handle adaptable {}", adaptable.getClass().getName());
            return null;
        }
    }
}
