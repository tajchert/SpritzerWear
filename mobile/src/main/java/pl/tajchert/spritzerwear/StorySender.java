package pl.tajchert.spritzerwear;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;

import java.util.ArrayList;

import pl.tajchert.spritzerwearcommon.Story;
import pl.tajchert.spritzerwearcommon.Tools;

public class StorySender extends AsyncTask<Story, Void, String> {
    private ArrayList<Story> stories;
    private Context context;
    private GoogleApiClient mGoogleAppiClient;


    public StorySender(ArrayList<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    @Override
    protected String doInBackground(Story... params) {
        sendData(stories);
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onPreExecute() {
        mGoogleAppiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                    }
                })
                .addApi(Wearable.API)
                .build();
        mGoogleAppiClient.connect();
    }

    @Override
    protected void onProgressUpdate(Void... values) {}

    private void sendData(ArrayList<Story> storiesToSend) {
        if(storiesToSend == null || storiesToSend.size() == 0){
            return;
        }
        PutDataMapRequest dataMap = PutDataMapRequest.create(Tools.WEAR_PATH);
        DataMap dataMapToPut = dataMap.getDataMap();
        JSONArray contentList = new JSONArray();
        for(Story story : storiesToSend){
            contentList.put(story.toJSONObject());
        }

        dataMapToPut.putString(Tools.WEAR_KEY, contentList.toString());
        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleAppiClient, request);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
                mGoogleAppiClient.disconnect();
            }
        });

    }
}