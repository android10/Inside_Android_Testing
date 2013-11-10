package com.fernandocejas.testing.test.sample.junit.model;

import com.fernandocejas.testing.sample.junit.model.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author: Fernando Cejas <fcejas@gmail.com>
 * android10.org
 */
public class EngineTest {

	@BeforeClass
	public static void executeBeforeAllTests() {
		//Empty
	}

	@AfterClass
	public static void executeAfterAllTests() {
		//Empty
	}

	@Test
	public void testEngineModel() {
		Engine engine = new Engine(10);

		String actualModel = engine.getModel();
		String expectedModel = "generic";

		assertThat(actualModel, is(equalTo(expectedModel)));
	}
}
