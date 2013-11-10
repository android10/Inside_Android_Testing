package com.pivotallabs.injected;

import android.util.Log;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import roboguice.util.Ln;

import java.util.Date;

public class RobolectricSampleTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Counter.class).in(Scopes.SINGLETON);
        bind(Date.class).toProvider(FakeDateProvider.class);
        bind(Ln.BaseConfig.class).toInstance(new RobolectricLoggerConfig());
    }


    static class RobolectricLoggerConfig extends Ln.BaseConfig {
        public RobolectricLoggerConfig() {
            super();
            this.packageName = "robo";
            this.minimumLogLevel = Log.VERBOSE;
            this.scope = "ROBO";
        }
    }
}