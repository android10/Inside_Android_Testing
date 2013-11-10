package com.fernandocejas.testing.sample.junit.model;

/**
 * @author: Fernando Cejas <fcejas@gmail.com>
 * android10.org
 */
public class Car {

	private final Engine engine;

	private int kilometers = 0;
	private int fuel;

	public Car(Engine engine) {
		this(engine, 0);
	}

	public Car(Engine engine, int fuel) {
		this.engine = engine;
		this.fuel = fuel;
	}

	public Engine getEngine() {
		return engine;
	}

	public int getKilometers() {
		return kilometers;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public void moveForward(int kilometers) {
		if (fuel > 0) {
			final int fuelConsumption = engine.getFuelConsumptionPerKm();

			if (isFuelEnough(kilometers, fuelConsumption)) {
				this.kilometers += kilometers;
				this.fuel -= fuelConsumption * kilometers;
			}

		} else {
			throw new UnsupportedOperationException("There is not enough fuel...");
		}
	}

	private boolean isFuelEnough(int kilometers, int fuelConsumptionPerKm) {
		int totalFuelConsumed = fuelConsumptionPerKm * kilometers;

		return (totalFuelConsumed < fuel);
	}
}
