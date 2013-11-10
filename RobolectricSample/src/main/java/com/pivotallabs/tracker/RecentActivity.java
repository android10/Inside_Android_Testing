package com.pivotallabs.tracker;

import org.w3c.dom.Element;

import static com.pivotallabs.api.Xmls.getTextContentOfChild;

public class RecentActivity {

    private String description;

    public RecentActivity apply(Element recentActivityElement) {
        this.description = getTextContentOfChild(recentActivityElement, "description");
        return this;
    }

    public RecentActivity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }
}
