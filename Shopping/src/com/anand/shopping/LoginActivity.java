package com.anand.shopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

	}
	public void loged(View v)
	{
		EditText username_entered=(EditText)findViewById(R.id.username_edit);
		String username_enter=username_entered.getText().toString();


		EditText password_entered=(EditText)findViewById(R.id.password_edit);
		String password_enter=password_entered.getText().toString();

		SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//Data Edit
		String username_saved=sp.getString("username","");
		String password_saved=sp.getString("password","");
		String name_saved=sp.getString("name","");

		if(username_saved.equalsIgnoreCase(username_enter))
		{
			if(password_saved.equals(password_enter))
			{
				HomeActivity.name=name_saved;
				startActivity(new Intent(this,HomeActivity.class));
			}
			else {

				Toast.makeText(getApplicationContext(),"Password Error",Toast.LENGTH_LONG).show();

			}
		}
		else{

			Toast.makeText(getApplicationContext(),"Invalid user please Register First",Toast.LENGTH_LONG).show();
			startActivity(new Intent(this,RegisterActivity.class));
		}


	}

}
