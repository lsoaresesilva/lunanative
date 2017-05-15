package luna.lunaandroid.userinterface.button;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaTable;

import framework.rest.RestBridge;
import framework.rest.RestFactory;
import framework.syntax.data.LuaHashMapAdapter;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.ButtonFactory;
import framework.userinterface.button.LuaButtonBridge;
import luna.lunaandroid.MainActivity;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 11/05/17.
 */

public class ButtonTests {

    @Test
    public void failWithInvalidLanguage(){
        ButtonFactory factory = new ButtonFactory(mock(MainActivity.class));
        Assert.assertNull(factory.create(null, null));
    }

    @Test
    public void failWithInvalidProperties(){
        //ButtonFactory factory = new ButtonFactory(mock(MainActivity.class));
        ButtonBridge buttonBridge = LuaButtonBridge.newButtonBridge(null, null);
        Assert.assertNull(buttonBridge);

        ButtonBridge secondButtonBridge = LuaButtonBridge.newButtonBridge(null, mock(MainActivity.class));
        Assert.assertNull(secondButtonBridge);
    }

}
