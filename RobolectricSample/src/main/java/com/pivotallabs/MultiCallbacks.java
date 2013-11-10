package com.pivotallabs;

public class MultiCallbacks extends Callbacks{

    private Callbacks[] callbacks;

    public MultiCallbacks(Callbacks... callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onStart() {
        for (Callbacks callback : callbacks) {
            callback.onStart();
        }
    }

    @Override
    public void onSuccess() {
        for (Callbacks callback : callbacks) {
            callback.onSuccess();
        }
    }

    @Override
    public void onFailure() {
        for (Callbacks callback : callbacks) {
            callback.onFailure();
        }
    }

    @Override
    public void onComplete() {
        for (Callbacks callback : callbacks) {
            callback.onComplete();
        }
    }
}
