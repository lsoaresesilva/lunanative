package luna.lunaandroid.userinterface.button.label;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaTable;

import framework.error.LunaError;
import framework.userinterface.label.LuaLabelBridge;
import framework.userinterface.label.LabelBridge;
import framework.userinterface.label.LabelFactory;
import framework.userinterface.label.LabelProxy;
import luna.lunaandroid.MainActivity;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by macbookair on 17/05/17.
 */

@RunWith(AndroidJUnit4.class)
public class LabelInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void makeLabel(){
        LuaTable properties = new LuaTable();
        properties.set("text", "Olá mundo!");
        properties.set("size", 20);

        LabelFactory factory = new LabelFactory(activityTestRule.getActivity(), LunaError.getInstance());
        LabelBridge luaLabelBridge = factory.create("lua", properties);
        Assert.assertThat(
                luaLabelBridge,
                instanceOf(LuaLabelBridge.class));

        LabelProxy proxy = luaLabelBridge.getProxy();
        Assert.assertThat(proxy
                ,
                instanceOf(LabelProxy.class));

        TextView text = (TextView)proxy.getAndroidView();
        Assert.assertNotNull(text);

        Assert.assertEquals("Olá mundo!", text.getText());
        Assert.assertEquals(40, text.getTextSize(), 0.1);
    }


}
