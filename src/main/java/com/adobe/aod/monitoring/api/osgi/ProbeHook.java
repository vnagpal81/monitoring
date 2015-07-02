package com.adobe.aod.monitoring.api.osgi;

import org.osgi.framework.*;
import org.osgi.framework.hooks.service.EventHook;
import org.osgi.framework.hooks.service.FindHook;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 */
public class ProbeHook implements EventHook, FindHook {

    private BundleContext bc;
    private Probe probeInstance;
    public static final String PROXY_PROPERTY_NAME = "proxied";

    public ProbeHook(BundleContext bc, Probe probe) {
        this.bc = bc;
        this.probeInstance = probe;
    }

    public void find(BundleContext bc, String name, String filter,
                     boolean allServices, Collection references) {
        try {
            if (bc.getBundle().getBundleId() == 0) {
                return;
            }

            Iterator iterator = references.iterator();

            while (iterator.hasNext()) {
                ServiceReference sr = (ServiceReference) iterator.next();

                if (sr.getProperty(PROXY_PROPERTY_NAME) == null) {
                    iterator.remove();
                }
            }
        } catch (Exception ex) {
        }
    }

    public void event(ServiceEvent event, Collection contexts) {
        final ServiceReference serviceReference = event.getServiceReference();

        if (serviceReference.getProperty(PROXY_PROPERTY_NAME) == null) {
            switch (event.getType()) {
                case ServiceEvent.REGISTERED: {
                    ServiceRegistration proxiedServiceRegistration = proxyService(serviceReference, bc, probeInstance);
                    probeInstance.setProxiedServiceRegistration(proxiedServiceRegistration);
                    break;
                }
                case ServiceEvent.UNREGISTERING: {
                    break;
                }
                case ServiceEvent.MODIFIED:
                case ServiceEvent.MODIFIED_ENDMATCH: {
                    break;
                }
            }
        }
        else {
            switch (event.getType()) {
                case ServiceEvent.REGISTERED: {
                    break;
                }
                case ServiceEvent.UNREGISTERING: {
                    contexts.clear();
                    break;
                }
                case ServiceEvent.MODIFIED:
                case ServiceEvent.MODIFIED_ENDMATCH: {
                    break;
                }
            }
        }
    }

    public static Properties buildProps(ServiceReference serviceReference) {
        String[] propertyKeys = serviceReference.getPropertyKeys();
        Properties properties = new Properties();
        for (String string : propertyKeys) {
            properties.put(string, serviceReference.getProperty(string));
        }
        return properties;
    }

    private static String[] toString(Class<?>[] interfaces) {
        String[] names = new String[interfaces.length];
        int i = 0;
        for (Class clazz : interfaces) {
            names[i++] = clazz.getName();
        }
        return names;
    }

    private static Class<?>[] toClass(String[] interfaces, Bundle bl) {
        Class<?>[] names = new Class<?>[interfaces.length];
        int i = 0;
        for (String clazz : interfaces) {
            try {
                names[i++] = bl.loadClass(clazz);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return names;
    }

    public static ServiceRegistration proxyService(ServiceReference serviceReference, BundleContext bundleContext, Probe probe) {
        Properties properties = buildProps(serviceReference);
        String[] interfaces = (String[]) serviceReference.getProperty(Constants.OBJECTCLASS);
        Class<?>[] toClass = toClass(interfaces, bundleContext.getBundle());
        properties.put(PROXY_PROPERTY_NAME, true);

        Object proxyService = Proxy.newProxyInstance(probe.getClass().getClassLoader(), toClass, probe);
        return bundleContext.registerService(interfaces, proxyService, properties);
    }
}