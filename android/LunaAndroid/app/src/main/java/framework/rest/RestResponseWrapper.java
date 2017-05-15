package framework.rest;

import org.luaj.vm2.LuaFunction;

/**
 * Created by macbookair on 22/04/17.
 */

public class RestResponseWrapper {

    private int responseCode;
    private String response;
    private LuaFunction userCallback;

    public RestResponseWrapper(String response, int responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    private RestResponseWrapper(){}

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getStringResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LuaFunction getUserCallback() {
        return   userCallback;
    }

    public void setUserCallback(LuaFunction userCallback) {
        this.userCallback = userCallback;
    }
}
