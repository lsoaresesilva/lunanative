package luna.lunaframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.stetho.Stetho;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;

import luna.framework.ui.button.ButtonBridge;
import luna.framework.ui.button.ButtonFactory;

public class MainActivity extends AppCompatActivity /*implements ResourceFinder*/ {

    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        RelativeLayout r = (RelativeLayout)findViewById(R.id.main_layout);


        //r.addView(b.getButtonProxy().getAndroidView());
            /*NativeInterface nativeInterface = new NativeInterface(this);
            DBAdapter dbAdapter = new DBAdapter(this);
            GeoLocationNative glNative = new GeoLocationNative(this);
            RestNative restNative = new RestNative();

            globals.set("NativeInterface", CoerceJavaToLua.coerce(nativeInterface));
            globals.set("DBAdapter", CoerceJavaToLua.coerce(dbAdapter));
            globals.set("GeoLocationNative", CoerceJavaToLua.coerce(glNative));
            globals.set("RestNative", CoerceJavaToLua.coerce(restNative));*/

        //this.LuaJInitialization();


    }

    /*private void LuaJInitialization(){
        try {
            globals = JsePlatform.standardGlobals();
            globals.finder = this;
            LuaValue chunk = null;

            ButtonFactory buttonFactory = new ButtonFactory(this);
            globals.set("ButtonFactory", CoerceJavaToLua.coerce(buttonFactory));
            chunk = globals.loadfile("init.lua");

            chunk.call();
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
    }*/
}
