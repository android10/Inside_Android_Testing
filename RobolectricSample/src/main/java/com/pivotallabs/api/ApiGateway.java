package com.pivotallabs.api;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.InputStream;

public class ApiGateway {

    private final Http http = new Http();

    public <T extends ApiResponse> void makeRequest(ApiRequest<T> apiRequest, final ApiResponseCallbacks<T> responseCallbacks) {
        new RemoteCallTask(responseCallbacks).execute(apiRequest);
    }

    protected void dispatch(ApiResponse apiResponse, ApiResponseCallbacks responseCallbacks) {
        if (apiResponse.isSuccess()) {
            try {
                responseCallbacks.onSuccess(apiResponse);
            } catch (Exception e) {
                Log.e(ApiGateway.class.getName(), "Error proccessing response", e);
                responseCallbacks.onFailure(apiResponse);
            }
        } else {
            responseCallbacks.onFailure(apiResponse);
        }
        responseCallbacks.onComplete();
    }

    private class RemoteCallTask extends AsyncTask<ApiRequest, Void, ApiResponse> {
        private final ApiResponseCallbacks responseCallbacks;

        public RemoteCallTask(ApiResponseCallbacks responseCallbacks) {
            this.responseCallbacks = responseCallbacks;
        }

        @Override
        protected ApiResponse doInBackground(ApiRequest... apiRequests) {
            ApiRequest apiRequest = apiRequests[0];
            InputStream responseBody = null;
            try {
                Http.Response response;
                if (HttpPost.METHOD_NAME.equals(apiRequest.getMethod())) {
                    response = http.post(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.getPostBody(), apiRequest.getUsername(), apiRequest.getPassword());
                } else if (HttpGet.METHOD_NAME.equals(apiRequest.getMethod())) {
                    response = http.get(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.getUsername(), apiRequest.getPassword());
                } else {
                    throw new RuntimeException("Unsupported Http Method!");
                }

                responseBody = response.getResponseBody();
                ApiResponse apiResponse = apiRequest.createResponse(response.getStatusCode());
                apiResponse.consumeResponse(responseBody);
                return apiResponse;
            } catch (Exception e) {
                return apiRequest.createResponse(-1);
            } finally {
                if (responseBody != null) {
                    try {
                        responseBody.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(ApiResponse apiResponse) {
            dispatch(apiResponse, responseCallbacks);
        }
    }
}
