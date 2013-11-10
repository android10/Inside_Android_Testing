package com.pivotallabs;

public class TestChangeListener implements OnChangeListener {
    public boolean changed;

    @Override
    public void onChange() {
        changed = true;
    }
}
