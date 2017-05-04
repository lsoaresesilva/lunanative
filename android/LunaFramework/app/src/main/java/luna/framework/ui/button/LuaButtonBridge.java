package luna.framework.ui.button;

import android.support.annotation.NonNull;
import android.widget.Button;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;

import luna.framework.syntax.data.LuaHashMapAdapter;
import luna.framework.syntax.data.LunaHashMapAdapter;
import luna.framework.syntax.function.LuaFunctionAdapter;
import luna.framework.syntax.function.LunaFunctionAdapter;
import luna.lunaframework.MainActivity;


/**
 * Created by macbookair on 26/04/17.
 */

public class LuaButtonBridge extends ButtonBridge{


    public static ButtonBridge newButtonBridge(Object properties, MainActivity context){
        if(context != null && properties != null &&
            (properties instanceof LuaTable) && ((LuaTable) properties).length() > 0){

            LuaTable buttonProperties = (LuaTable)properties;
            LunaHashMapAdapter luaPropertiesAdaptee = new LuaHashMapAdapter();
            luaPropertiesAdaptee.create(buttonProperties);

            if (luaPropertiesAdaptee.size() > 0) {
                ButtonBridge newButtonBridge = new LuaButtonBridge();
                newButtonBridge.buttonProxy = ButtonProxy.newButtonProxy(luaPropertiesAdaptee, context);

                return newButtonBridge;
            }
        }else{
            throw new IllegalArgumentException("Missing properties for button creation.");
        }

        return null;
    }

    @NonNull
    @Override
    public void setTouchCallback(Object callBack){
        if(callBack != null && callBack instanceof LuaFunction){
                LunaFunctionAdapter luaFunctionAdapter = new LuaFunctionAdapter();
                luaFunctionAdapter.create(callBack);
                this.getButtonProxy().setTouchCallback(luaFunctionAdapter);

        }else{
            throw new IllegalArgumentException("A callback is required.");
        }
    }
}
