package com.pivotallabs.injected;

import android.app.Application;
import com.google.inject.Module;
import roboguice.RoboGuice;

public class SampleRoboApplication extends Application {
    private Module module = new RobolectricSampleModule();

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new RobolectricSampleModule());
    }
}
