package luna.lunaandroid.rest;

import net.bytebuddy.asm.Advice;

import org.junit.Assert;
import org.junit.Test;

import framework.rest.LuaRestBridge;
import framework.rest.RestBridge;
import framework.rest.RestFactory;
import framework.rest.RestProxy;
import framework.syntax.function.LunaFunctionAdapter;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by macbookair on 05/05/17.
 */

public class RestTests {

    @Test
    public void createLuaBridgeInstance(){

        Assert.assertThat(
                RestFactory.create("lua"),
                instanceOf(RestBridge.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void failWithNoProperties(){
        LuaRestBridge lua = new LuaRestBridge();
        lua.makeRequest(null);
    }




    /*@Test
    public void  testInvalidParameter() {
        Assert.assertNull(RestFactory.newRest(null));
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        //let properties:NSMutableDictionary = NSMutableDictionary()


        //properties.setValue("", forKey: "method")
        //let luaProperties:LunaHashMapAdapter = LuaHashMapAdapter()
        //luaProperties.create(properties)
        //let proxy:RestProxy = RestProxy.newRestProxy(nil)


        //XCTAssertNil(RestProxy.newRestProxy(luaProperties))
        //
    }*/

}
