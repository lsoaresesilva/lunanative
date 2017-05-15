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

    public LuaHashMapAdapter(Object adaptee){
        if(adaptee == null || !(adaptee instanceof LuaTable)){
            LunaError.dispatch(11);
        }else {
            this.adaptee = (LuaTable) adaptee;
        }
    }

    @Override
    public Integer size() {
        if(adaptee != null && (adaptee instanceof LuaTable)) {
            int count = 0;
            LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = adaptee.next(k);
                if ((k = n.arg1()).isnil())
                    break;
                count++;
            }
            return count;
        }else{
        	LunaError.dispatch(11);
        }

        return null;
    }

    @Override
    public Boolean containsKey(String key) {
        if(adaptee != null && (adaptee instanceof LuaTable)) {
            LuaValue value = this.adaptee.get(key);
            if (value != LuaValue.NIL)
                return true;
            else
                return false;
        }else{
        	LunaError.dispatch(11);
        }
        
        return null;
    }

    @Override
    public Object get(String key) {
        if(adaptee != null && (adaptee instanceof LuaTable)) {
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
                    LunaHashMapAdapter luaHash = new LuaHashMapAdapter(luaValue);

                    value = luaHash;
                }
            }
            return value;
        }else{
        	LunaError.dispatch(11);
        }
        
        return null;
    }

    @Override
    public String[] keys() {
        if(adaptee != null && (adaptee instanceof LuaTable)) {
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
        }else{
        	LunaError.dispatch(11);
        }
        
        return null;
    }
}
