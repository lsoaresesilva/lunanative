package framework.userinterface.label;

import android.support.annotation.NonNull;

import framework.error.LunaError;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.ButtonProxy;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 17/05/17.
 */

public abstract class LabelBridge {

    protected LabelProxy proxy;
    protected LunaError errorHandling;

    public  LabelProxy getProxy(){
        return proxy;
    }

    @NonNull
    public static LabelBridge create(Object properties, MainActivity context, LunaError errorHandling){
        throw new UnsupportedOperationException("You must implement this method.");
    }
}
