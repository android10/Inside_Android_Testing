package com.pivotallabs;

public class TestResponses {
    public static final String RECENT_ACTIVITY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<activities type=\"array\">\n" +
            "    <activity>\n" +
            "        <id type=\"integer\">31457152</id>\n" +
            "        <version type=\"integer\">4887</version>\n" +
            "        <event_type>note_create</event_type>\n" +
            "        <occurred_at type=\"datetime\">2010/10/14 20:08:41 UTC</occurred_at>\n" +
            "        <author>Patrick</author>\n" +
            "        <project_id type=\"integer\">123</project_id>\n" +
            "        <description>I changed the 'request' for squidward. &quot;Add 'Buyout'&quot;</description>\n" +
            "        <stories>\n" +
            "            <story>\n" +
            "                <id type=\"integer\">4270226</id>\n" +
            "                <url>http://www.pivotaltracker.com/services/v3/projects/123/stories/4270226</url>\n" +
            "                <notes>\n" +
            "                    <note>\n" +
            "                        <id type=\"integer\">2719505</id>\n" +
            "                        <text>I changed the 'request' for squidward. </text>\n" +
            "                    </note>\n" +
            "                </notes>\n" +
            "            </story>\n" +
            "        </stories>\n" +
            "    </activity>\n" +
            "    <activity>\n" +
            "        <id type=\"integer\">31457156</id>\n" +
            "        <version type=\"integer\">4888</version>\n" +
            "        <event_type>story_update</event_type>\n" +
            "        <occurred_at type=\"datetime\">2010/10/14 20:08:41 UTC</occurred_at>\n" +
            "        <author>Spongebob Squarepants</author>\n" +
            "        <project_id type=\"integer\">123</project_id>\n" +
            "        <description>Spongebob Square edited &quot;Application tracks listing clicks&quot;</description>\n" +
            "        <stories>\n" +
            "            <story>\n" +
            "                <id type=\"integer\">4270226</id>\n" +
            "                <url>http://www.pivotaltracker.com/services/v3/projects/123/stories/4270226</url>\n" +
            "                <description>fire req to analytics server addr</description>\n" +
            "            </story>\n" +
            "        </stories>\n" +
            "    </activity>\n" +
            "</activities>";

    public static final String AUTH_SUCCESS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<token>\n" +
            "  <guid>c93f12c</guid>\n" +
            "  <id type=\"integer\">1</id>\n" +
            "</token>";

    public static final String GENERIC_XML =
            "<foo>\n" +
            "  <bar>baz</bar>\n" +
            "</foo>";

}
