package com.pivotallabs.tracker;

import com.pivotallabs.Callbacks;
import com.pivotallabs.MultiCallbacks;
import com.pivotallabs.api.ApiGateway;
import com.pivotallabs.api.ApiResponse;
import com.pivotallabs.api.ApiResponseCallbacks;
import com.pivotallabs.api.XmlApiResponse;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class RecentActivities extends ArrayList<RecentActivity> {
    private static final long serialVersionUID = 2810203342716301892L;

    private ApiGateway apiGateway;
    private AuthenticationGateway authenticationGateway;

    public RecentActivities(ApiGateway apiGateway, AuthenticationGateway authenticationGateway) {
        this.apiGateway = apiGateway;
        this.authenticationGateway = authenticationGateway;
    }

    public void update(Callbacks... callbacks) {
        MultiCallbacks multiCallbacks = new MultiCallbacks(callbacks);
        multiCallbacks.onStart();
        apiGateway.makeRequest(
                new RecentActivityRequest(authenticationGateway.getToken()),
                new RecentActivityApiResponseCallbacks(multiCallbacks));
    }

    private class RecentActivityApiResponseCallbacks implements ApiResponseCallbacks<XmlApiResponse> {
        private Callbacks callbacks;

        public RecentActivityApiResponseCallbacks(Callbacks callbacks) {
            this.callbacks = callbacks;
        }

        @Override
        public void onSuccess(XmlApiResponse response) {
            clear();
            NodeList activityNodeList = response.getResponseDocument().getElementsByTagName("activity");
            for (int i = 0; i < activityNodeList.getLength(); i++) {
                add(new RecentActivity().apply((Element) activityNodeList.item(i)));
            }
            callbacks.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse response) {
            callbacks.onFailure();
        }

        @Override
        public void onComplete() {
            callbacks.onComplete();
        }
    }
}
