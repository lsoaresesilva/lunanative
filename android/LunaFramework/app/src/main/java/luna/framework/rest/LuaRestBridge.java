package luna.framework.rest;

/**
 * Created by macbookair on 05/05/17.
 */

public class LuaRestBridge extends RestBridge {

    public static RestBridge newRestBridge(){
        RestBridge luaBridge = new LuaRestBridge();
        return luaBridge;
    }

    @Override
    public void makeRequest(Object properties) {

    }

    @Override
    public RestResponseWrapper getResponse() {
        return null;
    }
}
