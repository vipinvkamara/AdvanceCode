package com.androidexample.ftpdemo;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import java.io.File;

import com.kpbird.ftpdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FtpUpload extends Activity implements OnClickListener {
    
	
	/*********  work only for Dedicated IP ***********/
	static final String FTP_HOST= "50.63.92.56";
	
	/*********  FTP USERNAME ***********/
	static final String FTP_USER = "XXXXXX";
	
	/*********  FTP PASSWORD ***********/
	static final String FTP_PASS  ="XXXXXXX";
	
	Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);
        
    }
    
    public void onClick(View v) {
		
    	/********** Pick file from sdcard *******/
		File f = new File("/sdcard/logo.png");
		
		// Upload sdcard file
		uploadFile(f);
		
	}
    
    public void uploadFile(File fileName){
    	
    	
		 FTPClient client = new FTPClient();
		 
		try {
			
			client.connect(FTP_HOST,21);
			client.login(FTP_USER, FTP_PASS);
			client.setType(FTPClient.TYPE_BINARY);
			client.changeDirectory("/upload/");
			
			client.upload(fileName, new MyTransferListener());
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				client.disconnect(true);	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
    }
    
    /*******  Used to file upload and show progress  **********/
    
    public class MyTransferListener implements FTPDataTransferListener {

    	public void started() {
    		
    		btn.setVisibility(View.GONE);
    		// Transfer started
    		Toast.makeText(getBaseContext(), " Upload Started ...", Toast.LENGTH_SHORT).show();
    		//System.out.println(" Upload Started ...");
    	}

    	public void transferred(int length) {
    		
    		// Yet other length bytes has been transferred since the last time this
    		// method was called
    		Toast.makeText(getBaseContext(), " transferred ..." + length, Toast.LENGTH_SHORT).show();
    		//System.out.println(" transferred ..." + length);
    	}

    	public void completed() {
    		
    		btn.setVisibility(View.VISIBLE);
    		// Transfer completed
    		
    		Toast.makeText(getBaseContext(), " completed ...", Toast.LENGTH_SHORT).show();
    		//System.out.println(" completed ..." );
    	}

    	public void aborted() {
    		
    		btn.setVisibility(View.VISIBLE);
    		// Transfer aborted
    		Toast.makeText(getBaseContext()," transfer aborted , please try again...", Toast.LENGTH_SHORT).show();
    		//System.out.println(" aborted ..." );
    	}

    	public void failed() {
    		
    		btn.setVisibility(View.VISIBLE);
    		// Transfer failed
    		System.out.println(" failed ..." );
    	}

    }
	

}