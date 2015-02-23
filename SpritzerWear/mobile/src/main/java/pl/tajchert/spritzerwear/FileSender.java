package pl.tajchert.spritzerwear;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import pl.tajchert.spritzerwearcommon.Story;
import pl.tajchert.spritzerwearcommon.Tools;

public class FileSender extends AsyncTask<Story, Void, String> {
    private Asset asset;
    private Context context;
    private GoogleApiClient mGoogleAppiClient;
    private static final String TAG = "AssetsSender";


    public FileSender(Asset asset, Context context) {
        this.asset = asset;
        this.context = context;
    }

    @Override
    protected String doInBackground(Story... params) {
        sendData(asset);
        return "";
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

    private void sendData(Asset asset) {
        if(asset == null){
            return;
        }
        PutDataMapRequest dataMap = PutDataMapRequest.create(Tools.WEAR_PATH);
        byte[] arr = asset.getData();
        dataMap.getDataMap().putByteArray(Tools.DATA_ASSET_FILE, arr);
        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleAppiClient, request);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
                Log.d(TAG, "onResult result:" + dataItemResult.getStatus());
            }
        });
    }
}