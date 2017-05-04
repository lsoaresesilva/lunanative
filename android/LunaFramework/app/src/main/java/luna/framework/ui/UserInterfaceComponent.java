package luna.framework.ui;

import android.view.View;

import luna.lunaframework.MainActivity;

/**
 * Native user interface proxies must extends this class to provide access to the native component.
 * Created by Leonardo Soares on 30/03/17.
 */
public abstract class UserInterfaceComponent {

    protected View androidView;
    protected MainActivity _activity;

    public View getAndroidView() {
        return androidView;
    }

    public void setAndroidView(View androidView)
    {
        this.androidView = androidView;
    }
}
