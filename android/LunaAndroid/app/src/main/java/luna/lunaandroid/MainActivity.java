package luna.lunaandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity implements ResourceFinder {

    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        //userInterfaceTest();


        this.LuaJInitialization();
    }

    private void userInterfaceTest(){
        ViewGroup group = (RelativeLayout)findViewById(R.id.main_layout);

        ButtonFactory buttonFactory = new ButtonFactory(this, LunaError.getInstance());
        LuaTable buttonProperties = new LuaTable();
        LuaTable imgProperties = new LuaTable();
        imgProperties.set("normal", "outro.png");
        imgProperties.set("pressed", "outrobtn.png");
        buttonProperties.set("img", imgProperties);
        ButtonBridge bridge = buttonFactory.create("lua", buttonProperties);


        LuaTable textButtonProperties = new LuaTable();
        textButtonProperties.set("text", "Olá mundo!");

        ButtonBridge otherBridge = buttonFactory.create("lua", textButtonProperties);

        group.addView(otherBridge.getButtonProxy().getAndroidView());
        group.addView(bridge.getButtonProxy().getAndroidView());
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
