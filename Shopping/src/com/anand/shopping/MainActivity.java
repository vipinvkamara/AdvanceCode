package com.anand.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}
	public void login(View v)
	{
		startActivity(new Intent(this,LoginActivity.class));
	}
	public void register(View v)
	{
		startActivity(new Intent(this,RegisterActivity.class));
	}

}
