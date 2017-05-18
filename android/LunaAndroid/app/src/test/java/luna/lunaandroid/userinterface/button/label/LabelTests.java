package luna.lunaandroid.userinterface.button.label;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.LuaButtonBridge;
import framework.userinterface.label.LabelBridge;
import framework.userinterface.label.LabelFactory;
import framework.userinterface.label.LabelProxy;
import framework.userinterface.label.LuaLabelBridge;
import luna.lunaandroid.MainActivity;

import static org.mockito.Mockito.mock;

/**
 * Created by macbookair on 17/05/17.
 */

public class LabelTests {

    private LunaError errorSpy;

    @Before
    public void before(){
        errorSpy = Mockito.spy(LunaError.getInstance());
    }

    @Test
    public void failWithInvalidLanguage(){
        Mockito.doNothing().when(errorSpy).dispatch(0);
        Assert.assertNull(
                new LabelFactory(null, errorSpy)
                        .create("", null));
        Mockito.verify(errorSpy).dispatch(0);
    }

    @Test
    public void failWithInvalidProperties(){

        Mockito.doNothing().when(errorSpy).dispatch(1);
        LabelBridge labelBridge = LuaLabelBridge.create(null, null, errorSpy);
        Assert.assertNull(labelBridge);
        Mockito.verify(errorSpy).dispatch(1);

        errorSpy = Mockito.spy(LunaError.getInstance());

        Mockito.doNothing().when(errorSpy).dispatch(1);
        LabelProxy proxy = LabelProxy.create(null,
                                            mock(MainActivity.class),
                                            errorSpy);
        Assert.assertNull(proxy);
        Mockito.verify(errorSpy).dispatch(1);

        errorSpy = Mockito.spy(LunaError.getInstance());

        // it will fail because there is no text property.

        Mockito.doNothing().when(errorSpy).dispatch(7);

        LuaTable luaProperties = new LuaTable();
        luaProperties.set("anything", "anything");
        LunaHashMapAdapter properties = new LuaHashMapAdapter(luaProperties);

        LabelProxy otherProxy = LabelProxy.create(properties,
                mock(MainActivity.class),
                errorSpy);
        Assert.assertNull(otherProxy);
        Mockito.verify(errorSpy).dispatch(7);
    }
}
