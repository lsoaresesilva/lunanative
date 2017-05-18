package framework.userinterface.label;

import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 17/05/17.
 */

public class LabelFactory {

    private MainActivity activity;
    private LunaError errorHandling;

    public LabelFactory(MainActivity activity, LunaError errorHandling) {
        this.activity = activity;
        this.errorHandling = errorHandling;
    }
    private LabelFactory(){}

    public LabelBridge create(String language, LuaTable properties) {

        if(language.equals("lua")){
            return LuaLabelBridge.create(properties, activity, errorHandling);
        }

        errorHandling.dispatch(0);

        return null;
    }
}
