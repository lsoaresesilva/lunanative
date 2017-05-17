package framework.rest;

import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;

/**
 * Created by macbookair on 05/05/17.
 */

public class LuaRestBridge implements RestBridge {

    private RestProxy restProxy;
    private LunaError errorHandling;

    private LuaRestBridge(){}

    public LuaRestBridge( LunaError errorHandling){
        this.errorHandling = errorHandling;
    }

    @Override
    public void makeRequest(Object properties) {
        if( properties != null && (properties instanceof LuaTable)){

            LunaHashMapAdapter luaProperties = new LuaHashMapAdapter((LuaTable) properties);

            this.restProxy = new RestProxy(this.errorHandling);
            restProxy.execute(luaProperties);
        }

        this.errorHandling.dispatch(2);
    }


    @Override
    public RestResponseWrapper response() {
        return this.restProxy.getResponse();
    }

}
