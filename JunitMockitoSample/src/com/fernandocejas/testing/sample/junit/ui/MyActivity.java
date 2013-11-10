package com.fernandocejas.testing.sample.junit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.fernandocejas.testing.sample.junit.R;
import com.fernandocejas.testing.sample.junit.model.Car;
import com.fernandocejas.testing.sample.junit.model.Engine;

public class MyActivity extends Activity {

	private TextView tv_engine;
	private TextView tv_kilometers;
	private TextView tv_fuel;

	private Button bt_create;
	private Button bt_move;

	private Car car;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapGUI();
		createCar();
	}

	private void mapGUI() {
		tv_engine = (TextView)findViewById(R.id.tv_engine);
		tv_kilometers = (TextView)findViewById(R.id.tv_kilometers);
		tv_fuel = (TextView)findViewById(R.id.tv_fuel);

		bt_create = (Button)findViewById(R.id.bt_create);
		bt_create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createCar();
			}
		});

		bt_move = (Button)findViewById(R.id.bt_move);
		bt_move.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				moveCar();
			}
		});
	}

	private void createCar() {
		Engine engine = new Engine("V6", 1000, 1);
		car = new Car(engine, 1000);
		showCarData();
	}

	private void moveCar() {
		final int kilometers = 10;

		if (car != null) {
			car.moveForward(kilometers);
			showCarData();
			Toast.makeText(this, "Car moved: " + kilometers + " kms", Toast.LENGTH_SHORT).show();
		}
	}

	private void showCarData() {
		//TODO: Refactor this and use resources
		if (car != null) {
			tv_engine.setText("Engine: " + car.getEngine().getModel());
			tv_kilometers.setText("Kilometers: " + car.getKilometers());
			tv_fuel.setText("Fuel: " + car.getFuel());
		}
	}
}
