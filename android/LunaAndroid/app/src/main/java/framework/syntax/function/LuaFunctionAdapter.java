package framework.syntax.function;

import org.luaj.vm2.LuaFunction;

import framework.error.LunaError;

/**
 * Created by macbookair on 27/04/17.
 */

public class LuaFunctionAdapter implements LunaFunctionAdapter {

    LuaFunction callback;

    private LuaFunctionAdapter(){

    }

    public LuaFunctionAdapter( Object function ){
        if( function == null &&
                !(function instanceof LuaFunction)){
            LunaError.dispatch(12);
        }
        this.callback = (LuaFunction)function;

    }

    @Override
    public Boolean isFunction() {
        if(callback != null && callback instanceof LuaFunction) {
            if (callback.checkfunction() != null) {
                return true;
            }

            return false;
        }else{
        	LunaError.dispatch(12);
        }
        
        return null;
    }

    @Override
    public void execute(){
        if(callback != null && callback instanceof LuaFunction) {
            if (isFunction()) {
                callback.invoke();
            }
        }
    }
}
