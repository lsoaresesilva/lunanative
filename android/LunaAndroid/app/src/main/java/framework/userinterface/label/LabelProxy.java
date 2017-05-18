package framework.userinterface.label;

import android.util.TypedValue;
import android.widget.TextView;

import framework.error.LunaError;
import framework.syntax.data.LunaHashMapAdapter;
import framework.userinterface.UserInterfaceComponent;
import luna.lunaandroid.MainActivity;

/**
 * Created by macbookair on 18/05/17.
 */

public class LabelProxy extends UserInterfaceComponent {

    private MainActivity activity;
    private LunaError errorHandling;

    public static LabelProxy create(LunaHashMapAdapter properties, MainActivity activity, LunaError errorHandling) {
        if( properties == null || activity == null) {
            errorHandling.dispatch(1);
            return null;
        }

        if( properties.size() == 0 || !properties.containsKey("text") ){
            errorHandling.dispatch(7);
            return null;
        }

        TextView label = new TextView(activity);
        label.setText((String)properties.get("text"));

        if(properties.containsKey("size") && (properties.get("size") instanceof Double)){

            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)((Double)properties.get("size")).doubleValue());
        }

        return new LabelProxy(label, activity);
    }

    public LabelProxy(TextView label, MainActivity activity){
        super(label, activity);
    }
}
