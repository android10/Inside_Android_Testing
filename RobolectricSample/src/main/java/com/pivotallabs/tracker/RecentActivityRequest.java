package com.pivotallabs.tracker;

import com.pivotallabs.api.ApiRequest;
import com.pivotallabs.api.XmlApiResponse;

import java.util.Map;

public class RecentActivityRequest extends ApiRequest<XmlApiResponse> {
    private String token;

    public RecentActivityRequest(String token) {
        super();
        this.token = token;
    }

    @Override
    public String getUrlString() {
        return "http://www.pivotaltracker.com/services/v3/activities?limit=25";
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = super.getHeaders();
        headers.put("X-TrackerToken", token);
        return headers;
    }

    @Override
    public XmlApiResponse createResponse(int statusCode) {
        return new XmlApiResponse(statusCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentActivityRequest that = (RecentActivityRequest) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
