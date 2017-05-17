package luna.lunaandroid.syntax.function.lua;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import java.util.ArrayList;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by macbookair on 06/05/17.
 */

public class LuaFunctionAdapterTest {


    @Test(expected = IllegalArgumentException.class)
    public void failWithInvalidAdaptee(){
        LuaFunctionAdapter adapter = new LuaFunctionAdapter(null);
    }

    @Test
    public void invokeCallback(){
        LuaFunction callback = new LuaFunction() {
        };
        LuaFunction callBackSpy = spy(callback);
        doReturn(null).when(callBackSpy).invoke();
        LuaFunctionAdapter adapter = new LuaFunctionAdapter(callBackSpy);
        adapter.execute();
        verify(callBackSpy).invoke();
    }











}
