package com.pivotallabs.tracker;

import com.pivotallabs.api.ApiRequest;
import com.pivotallabs.api.XmlApiResponse;


class TrackerAuthenticationRequest extends ApiRequest<XmlApiResponse> {
    private String username;
    private String password;

    public TrackerAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUrlString() {
        return "https://www.pivotaltracker.com/services/v3/tokens/active";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public XmlApiResponse createResponse(int statusCode) {
        return new XmlApiResponse(statusCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackerAuthenticationRequest that = (TrackerAuthenticationRequest) o;

        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
