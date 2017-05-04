package luna.lunaframework.ui.button;

import org.junit.Test;
import org.mockito.Mock;

import luna.framework.ui.button.ButtonBridge;
import luna.framework.ui.button.ButtonFactory;
import luna.framework.ui.button.LuaButtonBridge;
import luna.lunaframework.MainActivity;

/**
 * Created by macbookair on 04/05/17.
 */

public class ButtonTests {


    @Mock
    MainActivity mActivity;

    @Test(expected=IllegalArgumentException.class)
    public void testLuaButtonCreationWithNullParameters(){

         //ButtonBridge luaButton = ButtonFactory.newButton("lua", null, mActivity);
    }
}
