package framework.syntax.data;

import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import framework.error.LunaError;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;

import static org.luaj.vm2.LuaValue.TBOOLEAN;
import static org.luaj.vm2.LuaValue.TFUNCTION;
import static org.luaj.vm2.LuaValue.TINT;
import static org.luaj.vm2.LuaValue.TNIL;
import static org.luaj.vm2.LuaValue.TNUMBER;
import static org.luaj.vm2.LuaValue.TSTRING;
import static org.luaj.vm2.LuaValue.TTABLE;

/**
 * Created by macbookair on 27/04/17.
 */

public class LuaHashMapAdapter implements LunaHashMapAdapter {

    private LuaTable adaptee;

    private LuaHashMapAdapter(){

    }

    public LuaHashMapAdapter(LuaTable adaptee){
        if(adaptee == null){
            throw new IllegalArgumentException("Missing adaptee for LuaHashMapAdapter.");
        }

        this.adaptee = adaptee;

    }

    @Override
    public Integer size() {
        int count = 0;
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = adaptee.next(k);
            if ((k = n.arg1()).isnil())
                break;
            count++;
        }
        return count;
    }

    @Override
    public Boolean containsKey(String key) {
        LuaValue value = this.adaptee.get(key);
        if (value != LuaValue.NIL)
            return true;
        else
            return false;
    }

    @Override
    public Object get(String key) {
        Object value = null;
        if (this.adaptee.get(key) != null || !(this.adaptee.get(key) instanceof LuaNil)) {
            LuaValue luaValue = this.adaptee.get(key);

            if (luaValue.type() == TSTRING) {
                value = luaValue.toString();
            } else if (luaValue.type() == TINT) {
                value = luaValue.toint();
            } else if (luaValue.type() == TNIL) {
                value = null;
            } else if (luaValue.type() == TBOOLEAN) {
                value = luaValue.toboolean();
            } else if (luaValue.type() == TFUNCTION) {
                LunaFunctionAdapter luaFunction = new LuaFunctionAdapter((LuaFunction) luaValue);
                //luaFunction.create((LuaFunction)luaValue);
                value = luaFunction;
            } else if (luaValue.type() == TNUMBER) {
                value = luaValue.todouble();
            } else if (luaValue.type() == TTABLE) {
                LunaHashMapAdapter luaHash = new LuaHashMapAdapter((LuaTable)luaValue);

                value = luaHash;
            }
        }
        return value;
    }

    @Override
    public String[] keys() {
        String[] keys = new String[this.size()];
        LuaValue k = LuaValue.NIL;
        int count = 0;
        while ( true ) {
            Varargs n = adaptee.next(k);
            if ( (k = n.arg1()).isnil() )
                break;
            keys[count] = k.toString();

        }

        return keys;
    }
}
