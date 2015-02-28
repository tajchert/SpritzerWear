package pl.tajchert.detectwear;


import android.content.Context;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class DetectWear implements NodeApi.NodeListener {
    private static ArrayList<Node> nodesList = new ArrayList<>();
    private static DetectNodes detectNodes;

    public boolean getNodes(Context context){
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi
                .getConnectedNodes(WearApiClientManager.getInstance(context))
                .await();

        Wearable.NodeApi.addListener(WearApiClientManager.getInstance(context), this).await();
        if(nodes.getNodes() == null){
            return false;
        }
        nodesList = new ArrayList<>();
        for (Node node : nodes.getNodes()) {
            nodesList.add(node);
        }
        return nodes.getNodes().size() > 0;

    }

    @Override
    public void onPeerConnected(Node node) {
        if(!nodesList.contains(node)){
            nodesList.add(node);
        }
        detectNodes.nodesChanged(nodesList);
    }

    @Override
    public void onPeerDisconnected(Node node) {
        if(nodesList.contains(node)){
            nodesList.remove(node);
        }
        detectNodes.nodesChanged(nodesList);
    }

    public static interface DetectNodes {
        void nodesChanged(ArrayList<Node> nodes);
    }
}
