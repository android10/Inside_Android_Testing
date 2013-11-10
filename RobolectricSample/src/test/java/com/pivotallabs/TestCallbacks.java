package com.pivotallabs;

public class TestCallbacks extends Callbacks{
    public boolean startWasCalled;
    public boolean successWasCalled;
    public boolean failureWasCalled;
    public boolean completeWasCalled;

    @Override
    public void onStart() {
        startWasCalled = true;
    }

    @Override
    public void onSuccess() {
        successWasCalled = true;
    }

    @Override
    public void onFailure() {
        failureWasCalled = true;
    }

    @Override
    public void onComplete() {
        completeWasCalled = true;
    }
}
