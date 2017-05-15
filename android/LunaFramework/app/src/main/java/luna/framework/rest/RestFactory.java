package luna.framework.rest;

/**
 * Created by macbookair on 05/05/17.
 */

public class RestFactory {

    public static RestBridge newRest(String language){

        if(language.equals("lua")){
            return LuaRestBridge.newRestBridge();
        }
        return null;
    }
}
