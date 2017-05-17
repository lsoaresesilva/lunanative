package luna.lunaandroid.error;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.LuaFunction;
import org.mockito.Mockito;

import framework.error.LunaError;
import framework.rest.RestBridge;
import framework.rest.RestFactory;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by macbookair on 10/05/17.
 */

public class LunaErrorTest {

    @Test
    public void createInstance(){
        Assert.assertThat(
                LunaError.getInstance(),
                instanceOf(LunaError.class));
    }

    @Test
    public void dispatchError(){
        LuaFunction function = new LuaFunction() {
        };
        LuaFunction functionSpy = Mockito.spy(function);
        Mockito.doReturn(null).when(functionSpy).invoke();

        //LuaFunctionAdapter lunaFunctionAdapter = new LuaFunctionAdapter(functionMock);
        LunaError.getInstance().define(functionSpy);
        LunaError.getInstance().dispatch(1);
        Mockito.verify(functionSpy).invoke();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithInvalidCallback(){
        LunaError.getInstance().define(null);
    }
}
