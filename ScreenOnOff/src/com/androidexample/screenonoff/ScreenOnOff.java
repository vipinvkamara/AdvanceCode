package com.androidexample.screenonoff;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class ScreenOnOff extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_on_off);
		
		// Start AEScreenOnOffService Service
		
		Intent i0 = new Intent(); 
		i0.setAction("com.androidexample.screenonoff.AEScreenOnOffService");
		startService(i0);
		
	}	
	
}