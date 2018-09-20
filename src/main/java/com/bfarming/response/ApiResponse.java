package com.bfarming.response;


public class ApiResponse<V> {


    AccessDetails accessDetails;
    ResponseStatus responseStatus;
    Object data;

    public ApiResponse() {
    }

    public ApiResponse(V data) {
        this(data, null, null, null);
    }

    public ApiResponse(ResponseStatus response) {
        this(null, null, response);
    }

    public ApiResponse(V data, ResponseStatus response, String wrapperName) {
        this(data, null, response, wrapperName);
    }

    public ApiResponse(V data, ResponseStatus response) {
        this(data, null, response);
    }

    public ApiResponse(V data, AccessDetails accessDetails, ResponseStatus responseStatus) {
        this.data = (data != null ? data : null);
        this.accessDetails = accessDetails;
        this.responseStatus = responseStatus;
    }

    public ApiResponse(V data, AccessDetails accessDetails, ResponseStatus responseStatus, String wrapperName) {
        this.data = (data != null ? new DataWrapper<V>(data, wrapperName) : null);
        this.accessDetails = accessDetails;
        this.responseStatus = responseStatus;
    }

    /**
     * @return the data
     */

    public Object getData() {
        return data;
    }


    public void setData(Object data) {
        this.data = data;
    }

    public AccessDetails getAccessDetails() {
        return accessDetails;
    }

    public void setAccessDetails(AccessDetails accessDetails) {
        this.accessDetails = accessDetails;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}

