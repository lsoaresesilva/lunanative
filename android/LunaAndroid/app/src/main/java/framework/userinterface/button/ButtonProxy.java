package framework.userinterface.button;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;

import framework.error.LunaError;
import framework.syntax.data.LunaHashMapAdapter;
import framework.syntax.function.LunaFunctionAdapter;
import framework.userinterface.UserInterfaceComponent;
import luna.lunaandroid.MainActivity;


/**
 * This class is a abstraction to represent a native Button. It does not provide direct access to native button, instead encapsulates it providing access to only "allowed" methods.
 * Methods in this class share common signature between Android and iOS.
 * In Luna Lua API access to this class is made through lib.userinterface.button library.
 * For Lua it is not know which implementation will be called, as soon as the Native implementation provides these signatures.
 * Created by Leonardo Soares e Silva on 27/03/17.
 */
public class ButtonProxy extends UserInterfaceComponent {

    private static View createImageButton(LunaHashMapAdapter properties, MainActivity context){
        if(properties == null && properties.size() == 0 && context == null){
            LunaError.dispatch(1);
            return null;
        }

        try{
            final LunaHashMapAdapter imgSrc = (LunaHashMapAdapter) properties.get("img");
            final ImageButton buttonCreated = new ImageButton(context);
            InputStream imgFileNormal = context.getAssets().open("img/"+imgSrc.get("normal"));
            InputStream imgFilePressed = null;
            if( imgSrc.containsKey("pressed") ){
                imgFilePressed = context.getAssets().open("img/"+imgSrc.get("pressed"));
            }
            final Bitmap bitmapNormal = BitmapFactory.decodeStream(imgFileNormal);
            final Bitmap bitmapPressed = BitmapFactory.decodeStream(imgFilePressed);
            buttonCreated.setImageBitmap(bitmapNormal);

            buttonCreated.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                        buttonCreated.setImageBitmap(bitmapNormal);
                    }else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        if( imgSrc.containsKey("pressed") ){
                            buttonCreated.setImageBitmap(bitmapPressed);
                        }

                    }


                    return true;
                }
            });

            return buttonCreated;

        }catch(IOException e){
            LunaError.dispatch(8);
        }

        return null;
    }

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

        if( properties == null && properties.size() == 0 && context == null) {
            LunaError.dispatch(1);
            return null;
        }

        String text =  properties.containsKey("text")?(String)properties.get("text"):null;
        LunaHashMapAdapter imgSrc = properties.containsKey("img")?(LunaHashMapAdapter) properties.get("img"):null;

        View buttonCreated = null;

        if(imgSrc != null && imgSrc.containsKey("normal") ){

            buttonCreated = createImageButton(properties, context);

        }
        else{
            buttonCreated = new Button(context);
            if (text != null) {
                ((Button)buttonCreated).setText(text.toString());
            }else{
                LunaError.dispatch(7);
            }
        }


        return new ButtonProxy(buttonCreated, context);
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
        if(callBackName != null) {
            androidView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (callBackName.isFunction()) {
                        callBackName.execute();
                    }

                }
            });
        }else{
            LunaError.dispatch(6);
        }
    }
}
