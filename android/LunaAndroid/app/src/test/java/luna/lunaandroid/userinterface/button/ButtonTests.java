package luna.lunaandroid.userinterface.button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import framework.error.LunaError;
import framework.rest.LuaRestBridge;
import framework.rest.RestBridge;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.ButtonFactory;
import framework.userinterface.button.ButtonProxy;
import framework.userinterface.button.LuaButtonBridge;
import luna.lunaandroid.MainActivity;

import static org.mockito.Mockito.mock;

/**
 * Created by macbookair on 11/05/17.
 */

public class ButtonTests {

    private LunaError errorSpy;

    @Before
    public void before(){
        errorSpy = Mockito.spy(LunaError.getInstance());
    }

    @Test
    public void failWithInvalidLanguage(){
        Mockito.doNothing().when(errorSpy).dispatch(0);
        ButtonFactory factory = new ButtonFactory(mock(MainActivity.class), errorSpy);
        Assert.assertNull(factory.create(null, null));
        Mockito.verify(errorSpy).dispatch(0);
    }



    @Test
    public void failWithInvalidProperties(){


        ButtonBridge buttonBridge = LuaButtonBridge.create(null, null, LunaError.getInstance());
        Assert.assertNull(buttonBridge);

        Mockito.doNothing().when(errorSpy).dispatch(1);
        ButtonBridge secondButtonBridge = LuaButtonBridge.create(new LuaTable(), mock(MainActivity.class), errorSpy);
        Assert.assertNull(secondButtonBridge);
        Mockito.verify(errorSpy).dispatch(1);
    }




}
