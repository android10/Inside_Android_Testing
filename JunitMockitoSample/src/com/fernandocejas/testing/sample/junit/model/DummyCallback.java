/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.testing.sample.junit.model;

import java.util.List;

/**
 * Dummy callback used as an example
 */
public interface DummyCallback {
	public void onSuccess(List<String> result);
	public void onFail(int code);
}
