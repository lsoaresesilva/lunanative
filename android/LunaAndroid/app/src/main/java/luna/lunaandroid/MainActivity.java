package luna.lunaandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.stetho.Stetho;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;

import framework.error.LunaError;
import framework.rest.RestFactory;
import framework.userinterface.button.ButtonBridge;
import framework.userinterface.button.ButtonFactory;
import framework.userinterface.label.LabelBridge;
import framework.userinterface.label.LabelFactory;

public class MainActivity extends AppCompatActivity implements ResourceFinder {

    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        userInterfaceTest();


        this.LuaJInitialization();
    }

    private void userInterfaceTest(){
        ViewGroup group = (RelativeLayout)findViewById(R.id.main_layout);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        group.addView(layout);


        ButtonFactory buttonFactory = new ButtonFactory(this, LunaError.getInstance());
        LabelFactory labelFactory = new LabelFactory(this, LunaError.getInstance());



        LuaTable buttonProperties = new LuaTable();
        LuaTable imgProperties = new LuaTable();
        imgProperties.set("normal", "outro.png");
        imgProperties.set("pressed", "outrobtn.png");
        buttonProperties.set("img", imgProperties);
        ButtonBridge bridge = buttonFactory.create("lua", buttonProperties);

        LuaTable textButtonProperties = new LuaTable();
        textButtonProperties.set("text", "Olá mundo!");
        textButtonProperties.set("size", 30);
        ButtonBridge otherBridge = buttonFactory.create("lua", textButtonProperties);

        LuaTable labelProperties = new LuaTable();
        labelProperties.set("text", "Olá mundo!");
        labelProperties.set("size", 20);
        LabelBridge labelBridge = labelFactory.create("lua", labelProperties);

        layout.addView(otherBridge.getProxy().getAndroidView());
        layout.addView(bridge.getProxy().getAndroidView());
        layout.addView(labelBridge.getProxy().getAndroidView());
    }

    private void LuaJInitialization(){
        try {
            globals = JsePlatform.standardGlobals();
            globals.finder = this;
            LuaValue chunk = null;

            RestFactory restFactory = new RestFactory();
            LunaError lunaError = LunaError.getInstance();
            globals.set("RestFactory", CoerceJavaToLua.coerce(restFactory));
            globals.set("LunaError", CoerceJavaToLua.coerce(lunaError));
            chunk = globals.loadfile("init.lua");

            chunk.call();

            //LunaError.dispatch(1);
        }catch(Exception e ){
            Log.e("error", e.getMessage());

        }
    }

    public Globals getGlobals() {
        return globals;
    }

    public InputStream findResource(String name) {
        try {
            return getAssets().open(name);
        } catch (java.io.IOException ioe) {
            return null;
        }
    }
}
