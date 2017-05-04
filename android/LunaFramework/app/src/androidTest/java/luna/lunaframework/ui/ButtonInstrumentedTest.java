package luna.lunaframework.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.luaj.vm2.LuaTable;

import luna.framework.ui.button.ButtonBridge;
import luna.framework.ui.button.ButtonFactory;
import luna.framework.ui.button.LuaButtonBridge;
import luna.lunaframework.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by macbookair on 04/05/17.
 */

@RunWith(AndroidJUnit4.class)
public class ButtonInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    private ButtonBridge buttonFactory;

    public void testLuaButtonCreation(){

    }
}
