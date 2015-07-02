package com.adobe.aod.monitoring.api.jcr;

import com.adobe.aod.monitoring.api.Payload;

/**
 *
 */
public class JCRPathPayload implements Payload<String> {

    private String path;

    public JCRPathPayload() {}
    public JCRPathPayload(String path) {
        this.path = path;
    }

    public String value() {
        return path;
    }
}
