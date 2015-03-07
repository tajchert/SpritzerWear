package pl.tajchert.spritzerwear;


import android.app.Application;

import pl.tajchert.detectwear.DetectWear;

public class SpritzerWearApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DetectWear.init(this);
    }
}
