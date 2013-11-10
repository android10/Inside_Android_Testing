package com.fernandocejas.InstrumentationSample.tests.model;

import junit.framework.TestCase;

import com.fernandocejas.InstrumentationSample.model.Calculator;

/**
 * Copyright (c) Tuenti Technologies. All rights reserved.
 *
 * @author "Fernando Cejas" <fcejas@tuenti.com>
 */
public class CalculatorTest extends TestCase {

	private Calculator calculator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		calculator = new Calculator();
	}

	public void testSum() {
		int number = 2;

		int actualResult = calculator.sum(number, number);

		assertEquals(4, actualResult);
	}
}
