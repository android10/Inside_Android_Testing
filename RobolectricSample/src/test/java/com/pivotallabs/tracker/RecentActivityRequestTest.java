package com.pivotallabs.tracker;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RecentActivityRequestTest {
    private RecentActivityRequest request;

    @Before
    public void setUp() throws Exception {
        request = new RecentActivityRequest("xyz123");
    }

    @Test
    public void shouldHaveUrl() throws Exception {
        assertThat(request.getUrlString(), equalTo("http://www.pivotaltracker.com/services/v3/activities?limit=25"));
    }

    @Test
    public void shouldIncludeAuthTokenInHeader() {
        assertThat(request.getHeaders().get("X-TrackerToken"), equalTo("xyz123"));
    }
}
