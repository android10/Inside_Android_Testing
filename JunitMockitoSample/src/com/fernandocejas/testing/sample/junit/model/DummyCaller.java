/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.testing.sample.junit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DummyCollaborator class for several examples using Mockito
 */
public class DummyCaller implements DummyCallback {

	private final DummyCollaborator dummyCollaborator;

	private List<String> result = new ArrayList<String>();

	public DummyCaller(DummyCollaborator dummyCollaborator) {
		this.dummyCollaborator = dummyCollaborator;
	}

	public void doSomethingAsynchronously() {
		dummyCollaborator.doSomethingAsynchronously(this);
	}

	public List<String> getResult() {
		return this.result;
	}

	@Override
	public void onSuccess(List<String> result) {
		this.result = result;
		System.out.println("On success");
	}

	@Override
	public void onFail(int code) {
		System.out.println("On Fail");
	}
}
