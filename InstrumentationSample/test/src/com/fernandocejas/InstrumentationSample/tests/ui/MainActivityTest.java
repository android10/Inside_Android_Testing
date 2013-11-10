package com.fernandocejas.InstrumentationSample.tests.ui;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.mock.MockContext;
import android.test.mock.MockResources;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.InstrumentationSample.R;
import com.fernandocejas.InstrumentationSample.R.id;
import com.fernandocejas.InstrumentationSample.ui.MainActivity;
import com.fernandocejas.InstrumentationSample.ui.OtherActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.fernandocejas.InstrumentationSample.ui.MainActivityTest \
 * com.fernandocejas.InstrumentationSample.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mainActivity;

	public MainActivityTest() {
		super("com.fernandocejas.InstrumentationSample", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		mainActivity = getActivity();
	}

	public void testHelloWorldText() {
		TextView textView = (TextView)mainActivity.findViewById(id.tv_title);

		String actualValue = textView.getText().toString();

		assertEquals("GDG", actualValue);
	}

	public void testTextViewPosition() {
		RelativeLayout relativeLayoutContainer = (RelativeLayout)mainActivity.findViewById(R.id.rl_container);

		TextView textViewTitle = (TextView)mainActivity.findViewById(R.id.tv_title);
		TextView textViewDescription = (TextView)mainActivity.findViewById(R.id.tv_description);

		ViewAsserts.assertGroupContains(relativeLayoutContainer, textViewTitle);
		ViewAsserts.assertGroupContains(relativeLayoutContainer, textViewDescription);
	}

	public void testFakeMethod() {
		MockContext mockContext = new MockContext();

		int actualValue = mainActivity.fakeMethod(mockContext);

		assertEquals(1, actualValue);
	}

	public void testGetStringResource() {
		MockResources mockResources = new MyMockResources();

		String actual = mainActivity.getStringResource(mockResources);

		assertEquals("string", actual);
	}

	public void testLaunchActivity() {
		//register next activity that need to be monitored
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(OtherActivity.class.getName(), null, false);

		//open current activity
		final Button button = (Button)mainActivity.findViewById(R.id.bt_launch);
		mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				button.performClick();
			}
		});

		// let's wait to open the activity
		OtherActivity otherActivity = (OtherActivity)getInstrumentation().waitForMonitor(activityMonitor);

		// next activity is opened and captured.
		assertNotNull(otherActivity);
		otherActivity.finish();
	}

	private static class MyMockResources extends MockResources {
		@Override
		public String getString(int id) throws NotFoundException {
			return "string";
		}
	}
}
