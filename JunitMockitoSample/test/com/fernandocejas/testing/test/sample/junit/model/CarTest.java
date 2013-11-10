package com.fernandocejas.testing.test.sample.junit.model;

import com.fernandocejas.testing.sample.junit.model.Car;
import com.fernandocejas.testing.sample.junit.model.Engine;
import com.fernandocejas.testing.sample.junit.model.EngineV8;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author: Fernando Cejas <fcejas@gmail.com>
 * android10.org
 */
public class CarTest {

	private Car car;

	private Engine mockEngine;

	@Before
	public void executeBeforeEachTest() {
		mockEngine = mock(Engine.class);
		car = new Car(mockEngine);
	}

	@Ignore
	public void testIgnored() {
		assertThat(true, is(true));
	}

	@Ignore
	public void testFail() {
		fail("This a fail testing due to...");
	}

	@Test
	public void testGoingForward() {
		car.setFuel(100);
		car.moveForward(10);

		int actualKilometers = car.getKilometers();

		assertThat(actualKilometers, is(10));
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testGoingForwardWithoutFuel() {
		car.moveForward(100);
	}

	@Test
	public void testFuel() {
		car = new Car(mockEngine, 10);

		int actualFuel = car.getFuel();

		assertThat(actualFuel, is(10));

		verifyNoMoreInteractions(mockEngine);
	}

	@Test
	public void testFuelConsumption() {
		given(mockEngine.getFuelConsumptionPerKm()).willReturn(1);

		car = new Car(mockEngine, 100);
		car.moveForward(10);

		int actualFuel = car.getFuel();

		assertThat(actualFuel, is(90));

		verify(mockEngine, atLeast(1)).getFuelConsumptionPerKm();
		verifyNoMoreInteractions(mockEngine);
	}

	@Test
	public void testCarWithEngineV8() {
		mockEngine = mock(EngineV8.class);
		car = new Car(mockEngine);

		Engine actualEngine = car.getEngine();

		assertThat(actualEngine, is(instanceOf(EngineV8.class)));
		assertThat(actualEngine, is(sameInstance(mockEngine)));
	}
}
