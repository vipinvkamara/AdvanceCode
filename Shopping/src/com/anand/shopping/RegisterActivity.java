package com.anand.shopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

	}

	public void registered(View v)
	{
		
		String name_enter=((EditText)findViewById(R.id.name_edit)).getText().toString();
		String username_enter=((EditText)findViewById(R.id.username_edit)).getText().toString();

		
		String password_enter=((EditText)findViewById(R.id.password_edit)).getText().toString();

		
		String place_enter=((EditText)findViewById(R.id.place_edit)).getText().toString();

		
		String phone_enter=((EditText)findViewById(R.id.phone_edit)).getText().toString();

		String credit_enter=((EditText)findViewById(R.id.credit_edit)).getText().toString();

		
		String email_enter=((EditText)findViewById(R.id.email_edit)).getText().toString();

		Editor ed=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();//Data Storage
		ed.putString("name",name_enter);
		ed.putString("username",username_enter);
		ed.putString("password",password_enter);
		ed.putString("place",place_enter);
		ed.putString("phone",phone_enter);
		ed.putString("credit",credit_enter);
		ed.putString("email",email_enter);
		ed.commit();

		startActivity(new Intent(this,LoginActivity.class));

	}
}
