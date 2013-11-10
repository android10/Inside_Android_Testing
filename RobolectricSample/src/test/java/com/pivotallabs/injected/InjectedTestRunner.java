package com.pivotallabs.injected;

import android.app.Application;
import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

public class InjectedTestRunner extends RobolectricTestRunner {

    public InjectedTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected Application createApplication() {
        SampleRoboApplication application = (SampleRoboApplication) super.createApplication();
        application.setModule(new RobolectricSampleTestModule());
        return application;
    }

    @Override
    public void prepareTest(Object test) {
        SampleRoboApplication application = (SampleRoboApplication) Robolectric.application;

        RoboGuice.setBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(application), new RobolectricSampleTestModule());

        RoboGuice.getInjector(application).injectMembers(test);
    }
}
