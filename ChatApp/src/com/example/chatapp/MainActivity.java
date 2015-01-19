package com.example.chatapp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	String myMessage;
	TextView txtMessageReceived;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtMessageReceived=((TextView)findViewById(R.id.textView1));

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ServerSocket myServerSocket;
					Socket mySocket;
					InputStream ins;
					String s="";
					byte[] b=new byte[10];

					while (true) {
						myServerSocket=new ServerSocket(8888);
						System.out.println("Still Waiting for a connection");
						mySocket=myServerSocket.accept();	
						System.out.println("connected succesfully");
						ins=mySocket.getInputStream();
						s="";

						while(ins.read(b)>0){
							s=s+(new String(b));
							b=new byte[10];
						}
						mySocket.close();
						runOnUiThread(new Runnable() {
							public void run() {
								txtMessageReceived.setText(txtMessageReceived.getText().toString()+"\n"+myMessage);

							}
						});
						System.out.println(myMessage);
						System.out.println("Finished Receiving Successfully");
						myServerSocket.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}


			}

		}).start();



	}
	public  void  onSentButtonClicked(View v) {
		myMessage=((EditText)findViewById(R.id.editText)).getText().toString();
		((EditText)findViewById(R.id.editText)).setText("");
		System.out.println("new Message");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Sending new");
					Socket s=new Socket("192.168.1.4", 8888);
					//Socket s=new Socket("localhost", 8888);
					OutputStream os=s.getOutputStream();
					os.write(myMessage.getBytes());
					os.flush();
					os.close();
					s.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
}



