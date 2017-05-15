package luna.framework.rest;

/**
 * Created by macbookair on 05/05/17.
 */

public abstract class RestBridge {


    public static RestBridge newRestBridge(){
        throw new UnsupportedOperationException("You must implement this method.");
    }
    public abstract void makeRequest(Object properties);
    public abstract RestResponseWrapper getResponse();

}
