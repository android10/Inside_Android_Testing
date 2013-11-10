package com.pivotallabs.tracker;

import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class TrackerAuthenticationRequestTest  {
    private TrackerAuthenticationRequest request;

    @Before
    public void setUp() throws Exception {
        request = new TrackerAuthenticationRequest("SpongeBob", "Patrick");
    }

    @Test
    public void shouldHaveUrl() throws Exception {
        String urlString = request.getUrlString();
        assertThat(urlString, equalTo("https://www.pivotaltracker.com/services/v3/tokens/active"));
    }
    
    @Test
    public void shouldReturnUsernameAndPassword() {
        assertThat(request.getUsername(), equalTo("SpongeBob"));
        assertThat(request.getPassword(), equalTo("Patrick"));
    }
}
