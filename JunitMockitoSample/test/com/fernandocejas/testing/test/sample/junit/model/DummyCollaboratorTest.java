/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.testing.test.sample.junit.model;

import com.fernandocejas.testing.sample.junit.model.DummyCollaborator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DummyCollaboratorTest {

	private DummyCollaborator dummyCollaborator;

	@Before
	public void setUp() {
		dummyCollaborator = new DummyCollaborator();
	}

	@Test
	public void testDoSomethingAsynchronously() {
		assertThat(true, is(true));
	}
}
