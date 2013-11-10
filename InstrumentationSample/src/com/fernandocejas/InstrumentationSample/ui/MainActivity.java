package com.fernandocejas.InstrumentationSample.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fernandocejas.InstrumentationSample.R;
import com.fernandocejas.InstrumentationSample.R.id;

public class MainActivity extends Activity {

	private Button bt_launch;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bt_launch = (Button)findViewById(id.bt_launch);
		bt_launch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this, OtherActivity.class);
				startActivity(intent);
			}
		});
	}

	public int fakeMethod(Context context) {
		return (context != null) ? 1 : 0;
	}

	public String getStringResource(Resources resources) {
		return resources.getString(1);
	}
}
