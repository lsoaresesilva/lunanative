package luna.lunaandroid.rest.lua;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import framework.rest.RestProxy;
import framework.rest.RestResponseWrapper;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import framework.test.AsyncTaskTest;
import framework.test.AsyncTest;

import static org.mockito.Mockito.*;

/**
 * Created by macbookair on 05/05/17.
 */
@RunWith(AndroidJUnit4.class)
public class ResFactorytInstrumentedTests {


    //static CountDownLatch signal = null;

    @BeforeClass
    public static void setUp() throws Exception {
        //signal = new CountDownLatch(1);
    }


    /**
     * REST GET request expecting success.
     * @throws InterruptedException
     */
    @Test
    public void makeGetRequestWithSuccess() throws InterruptedException {

        LuaTable luaProperties = new LuaTable();

        LuaFunction f = mock(LuaFunction.class);
        when(f.invoke()).thenReturn(null);
        when(f.type()).thenReturn(LuaValue.TFUNCTION);

        luaProperties.set("callback", f);
        luaProperties.set("method", "GET");
        luaProperties.set("url", "http://jsonplaceholder.typicode.com/posts/1");

        final LunaHashMapAdapter lunaProperties = new LuaHashMapAdapter(luaProperties);
        final RestProxy r = new RestProxy();
        r.execute(lunaProperties);

        AsyncTaskTest.build(r).
                    run(new AsyncTest() {
                        @Override
                        public void test(Object result) {

                            Assert.assertEquals(200, ((RestResponseWrapper)result).getResponseCode());
                        }
                    });
    }

    @Test
    public void makePostRequestWithSuccess() throws InterruptedException {

        LuaTable luaProperties = new LuaTable();

        LuaFunction f = mock(LuaFunction.class);
        when(f.invoke()).thenReturn(null);
        when(f.type()).thenReturn(LuaValue.TFUNCTION);

        luaProperties.set("callback", f);
        luaProperties.set("method", "POST");
        luaProperties.set("url", "http://jsonplaceholder.typicode.com/posts");

        LuaTable params = new LuaTable();
        params.set("nome", "Leonardo");

        luaProperties.set("params", params);

        final LunaHashMapAdapter lunaProperties = new LuaHashMapAdapter(luaProperties);
        final RestProxy r = new RestProxy();
        r.execute(lunaProperties);

        AsyncTaskTest.build(r).
                run(new AsyncTest() {
                    @Override
                    public void test(Object result) {
                        Assert.assertEquals(201, r.getResponse().getResponseCode());
                    }
                });
    }

    @Test
    public void makePUTRequestWithSuccess() throws InterruptedException {

        LuaTable luaProperties = new LuaTable();

        LuaFunction f = mock(LuaFunction.class);
        when(f.invoke()).thenReturn(null);
        when(f.type()).thenReturn(LuaValue.TFUNCTION);

        luaProperties.set("callback", f);
        luaProperties.set("method", "PUT");
        luaProperties.set("url", "http://jsonplaceholder.typicode.com/posts/1");

        LuaTable params = new LuaTable();
        params.set("nome", "Leonardo");

        luaProperties.set("params", params);

        final LunaHashMapAdapter lunaProperties = new LuaHashMapAdapter(luaProperties);
        final RestProxy r = new RestProxy();
        r.execute(lunaProperties);

        AsyncTaskTest.build(r).
                run(new AsyncTest() {
                    @Override
                    public void test(Object result) {
                        Assert.assertEquals(200, r.getResponse().getResponseCode());
                    }
                });
    }

    @Test
    public void makeDELETERequestWithSuccess() throws InterruptedException {

        LuaTable luaProperties = new LuaTable();

        LuaFunction f = mock(LuaFunction.class);
        when(f.invoke()).thenReturn(null);
        when(f.type()).thenReturn(LuaValue.TFUNCTION);

        luaProperties.set("callback", f);
        luaProperties.set("method", "DELETE");
        luaProperties.set("url", "http://jsonplaceholder.typicode.com/posts/1");

        final LunaHashMapAdapter lunaProperties = new LuaHashMapAdapter(luaProperties);
        final RestProxy r = new RestProxy();
        r.execute(lunaProperties);

        AsyncTaskTest.build(r).
                run(new AsyncTest() {
                    @Override
                    public void test(Object result) {
                        Assert.assertEquals(200, r.getResponse().getResponseCode());
                    }
                });
    }

}
