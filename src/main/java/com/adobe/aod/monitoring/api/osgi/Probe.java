package com.adobe.aod.monitoring.api.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 *
 */
public abstract class Probe implements InvocationHandler {

    private Object actualService;
    private ServiceRegistration proxiedServiceRegistration;

    private ServiceRegistration hookRegistration;

    private BundleContext bundleContext;
    private ServiceReference serviceReference;

    public Probe() {

    }

    public void setup(BundleContext bundleContext, ServiceReference serviceReference) {
        actualService = bundleContext.getService(serviceReference);
        this.bundleContext = bundleContext;
        this.serviceReference = serviceReference;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(actualService == null) {
            //error
        }
        beforeInvoke(method, args);
        Object returnValue = method.invoke(actualService, args);
        afterInvoke(method, args, returnValue);
        return returnValue;
    }

    protected abstract void beforeInvoke(Method method, Object[] args) throws Throwable;
    protected abstract void afterInvoke(Method method, Object[] args, Object returnValue) throws Throwable;
    public void cleanup() {
        if(proxiedServiceRegistration != null) {
            try {
                proxiedServiceRegistration.unregister();
            } catch (Exception e) {

            }
        }
        if(hookRegistration != null) {
            try {
                hookRegistration.unregister();
            } catch (Exception e) {

            }
        }

        Properties properties = ProbeHook.buildProps(serviceReference);
        properties.remove(ProbeHook.PROXY_PROPERTY_NAME);
        bundleContext.registerService((String[]) serviceReference.getProperty(Constants.OBJECTCLASS), actualService, properties);
    }

    public ServiceRegistration getProxiedServiceRegistration() {
        return proxiedServiceRegistration;
    }

    public void setProxiedServiceRegistration(ServiceRegistration proxiedServiceRegistration) {
        this.proxiedServiceRegistration = proxiedServiceRegistration;
    }

    public ServiceRegistration getHookRegistration() {
        return hookRegistration;
    }

    public void setHookRegistration(ServiceRegistration hookRegistration) {
        this.hookRegistration = hookRegistration;
    }
}
