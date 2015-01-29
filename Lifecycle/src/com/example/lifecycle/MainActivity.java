package com.example.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(getApplicationContext(), "On create", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(getApplicationContext(), "On Start", Toast.LENGTH_SHORT).show();

	}
	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText(getApplicationContext(), "On onResume", Toast.LENGTH_SHORT).show();

	}
	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(getApplicationContext(), "On onPause", Toast.LENGTH_SHORT).show();

	}
	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText(getApplicationContext(), "On onStop", Toast.LENGTH_SHORT).show();

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "On onDestroy", Toast.LENGTH_SHORT).show();

	}

}
