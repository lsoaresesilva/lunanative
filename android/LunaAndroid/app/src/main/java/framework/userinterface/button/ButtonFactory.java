package framework.userinterface.button;


import framework.error.LunaError;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 26/04/17.
 */

public class ButtonFactory {

    private MainActivity mMainActivity;
    private LunaError errorHandling;

    public ButtonFactory(MainActivity activity, LunaError errorHandling){
        this.mMainActivity = activity;
        this.errorHandling = errorHandling;
    }

    public ButtonBridge create(String language, Object properties){

        if( language != null ) {
            if (language.equals("lua")) {
                return LuaButtonBridge.create(properties, this.mMainActivity, this.errorHandling);
            }
        }

        errorHandling.dispatch(0);

        return null;
    }
}
