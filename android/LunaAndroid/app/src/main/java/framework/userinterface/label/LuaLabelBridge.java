package framework.userinterface.label;

import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 17/05/17.
 */

public class LuaLabelBridge extends LabelBridge {

    public LuaLabelBridge(LabelProxy proxy, LunaError errorHandling) {
        this.proxy = proxy;
        this.errorHandling = errorHandling;

    }

    public static LabelBridge create(Object properties, MainActivity activity, LunaError errorHandling){
        if(properties == null || !(properties instanceof LuaTable) ){
            errorHandling.dispatch(1);
        }else {
            LunaHashMapAdapter adapter = new LuaHashMapAdapter((LuaTable) properties);
            LabelProxy proxy = LabelProxy.create(adapter, activity, errorHandling);
            if (proxy != null) {
                return new LuaLabelBridge(proxy, errorHandling);
            }
        }

        return null;
    }

}
