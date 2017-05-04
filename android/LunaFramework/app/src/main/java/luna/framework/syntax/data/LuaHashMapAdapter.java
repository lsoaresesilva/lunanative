package luna.framework.syntax.data;

import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import static org.luaj.vm2.LuaValue.TBOOLEAN;
import static org.luaj.vm2.LuaValue.TINT;
import static org.luaj.vm2.LuaValue.TNIL;
import static org.luaj.vm2.LuaValue.TNUMBER;
import static org.luaj.vm2.LuaValue.TSTRING;
import static org.luaj.vm2.LuaValue.TTABLE;

/**
 * Created by macbookair on 27/04/17.
 */

public class LuaHashMapAdapter implements LunaHashMapAdapter {

    LuaTable luaTable;

    @Override
    public void create(Object luaTable) {
        if( luaTable != null && luaTable instanceof LuaTable)
            this.luaTable = (LuaTable)luaTable;
    }

    @Override
    public int size() {
        if( this.luaTable != null ) {
            int count = 0;
            LuaValue k = LuaValue.NIL;
            while ( true ) {
                Varargs n = luaTable.next(k);
                if ( (k = n.arg1()).isnil() )
                    break;
                count++;
            }
            return count;
        }
        throw new NullPointerException("An LuaTable was not passed to create() method.");
    }

    @Override
    public boolean containsKey(String key) {
        if( this.luaTable != null ) {
            LuaValue value = this.luaTable.get(key);
            if ( value != LuaValue.NIL )
                return true;
            else
                return false;
        }

        throw new NullPointerException("An LuaTable was not passed to create() method.");
    }

    @Override
    public Object get(String key) {
        if(this.luaTable != null) {
            Object value = null;
            if (this.luaTable.get(key) != null || !(this.luaTable.get(key) instanceof LuaNil)) {
                LuaValue luaValue = this.luaTable.get(key);

                if (luaValue.type() == TSTRING) {
                    value = luaValue.toString();
                } else if (luaValue.type() == TINT) {
                    value = luaValue.toint();
                } else if (luaValue.type() == TNIL) {
                    value = null;
                } else if (luaValue.type() == TBOOLEAN) {
                    value = luaValue.toboolean();
                } else if (luaValue.type() == TNUMBER) {
                    value = luaValue.todouble();
                } else if (luaValue.type() == TTABLE) {
                    LunaHashMapAdapter luaHash = new LuaHashMapAdapter();
                    luaHash.create(luaValue);
                    value = luaHash;
                }
            }
            return value;
        }
        throw new NullPointerException("An LuaTable was not passed to create() method.");
    }
}
