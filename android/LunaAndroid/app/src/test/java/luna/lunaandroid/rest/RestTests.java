package luna.lunaandroid.rest;

import net.bytebuddy.asm.Advice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.mockito.Mockito;

import framework.error.LunaError;
import framework.rest.LuaRestBridge;
import framework.rest.RestBridge;
import framework.rest.RestFactory;
import framework.rest.RestProxy;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.function.LunaFunctionAdapter;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by macbookair on 05/05/17.
 */

public class RestTests {

    private LunaError errorSpy;

    @Before
    public void before(){
        errorSpy = Mockito.spy(LunaError.getInstance());
    }



    @Test
    public void makeRest(){

        Assert.assertThat(
                RestFactory.create("lua"),
                instanceOf(RestBridge.class));
    }

    @Test
    public void testFailWithInvalidLanguage(){
        Assert.assertNull(RestFactory.create(""));
    }

    @Test
    public void failWithInvalidProperties(){

        Mockito.doNothing().when(errorSpy).dispatch(2);
        RestBridge lunaRest = new LuaRestBridge(errorSpy);
        lunaRest.makeRequest(null);
        Mockito.verify(errorSpy).dispatch(2);

        // Restarting errorSpy

        errorSpy = Mockito.spy(LunaError.getInstance());

        Mockito.doNothing().when(errorSpy).dispatch(2);
        RestProxy restProxy = new RestProxy(errorSpy);
        restProxy.doInBackground(new LuaHashMapAdapter(new LuaTable()));

        Mockito.verify(errorSpy).dispatch(2);
    }

    @Test
    public void failWithInvalidMethod(){

        Mockito.doNothing().when(errorSpy).dispatch(3);
        RestProxy restProxy = new RestProxy(errorSpy);
        LuaTable restProperties = new LuaTable();
        restProperties.set("url", "http://");
        restProperties.set("method", "");
        restProperties.set("callback", new LuaFunction() {
        });
        restProxy.doInBackground(
                    new LuaHashMapAdapter(
                            restProperties));

        Mockito.verify(errorSpy).dispatch(3);
    }

    @Test
    public void failWithInvalidCallback(){

        Mockito.doNothing().when(errorSpy).dispatch(2);
        RestProxy restProxy = new RestProxy(errorSpy);
        LuaTable restProperties = new LuaTable();
        restProperties.set("url", "http://");
        restProperties.set("method", "");
        restProperties.set("callback", new LuaValue() {
            @Override
            public int type() {
                return 0;
            }

            @Override
            public String typename() {
                return null;
            }
        });
        restProxy.doInBackground(
                new LuaHashMapAdapter(
                        restProperties));

        Mockito.verify(errorSpy).dispatch(2);
    }

    @Test
    public void failWithInvalidRequest(){

        Mockito.doNothing().when(errorSpy).dispatch(9);
        RestProxy restProxy = new RestProxy(errorSpy);
        LuaTable restProperties = new LuaTable();
        restProperties.set("url", "http://www.maoeeoam.com");
        restProperties.set("method", "GET");
        restProperties.set("callback", new LuaFunction() {
        });
        restProxy.doInBackground(
                new LuaHashMapAdapter(
                        restProperties));

        Mockito.verify(errorSpy).dispatch(9);
    }

}
