package luna.lunaandroid.userinterface.button.button;

import android.graphics.drawable.BitmapDrawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.ImageButton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaTable;
import org.mockito.Mockito;

import framework.error.LunaError;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.ButtonFactory;
import luna.lunaandroid.MainActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by macbookair on 11/05/17.
 */

@RunWith(AndroidJUnit4.class)
public class ButtonInstrumentedTest {

    private LunaError errorSpy;

    @Before
    public void before(){
        errorSpy = Mockito.spy(LunaError.getInstance());
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void failWithInvalidImage(){
        Mockito.doNothing().when(errorSpy).dispatch(8);
        ButtonFactory buttonFactory = new ButtonFactory(activityTestRule.getActivity(), errorSpy);
        LuaTable buttonProperties = new LuaTable();

        LuaTable imgProperties = new LuaTable();
        imgProperties.set("normal", "btn.png");
        buttonProperties.set("img", imgProperties);
        ButtonBridge result = buttonFactory.create("lua", buttonProperties);
        Assert.assertNull(result);
        Mockito.verify(errorSpy).dispatch(8);

        LuaTable buttonProperties2 = new LuaTable();
        LuaTable imgProperties2 = new LuaTable();
        imgProperties2.set("normal", "btn");
        buttonProperties2.set("img", imgProperties);
        ButtonBridge result2 = buttonFactory.create("lua", buttonProperties);
        Assert.assertNull(result2);
    }

    @Test
    public void makeButton(){
        ButtonFactory buttonFactory = new ButtonFactory(activityTestRule.getActivity(), LunaError.getInstance());
        LuaTable buttonProperties = new LuaTable();
        buttonProperties.set("text", "Ola");
        buttonProperties.set("size", 30);
        ButtonBridge bridge = buttonFactory.create("lua", buttonProperties);
        Assert.assertThat(
                bridge,
                instanceOf(ButtonBridge.class));

        Assert.assertThat(
                bridge.getProxy().getAndroidView(),
                instanceOf(Button.class));
        Assert.assertEquals(60, ((Button)bridge.getProxy().getAndroidView()).getTextSize(), 0.1);


        Button buttonCreated = (Button)bridge.getProxy().getAndroidView();
        Assert.assertEquals("Ola", buttonCreated.getText());



        LuaTable imgButtonProperties = new LuaTable();
        LuaTable imgProperties = new LuaTable();
        imgProperties.set("normal", "outro.png");
        imgButtonProperties.set("img", imgProperties);
        ButtonBridge imgButtonBridge = buttonFactory.create("lua", imgButtonProperties);

        Assert.assertThat(
                imgButtonBridge,
                instanceOf(ButtonBridge.class));

        Assert.assertThat(
                imgButtonBridge.getProxy().getAndroidView(),
                instanceOf(ImageButton.class));

        ImageButton imageButtonCreated = (ImageButton)imgButtonBridge.getProxy().getAndroidView();
        Assert.assertNotNull(((BitmapDrawable)imageButtonCreated.getDrawable()).getBitmap());

    }


}
