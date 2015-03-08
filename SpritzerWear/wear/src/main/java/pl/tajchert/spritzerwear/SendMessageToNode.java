package pl.tajchert.spritzerwear;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;


public class SendMessageToNode extends Thread {
    public static final long CONNECTION_TIME_OUT_MS = 100;
    private static final String TAG = "SendMessageToNode";
    private byte[] objectArray;
    private Context context;
    private String path;

    public SendMessageToNode(String messagePath, String content, Context ctx) {
        context = ctx;
        path = messagePath;
        if(content != null){
            objectArray = content.getBytes();
        } else {
            objectArray = "".getBytes();
        }
    }

    public void run() {
        if ((objectArray.length / 1024) > 100) {
            throw new RuntimeException("Object is too big to push it via Google Play Services");
        }
        GoogleApiClient googleApiClient = SendWearManager.getInstance(context);
        googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
        if(nodes != null) {
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result;
                result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), path, objectArray).await();
                if (!result.getStatus().isSuccess()) {
                    Log.v(TAG, "ERROR: failed to send Message via Google Play Services");
                }
                Log.d(TAG, "run send:");
            }
        }
    }
}