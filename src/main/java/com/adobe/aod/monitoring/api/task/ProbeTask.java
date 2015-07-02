package com.adobe.aod.monitoring.api.task;

import com.adobe.aod.monitoring.api.Payload;
import com.adobe.aod.monitoring.api.osgi.Probe;
import com.adobe.aod.monitoring.api.osgi.ProbeHook;
import org.osgi.framework.*;
import org.osgi.framework.hooks.service.EventHook;
import org.osgi.framework.hooks.service.FindHook;

import java.util.Properties;

/**
 *
 */
public abstract class ProbeTask extends BaseTask {

    public ProbeTask(String name, Payload payload, BundleContext bundleContext, Class interfaceClazz, Properties properties, Probe probe) {
        super(name, payload);

        try {
            String filterStr = null;
            if(properties != null) {
                filterStr = "(";
                for(String k : properties.stringPropertyNames()) {
                    filterStr = filterStr + k + "=" + properties.getProperty(k);
                }
                filterStr = filterStr + ")";
            }
            else {
                properties = new Properties();
            }
            ServiceReference[] serviceReferences = bundleContext.getServiceReferences(interfaceClazz.getName(), filterStr);

            if(serviceReferences.length > 1) {
                //error
            }

            probe.setup(bundleContext, serviceReferences[0]);

            ServiceRegistration serviceRegistration = ProbeHook.proxyService(serviceReferences[0], bundleContext, probe);

            properties.setProperty(Constants.OBJECTCLASS, interfaceClazz.getName());
            ServiceRegistration hookRegistration = bundleContext.registerService(
                    new String[]{FindHook.class.getName(), EventHook.class.getName()},
                    new ProbeHook(bundleContext, probe), properties);

            probe.setProxiedServiceRegistration(serviceRegistration);
            probe.setHookRegistration(hookRegistration);

        } catch (InvalidSyntaxException e) {
        }
    }
}
