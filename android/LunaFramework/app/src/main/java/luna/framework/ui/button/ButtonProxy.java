package luna.framework.ui.button;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.luaj.vm2.LuaError;

import java.io.IOException;
import java.io.InputStream;

import luna.framework.syntax.data.LunaHashMapAdapter;
import luna.framework.syntax.function.LunaFunctionAdapter;
import luna.framework.ui.UserInterfaceComponent;
import luna.lunaframework.MainActivity;

/**
 * This class is a abstraction to represent a native Button. It does not provide direct access to native button, instead encapsulates it providing access to only "allowed" methods.
 * Methods in this class share common signature between Android and iOS.
 * In Luna Lua API access to this class is made through lib.userinterface.button library.
 * For Lua it is not know which implementation will be called, as soon as the Native implementation provides these signatures.
 * Created by Leonardo Soares e Silva on 27/03/17.
 */
public class ButtonProxy extends UserInterfaceComponent {

    /**
     * Creates a instance of ButtonNativeFactory.
     * Internally it creates an instance of native button configured with properties, which is a LuaTable, passed to this method as parameter.
     * A button can be represented as text only or with image. The properties passed as parameter must include a text or img key.
     * The former is the text to be displayed in the button, img is a LuaTable with two keys: normal and pressed, values for these keys are the name of the image to be used when for a normal and pressed state button. This image must be located inside assets/img folder.
     * @param context An instance of Context.
     * @param properties An instance of LuaTable with
     * @return
     */
    public static ButtonProxy newButtonProxy(LunaHashMapAdapter properties, MainActivity context){

        if( properties != null && properties.size() > 0) {

            Integer identifier =  properties.containsKey("id")?(Integer)properties.get("id"):null;

            String text =  properties.containsKey("text")?(String)properties.get("text"):null;
            LunaHashMapAdapter imgSrc = properties.containsKey("img")?(LunaHashMapAdapter) properties.get("img"):null;

            View buttonCreated;

            if(imgSrc != null && imgSrc.containsKey("normal") ){

                buttonCreated = new ImageButton(context);
                try{
                    InputStream imgFileNormal = context.getAssets().open("img/"+imgSrc.get("normal"));
                    InputStream imgFilePressed = null;
                    if( imgSrc.containsKey("pressed") ){
                        imgFilePressed = context.getAssets().open("img/"+imgSrc.get("pressed"));
                    }
                    Bitmap bitmapNormal = BitmapFactory.decodeStream(imgFileNormal);
                    Bitmap bitmapPressed = BitmapFactory.decodeStream(imgFilePressed);
                    ((ImageButton)buttonCreated).setImageBitmap(bitmapNormal);
                }catch(IOException e){
                    throw new IllegalArgumentException("Image for button not found.");
                }
            }
            else{
                buttonCreated = new Button(context);
                if (text != null) {
                    ((Button)buttonCreated).setText(text.toString());
                }else{
                    throw new IllegalArgumentException("Missing properties 'text' for button creation.");
                }
            }

            if (identifier != null ) {
                buttonCreated.setId(identifier);
            }

            return new ButtonProxy(buttonCreated, context);

        }else{
            // there must be at least a text property
            throw new IllegalArgumentException("Missing properties for button creation.");
        }
    }

    private ButtonProxy(View _target, MainActivity activity) {
        this.androidView = _target;
        this._activity = activity;

    }

    public void setIdentifier(int id){
        androidView.setId(id);
    }


    public MainActivity get_activity() {
        return _activity;
    }

    public void setTouchCallback(final LunaFunctionAdapter callBackName){
        androidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( callBackName.isFunction()  ){
                    callBackName.execute();
                }

            }
        });
    }
}
