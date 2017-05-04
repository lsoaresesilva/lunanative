package luna.framework.syntax.function;

import org.luaj.vm2.LuaFunction;

/**
 * Created by macbookair on 27/04/17.
 */

public class LuaFunctionAdapter implements LunaFunctionAdapter {

    LuaFunction luaCallback;

    @Override
    public boolean isFunction() {
        if(luaCallback.checkfunction() != null){
            return true;
        }

        return false;
    }

    @Override
    public void create(Object function) {
        if( function != null &&
            function instanceof LuaFunction){
            this.luaCallback = (LuaFunction)function;
        }
    }

    @Override
    public void execute(){
        if( isFunction() ){
            luaCallback.invoke();
        }
    }
}
