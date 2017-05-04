package luna.framework.ui.button;


import luna.lunaframework.MainActivity;

/**
 * Created by macbookair on 26/04/17.
 */

public class ButtonFactory {

    private MainActivity mMainActivity;

    public ButtonFactory(MainActivity activity){
        this.mMainActivity = activity;
    }

    public ButtonBridge newButton(String language, Object properties){
        if(language.equals("lua")){
            return LuaButtonBridge.newButtonBridge(properties, this.mMainActivity);
        }

        return null;
    }
}
