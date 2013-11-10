package com.pivotallabs.api;

import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.HttpRequestInfo;

import java.net.URI;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.util.Strings.fromStream;

@RunWith(RobolectricTestRunner.class)
public class HttpTest {
    private Http http;

    @Before
    public void setup() {
        Robolectric.setDefaultHttpResponse(200, "OK");
        http = new Http();
    }

    @Test
    public void testGet_FormsCorrectRequest_noBasicAuth() throws Exception {
        Robolectric.addPendingHttpResponse(200, "OK");

        http.get("www.example.com", new HashMap<String, String>(), null, null);

        assertThat(((HttpUriRequest) Robolectric.getSentHttpRequest(0)).getURI(), equalTo(URI.create("www.example.com")));
    }

    @Test
    public void testGet_shouldApplyCorrectHeaders() throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("foo", "bar");
        http.get("www.example.com", headers, null, null);

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);
        assertThat(sentHttpRequest.getHeaders("foo")[0].getValue(), equalTo("bar"));
    }

    @Test
    public void testGet_ShouldUseCorrectHttpMethod() throws Exception {
        http.get("www.example.com", new HashMap<String, String>(), null, null);
        HttpUriRequest sentHttpRequest = (HttpUriRequest) Robolectric.getSentHttpRequest(0);
        assertThat(sentHttpRequest.getMethod(), equalTo(HttpGet.METHOD_NAME));
    }

    @Test
    public void testGet_FormsCorrectRequest_withBasicAuth() throws Exception {
        Robolectric.addPendingHttpResponse(200, "OK");
        http.get("www.example.com", new HashMap<String, String>(), "username", "password");
        HttpRequestInfo sentHttpRequestData = Robolectric.getSentHttpRequestInfo(0);

        CredentialsProvider credentialsProvider =
                (CredentialsProvider) sentHttpRequestData.getHttpContext().getAttribute(ClientContext.CREDS_PROVIDER);
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getUserPrincipal().getName(), equalTo("username"));
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getPassword(), equalTo("password"));
    }

    @Test
    public void shouldReturnCorrectResponse() throws Exception {
        Robolectric.addPendingHttpResponse(666, "it's all cool");

        Http.Response response = http.get("www.example.com", new HashMap<String, String>(), null, null);

        assertThat(fromStream(response.getResponseBody()), equalTo("it's all cool"));
        assertThat(response.getStatusCode(), equalTo(666));
    }

    @Test
    public void testPost_ShouldUseCorrectMethod() throws Exception {
        http.post("www.example.com", new HashMap<String, String>(), "a post body", null, null);

        HttpUriRequest sentHttpRequest = (HttpUriRequest) Robolectric.getSentHttpRequest(0);
        assertThat(sentHttpRequest.getMethod(), equalTo(HttpPost.METHOD_NAME));
    }

    @Test
    public void testPost_ShouldIncludePostBody() throws Exception {
        http.post("www.example.com", new HashMap<String, String>(), "a post body", null, null);

        HttpPost sentHttpRequest = (HttpPost) Robolectric.getSentHttpRequest(0);
        StringEntity entity = (StringEntity) sentHttpRequest.getEntity();
        String sentPostBody = fromStream(entity.getContent());
        assertThat(sentPostBody, equalTo("a post body"));
        assertThat(entity.getContentType().getValue(), equalTo("text/plain; charset=UTF-8"));
    }
}
