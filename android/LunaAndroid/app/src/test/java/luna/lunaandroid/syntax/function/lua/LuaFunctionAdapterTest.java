package luna.lunaandroid.syntax.function.lua;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import java.util.ArrayList;

import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.function.LuaFunctionAdapter;
import framework.syntax.function.LunaFunctionAdapter;

import static org.mockito.Mockito.spy;


/**
 * Created by macbookair on 06/05/17.
 */

public class LuaFunctionAdapterTest {

    @Test
    public void testIsFunction(){

        LuaFunction function = new LuaFunction() {
            @Override
            public int type() {
                return super.type();
            }
        };

        LunaFunctionAdapter functionAdapter = new LuaFunctionAdapter(function);

        Assert.assertTrue(functionAdapter.isFunction());
    }











}
