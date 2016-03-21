package main.java.myapp;



import com.example.mysuperapplication.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class WinActivity extends Activity  {
	 public static MediaPlayer mp= null;
	
		 @Override
		 protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.win_layout);
		  
		  mp= MediaPlayer.create(this, R.raw.victory);
		  mp.start();
		  
		  Button restart = (Button)findViewById(R.id.button1);
		  restart.setOnClickListener(new OnClickListener() {
			  
			  @Override
				public void onClick(View v) {
				redirectToMain(v);
				}
			}); 
		 }
		 
		 public void redirectToMain(View view){
			  Intent intent = new Intent(this,MainActivity.class);
			  startActivity(intent);
			 }
				
	}