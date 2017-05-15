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


    public static ButtonBridge newButtonBridge(Object properties, MainActivity context){
        if(context != null && properties != null &&
            (properties instanceof LuaTable)){

            LuaTable buttonProperties = (LuaTable)properties;
            LunaHashMapAdapter luaPropertiesAdaptee = new LuaHashMapAdapter(buttonProperties);

            if (luaPropertiesAdaptee.size() > 0) {
                ButtonBridge newButtonBridge = new LuaButtonBridge();
                newButtonBridge.buttonProxy = ButtonProxy.newButtonProxy(luaPropertiesAdaptee, context);

                return newButtonBridge;
            }
        }

        LunaError.dispatch(1);

        return null;
    }

    @NonNull
    @Override
    public void setTouchCallback(Object callBack){
        if(callBack != null && callBack instanceof LuaFunction){
                LunaFunctionAdapter luaFunctionAdapter = new LuaFunctionAdapter(callBack);

                this.getButtonProxy().setTouchCallback(luaFunctionAdapter);

        }else{
            LunaError.dispatch(6);
        }
    }
}
