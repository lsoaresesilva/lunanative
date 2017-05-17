package luna.lunaandroid.userinterface.button;

import android.graphics.drawable.BitmapDrawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

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
import luna.lunaandroid.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by macbookair on 11/05/17.
 */

@RunWith(AndroidJUnit4.class)
public class BtTests {

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
        ButtonBridge bridge = buttonFactory.create("lua", buttonProperties);
        Assert.assertThat(
                bridge,
                instanceOf(ButtonBridge.class));

        Assert.assertThat(
                bridge.getButtonProxy().getAndroidView(),
                instanceOf(Button.class));

        Button buttonCreated = (Button)bridge.getButtonProxy().getAndroidView();
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
                imgButtonBridge.getButtonProxy().getAndroidView(),
                instanceOf(ImageButton.class));

        ImageButton imageButtonCreated = (ImageButton)imgButtonBridge.getButtonProxy().getAndroidView();
        Assert.assertNotNull(((BitmapDrawable)imageButtonCreated.getDrawable()).getBitmap());

    }


}
