package com.fernandocejas.testing.sample.junit.model;

/**
 * @author: Fernando Cejas <fcejas@gmail.com>
 * android10.org
 */
public class Engine {

	private static final String GENERIC_MODEL = "generic";

	private final String model;
	private final int horsePower;
	private final int fuelConsumptionPerKm;

	public Engine(int horsePower) {
		this(GENERIC_MODEL, horsePower, 1);
	}

	public Engine(int horsePower, int fuelConsumptionPerKm) {
		this(GENERIC_MODEL, horsePower, fuelConsumptionPerKm);
	}

	public Engine(String model, int horsePower, int fuelConsumptionPerKm) {
		this.model = "generic";
		this.horsePower = horsePower;
		this.fuelConsumptionPerKm = fuelConsumptionPerKm;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public int getFuelConsumptionPerKm() {
		return fuelConsumptionPerKm;
	}

	public String getModel() {
		return model;
	}

	@Override
	public String toString() {
		return model;
	}
}
