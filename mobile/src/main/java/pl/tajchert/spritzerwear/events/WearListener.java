package pl.tajchert.spritzerwear.events;


import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import pl.tajchert.spritzerwear.FileSender;
import pl.tajchert.spritzerwearcommon.Tools;

public class WearListener extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if(Tools.WEAR_REFRESH_REQUEST.equals(messageEvent.getPath())){
            FileSender.syncRealm(WearListener.this);
        }
    }
}
