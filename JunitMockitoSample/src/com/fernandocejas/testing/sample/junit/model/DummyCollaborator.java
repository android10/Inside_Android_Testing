/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.testing.sample.junit.model;

import java.util.Collections;

/**
 * DummyCollaborator class for several examples using Mockito
 */
public class DummyCollaborator {

	public static int ERROR_CODE = 1;

	public DummyCollaborator() {
		// empty
	}

	public void doSomethingAsynchronously (final DummyCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					callback.onSuccess(Collections.EMPTY_LIST);
				} catch (InterruptedException e) {
					callback.onFail(ERROR_CODE);
					e.printStackTrace();
				}
			}
		}).start();
	}
}
