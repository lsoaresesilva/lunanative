package framework.syntax.function;

import org.luaj.vm2.LuaFunction;

import framework.error.LunaError;

/**
 * Created by macbookair on 27/04/17.
 */

public class LuaFunctionAdapter implements LunaFunctionAdapter {

    private LuaFunction callback;

    private LuaFunctionAdapter(){

    }

    public LuaFunctionAdapter( LuaFunction function ){
        if(function == null){
            throw new IllegalArgumentException("Missing function for LuaFunctionAdapter.");
        }

        this.callback = function;

    }


    @Override
    public void execute(){
        callback.invoke();
    }
}
