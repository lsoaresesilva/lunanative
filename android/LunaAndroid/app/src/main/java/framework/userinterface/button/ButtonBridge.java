package framework.userinterface.button;

import android.support.annotation.NonNull;

import framework.error.LunaError;
import luna.lunaandroid.MainActivity;


/**
 * Created by macbookair on 26/04/17.
 */

public abstract class ButtonBridge {

    protected ButtonProxy proxy;

    public ButtonProxy getProxy() {
        return proxy;
    }

    public void setProxy(ButtonProxy proxy) {
        this.proxy = proxy;
    }

    @NonNull
    public static ButtonBridge create(Object properties, MainActivity context, LunaError errorHandling){
        throw new UnsupportedOperationException("You must implement this method.");
    }

    @NonNull
    public abstract void setTouchCallback(final Object callBack);




}
