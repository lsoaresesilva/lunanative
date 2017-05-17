package framework.userinterface.button;

import android.support.annotation.NonNull;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;
import luna.lunaandroid.MainActivity;


/**
 * Created by macbookair on 26/04/17.
 */

public class LuaButtonBridge extends ButtonBridge{

    private LunaError errorHandling;

    private LuaButtonBridge(){

    }

    public LuaButtonBridge(ButtonProxy proxy, LunaError errorHandling){
        this.errorHandling = errorHandling;
        this.buttonProxy = proxy;
    }

    public static ButtonBridge create(Object properties, MainActivity context, LunaError errorHandling){
        if(context != null && properties != null &&
            (properties instanceof LuaTable)){

            LuaTable buttonProperties = (LuaTable)properties;
            LunaHashMapAdapter luaPropertiesAdaptee = new LuaHashMapAdapter(buttonProperties);

            ButtonProxy buttonProxy = ButtonProxy.create(luaPropertiesAdaptee, context, errorHandling);
            if( buttonProxy != null ){
                return new LuaButtonBridge(buttonProxy, errorHandling);
            }
        }

        errorHandling.dispatch(1);

        return null;
    }

    @NonNull
    @Override
    public void setTouchCallback(Object callBack){
        if(callBack != null && callBack instanceof LuaFunction){
                LunaFunctionAdapter luaFunctionAdapter = new LuaFunctionAdapter((LuaFunction)callBack);

                this.getButtonProxy().setTouchCallback(luaFunctionAdapter);

        }else{
            this.errorHandling.dispatch(6);
        }
    }
}
