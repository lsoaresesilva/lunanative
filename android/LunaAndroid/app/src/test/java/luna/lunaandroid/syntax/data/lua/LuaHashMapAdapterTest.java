package luna.lunaandroid.syntax.data.lua;

import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Test;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import java.util.ArrayList;

import framework.error.LunaError;
import framework.syntax.data.LuaHashMapAdapter;
import framework.syntax.data.LunaHashMapAdapter;
import framework.syntax.function.LunaFunctionAdapter;
import framework.userinterface.button.ButtonFactory;
import luna.lunaandroid.MainActivity;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.mock;

/**
 * Created by macbookair on 05/05/17.
 */

public class LuaHashMapAdapterTest {


    @Test(expected = IllegalArgumentException.class)
    public void failWithInvalidAdaptee(){
        LuaHashMapAdapter adapter = new LuaHashMapAdapter(null);
    }

    @Test
    public void returnSize(){
        LuaTable adaptee = new LuaTable();
        LuaHashMapAdapter adapter = new LuaHashMapAdapter(adaptee);
        Assert.assertEquals(new Integer(0), adapter.size());
        adaptee.set("hi", "hello");
        Assert.assertEquals(new Integer(1), adapter.size());
    }

    @Test
    public void checkKey(){
        LuaTable adaptee = new LuaTable();
        LuaHashMapAdapter adapter = new LuaHashMapAdapter(adaptee);
        Assert.assertFalse(adapter.containsKey("hi"));
        adaptee.set("hi", "hello");
        Assert.assertTrue(adapter.containsKey("hi"));
    }

    @Test
    public void returnKeys(){
        LuaTable adaptee = new LuaTable();
        adaptee.set("nome", "leonardo");
        LuaHashMapAdapter adapter = new LuaHashMapAdapter(adaptee);
        Assert.assertEquals(1, adapter.keys().length);
        Assert.assertEquals("nome", adapter.keys()[0]);

    }

    @Test
    public void returnValue(){
        LuaTable adaptee = new LuaTable();
        LuaHashMapAdapter adapter = new LuaHashMapAdapter(adaptee);
        Assert.assertNull(adapter.get("hi"));
        adaptee.set("hi", "hello");
        adaptee.set("callback", new LuaFunction() {
        });

        LuaTable aTable = new LuaTable();
        aTable.set("aKey", "aValue");
        adaptee.set("aTable", aTable);
        Assert.assertEquals("hello", adapter.get("hi"));
        Assert.assertThat(adapter.get("callback"), instanceOf(LunaFunctionAdapter.class));
        Assert.assertThat(adapter.get("aTable"), instanceOf(LunaHashMapAdapter.class));
    }


}
