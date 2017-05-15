package framework.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import framework.syntax.data.LunaHashMapAdapter;

import static org.luaj.vm2.LuaValue.TBOOLEAN;
import static org.luaj.vm2.LuaValue.TINT;
import static org.luaj.vm2.LuaValue.TNIL;
import static org.luaj.vm2.LuaValue.TNUMBER;
import static org.luaj.vm2.LuaValue.TSTRING;
import static org.luaj.vm2.LuaValue.TTABLE;

/**
 * Created by macbookair on 07/05/17.
 */

public class LunaHashMapAdapterJsonConverter {

    public static JSONObject toJson(LunaHashMapAdapter params){
        if(params == null){
            throw new IllegalArgumentException("Cannot generate JSON without values.");
        }
        JSONObject json = new JSONObject();
        String[] keys = params.keys();
        for(int i =0; i < keys.length; i++){
            try {
                json.put(keys[i], params.get(keys[i]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

}
