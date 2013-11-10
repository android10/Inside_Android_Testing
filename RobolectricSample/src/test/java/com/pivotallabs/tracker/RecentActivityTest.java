package com.pivotallabs.tracker;


import com.pivotallabs.TestResponses;
import com.pivotallabs.api.Xmls;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.pivotallabs.util.Strings.asStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RecentActivityTest {

    private RecentActivity recentActivity;

    @Before
    public void setUp() throws Exception {
        Document document = Xmls.getDocument(asStream(TestResponses.RECENT_ACTIVITY));
        Element activityElement = Xmls.getElement(document, "activity", 0);
        recentActivity = new RecentActivity();
        recentActivity.apply(activityElement);
    }

    @Test
    public void shouldParseResponseXML() throws Exception {
        assertThat(recentActivity.getDescription(), equalTo("I changed the 'request' for squidward. \"Add 'Buyout'\""));
    }
}
