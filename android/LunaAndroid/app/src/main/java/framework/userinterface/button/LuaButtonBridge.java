package framework.userinterface.button;

import android.support.annotation.NonNull;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;
import luna.lunaandroid.MainActivity;


/**
 * Created by macbookair on 26/04/17.
 */

public class LuaButtonBridge extends ButtonBridge{

    private LunaError errorHandling;

    private LuaButtonBridge(){}

    public LuaButtonBridge(ButtonProxy proxy, LunaError errorHandling){
        this.errorHandling = errorHandling;
        this.proxy = proxy;
    }

    public static ButtonBridge create(Object properties, MainActivity context, LunaError errorHandling){

        if(properties == null || !(properties instanceof LuaTable)){
            errorHandling.dispatch(1);
        }else {
            ButtonProxy buttonProxy = ButtonProxy.create(new LuaHashMapAdapter((LuaTable) properties),
                    context,
                    errorHandling);
            if (buttonProxy != null) {
                return new LuaButtonBridge(buttonProxy, errorHandling);
            }
        }


        return null;
    }

    @NonNull
    @Override
    public void setTouchCallback(Object callBack){
        if(callBack != null && callBack instanceof LuaFunction){
                LunaFunctionAdapter luaFunctionAdapter = new LuaFunctionAdapter((LuaFunction)callBack);

                this.getProxy().setTouchCallback(luaFunctionAdapter);

        }else{
            this.errorHandling.dispatch(6);
        }
    }
}
