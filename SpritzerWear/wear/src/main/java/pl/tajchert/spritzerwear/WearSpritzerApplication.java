package pl.tajchert.spritzerwear;


import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import pl.tajchert.spritzerwearcommon.StoryRealm;

public class WearSpritzerApplication extends Application{
    private static Realm realm;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        realm = Realm.getInstance(this);
        context = this;
    }

    public static Realm getRealm() {
        if(realm == null){
            realm = Realm.getInstance(context);
        }
        try {
            realm.refresh();
        } catch (Exception e) {
            //Realm was closed earlier
            realm = Realm.getInstance(context);
        }
        return realm;
    }

    public static StoryRealm getStoryRealm (String title){
        return getRealm().where(StoryRealm.class).equalTo("title", title).findFirst();
    }

    public static void closeRealm(){
        if(realm != null){
            realm.close();
        }
    }
}
