package com.adobe.aod.monitoring.api;

import com.adobe.aod.monitoring.api.status.Status;

/**
 *
 */
public interface MonitoringService {

    void logStatus(Status status);
}
