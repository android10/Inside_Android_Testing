package com.pivotallabs.api;

import com.pivotallabs.util.Strings;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.HttpRequestInfo;

import java.io.InputStream;
import java.util.Map;

import static com.pivotallabs.TestResponses.GENERIC_XML;
import static com.pivotallabs.util.TestUtil.asString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ApiGatewayTest {
    private ApiGateway apiGateway;
    private TestApiResponseCallbacks responseCallbacks;

    @Before
    public void setUp() throws Exception {
        apiGateway = new ApiGateway();
        responseCallbacks = new TestApiResponseCallbacks();
    }

    @Test
    public void dispatch_shouldCallOntoTheSuccessWhenApiResponseIsSuccess() throws Exception {
        XmlApiResponse apiResponse = new XmlApiResponse(200);
        apiGateway.dispatch(apiResponse, responseCallbacks);

        assertThat(responseCallbacks.successResponse, sameInstance(apiResponse));
        assertThat(responseCallbacks.failureResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenApiResponseIsFailure() throws Exception {
        XmlApiResponse apiResponse = new XmlApiResponse(500);
        apiGateway.dispatch(apiResponse, responseCallbacks);

        assertThat(responseCallbacks.failureResponse, sameInstance((ApiResponse) apiResponse));
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenXmlParseErrorOccurs() throws Exception {
        Robolectric.addPendingHttpResponse(200, "Invalid x-shmail");
        apiGateway.makeRequest(new TestGetRequest(), responseCallbacks);
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.failureResponse, not(nullValue()));
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenOnSuccessFails() throws Exception {
        XmlApiResponse apiResponse = new XmlApiResponse(200);

        TestApiResponseCallbacks callbacks = new TestApiResponseCallbacks() {
            @Override
            public void onSuccess(XmlApiResponse successResponse) {
                throw new RuntimeException("boom!");
            }
        };
        apiGateway.dispatch(apiResponse, callbacks);
        assertThat(callbacks.failureResponse, sameInstance((ApiResponse) apiResponse));
        assertThat(callbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void shouldMakeRemoteGetCalls() {
        Robolectric.getBackgroundScheduler().pause();

        TestGetRequest apiRequest = new TestGetRequest();
        apiGateway.makeRequest(apiRequest, responseCallbacks);

        Robolectric.addPendingHttpResponse(200, GENERIC_XML);

        Robolectric.getBackgroundScheduler().runOneTask();

        HttpRequestInfo sentHttpRequestData = Robolectric.getSentHttpRequestInfo(0);
        HttpRequest sentHttpRequest = sentHttpRequestData.getHttpRequest();
        assertThat(sentHttpRequest.getRequestLine().getUri(), equalTo("www.example.com"));
        assertThat(sentHttpRequest.getRequestLine().getMethod(), equalTo(HttpGet.METHOD_NAME));

        assertThat(sentHttpRequest.getHeaders("foo")[0].getValue(), equalTo("bar"));

        CredentialsProvider credentialsProvider =
                (CredentialsProvider) sentHttpRequestData.getHttpContext().getAttribute(ClientContext.CREDS_PROVIDER);
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getUserPrincipal().getName(), CoreMatchers.equalTo("spongebob"));
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getPassword(), CoreMatchers.equalTo("squarepants"));
    }

    @Test
    public void shouldMakeRemotePostCalls() throws Exception {
        Robolectric.getBackgroundScheduler().pause();

        TestPostRequest apiRequest = new TestPostRequest();
        apiGateway.makeRequest(apiRequest, responseCallbacks);

        Robolectric.addPendingHttpResponse(200, GENERIC_XML);

        Robolectric.getBackgroundScheduler().runOneTask();

        HttpRequestInfo sentHttpRequestData = Robolectric.getSentHttpRequestInfo(0);
        HttpRequest sentHttpRequest = sentHttpRequestData.getHttpRequest();
        assertThat(sentHttpRequest.getRequestLine().getUri(), equalTo("www.example.com"));
        assertThat(sentHttpRequest.getRequestLine().getMethod(), equalTo(HttpPost.METHOD_NAME));

        assertThat(sentHttpRequest.getHeaders("foo")[0].getValue(), equalTo("bar"));

        InputStream contentStream = ((HttpPost) sentHttpRequest).getEntity().getContent();

        assertThat(Strings.fromStream(contentStream), CoreMatchers.equalTo("post body content"));

        CredentialsProvider credentialsProvider =
                (CredentialsProvider) sentHttpRequestData.getHttpContext().getAttribute(ClientContext.CREDS_PROVIDER);
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getUserPrincipal().getName(), CoreMatchers.equalTo("spongebob"));
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getPassword(), CoreMatchers.equalTo("squarepants"));
    }

    @Test
    public void shouldDispatchUponReceivingResponse() throws Exception {
        Robolectric.getBackgroundScheduler().pause();
        Robolectric.getUiThreadScheduler().pause();

        Robolectric.addPendingHttpResponse(200, GENERIC_XML);

        apiGateway.makeRequest(new TestGetRequest(), responseCallbacks);
        Robolectric.getBackgroundScheduler().runOneTask();

        assertThat(responseCallbacks.successResponse, nullValue());

        Robolectric.getUiThreadScheduler().runOneTask();

        assertThat(asString(responseCallbacks.successResponse.getResponseDocument()), equalTo(GENERIC_XML));
    }

    private class TestGetRequest extends ApiRequest<XmlApiResponse> {
        @Override
        public String getUrlString() {
            return "www.example.com";
        }

        @Override
        public Map<String, String> getParameters() {
            Map<String, String> parameters = super.getParameters();
            parameters.put("baz", "bang");
            return parameters;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = super.getHeaders();
            headers.put("foo", "bar");
            return headers;
        }

        @Override
        public String getMethod() {
            return HttpGet.METHOD_NAME;
        }

        @Override
        public String getPostBody() {
            return super.getPostBody();
        }

        @Override
        public String getUsername() {
            return "spongebob";
        }

        @Override
        public String getPassword() {
            return "squarepants";
        }

        @Override
        public XmlApiResponse createResponse(int statusCode) {
            return new XmlApiResponse(statusCode);
        }
    }

    private class TestPostRequest extends ApiRequest {
        @Override
        public String getUrlString() {
            return "www.example.com";
        }

        @Override
        public Map<String, String> getParameters() {
            Map<String, String> parameters = super.getParameters();
            parameters.put("baz", "bang");
            return parameters;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = super.getHeaders();
            headers.put("foo", "bar");
            return headers;
        }

        @Override
        public String getMethod() {
            return HttpPost.METHOD_NAME;
        }

        @Override
        public String getPostBody() {
            return "post body content";
        }

        @Override
        public String getUsername() {
            return "spongebob";
        }

        @Override
        public String getPassword() {
            return "squarepants";
        }

        @Override
        public ApiResponse createResponse(int statusCode) {
            return new XmlApiResponse(statusCode);
        }
    }
}
