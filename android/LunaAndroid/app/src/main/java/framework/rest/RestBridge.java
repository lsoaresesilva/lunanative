package framework.rest;

/**
 * Created by macbookair on 05/05/17.
 */

public interface RestBridge {


    /*public static RestBridge newRestBridge(){
        throw new UnsupportedOperationException("You must implement this method.");
    }*/
    void makeRequest(Object properties);
    RestResponseWrapper response();

}
