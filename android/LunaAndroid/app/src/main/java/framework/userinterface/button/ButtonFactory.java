package framework.userinterface.button;


import framework.error.LunaError;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 26/04/17.
 */

public class ButtonFactory {

    private MainActivity mMainActivity;

    public ButtonFactory(MainActivity activity){
        this.mMainActivity = activity;
    }

    public ButtonBridge create(String language, Object properties){

        if( language != null ) {
            if (language.equals("lua")) {
                return LuaButtonBridge.newButtonBridge(properties, this.mMainActivity);
            }
        }

        LunaError.dispatch(1);

        return null;
    }
}
