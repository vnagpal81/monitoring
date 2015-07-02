package com.adobe.aod.monitoring.api.status;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *  Represents an entity's state in the system.<br/>
 *  All time values are in UTC for unambiguity and ease of comparison.
 */
public class Status implements Comparable<Status> {

    /**
     * Status value as string
     */
    private String value;

    /**
     * Moment in time when state was examined (always in UTC)
     * By default is the object instantiation timestamp.
     * Can be overridden and set explicitly in case of distributed systems and states across JVMs
     */
    private long utc;

    public Status() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utc = cal.getTimeInMillis();
    }

    public String value() {
        return value;
    }

    public Status value(String value) {
        this.value = value;
        return this;
    }

    public long utc() {
        return utc;
    }

    public Status utc(long utc) {
        this.utc = utc;
        return this;
    }

    public int compareTo(Status o) {
        if(utc > o.utc) {
            return 1;
        }
        else if(utc == o.utc()) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
