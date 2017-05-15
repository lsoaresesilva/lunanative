package framework.error;

import org.luaj.vm2.LuaFunction;

import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;

/**
 * Created by macbookair on 09/05/17.
 */

public class LunaError {

    LunaFunctionAdapter callback;
    int code;
    static LunaError instance;

    private LunaError(){

    }

    public static void dispatch(int code){
        LunaError instance = LunaError.getInstance();

        instance.code = code;

        if(instance.callback != null){
            instance.callback.execute();
        }
    }

    public int code(){
        return this.code;
    }

    public void define(LuaFunction callback){
        this.callback = new LuaFunctionAdapter(callback);
    }

    public static LunaError getInstance(){
        if (instance == null) {
            instance = new LunaError();
        }

        return instance;
    }
}
