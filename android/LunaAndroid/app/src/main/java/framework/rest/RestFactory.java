package framework.rest;

import framework.error.LunaError;

/**
 * Created by macbookair on 05/05/17.
 */

public class RestFactory {

    public static RestBridge create(String language){

        if( language != null ) {
            if (language.equals("lua")) {
                return new LuaRestBridge(LunaError.getInstance());
            }
        }

        LunaError.getInstance().dispatch(1);

        return null;
    }
}
