package com.bfarming.response;

import java.util.Map;

/**
 * @author sachin
 *
 */
public class AccessDetails {
    Map<String,String> accessMap;

    public AccessDetails() {
    }

    public Map<String, String> getAccessMap() {
        return accessMap;
    }

    public void setAccessMap(Map<String, String> accessMap) {
        this.accessMap = accessMap;
    }
}

