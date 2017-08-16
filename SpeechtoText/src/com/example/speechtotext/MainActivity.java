package com.example.speechtotext;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView t1;
	TextToSpeech tts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		t1=(TextView)findViewById(R.id.textView1);
		
		  tts=new TextToSpeech(this, new OnInitListener() {
				
				@Override
				public void onInit(int status) {
					// TODO Auto-generated method stub
					if(status!=TextToSpeech.ERROR)
					{
						tts.setLanguage(Locale.CHINA);
					}
				}
			});
	}

	public void start(View v)
	{
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
		if(i.resolveActivity(getPackageManager())!=null)
		   startActivityForResult(i,10);
		else
		{
			Toast.makeText(getApplicationContext(), "ur device is not supported",Toast.LENGTH_LONG).show();
			
		}
	}
     public void onActivityResult(int req,int res,Intent d)
     {
    	 super.onActivityResult(req, res, d);
    	 switch(req)
    	 {
    	 case 10:
    		   if(res==RESULT_OK && d!=null)
    		   {
    			   ArrayList<String> a = d.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    			   
    			   
    			   t1.setText(a.get(0));
    		   }
    	 }
     }

	
	 public void onPause()
	 {
		 if(tts!=null)
		 {
			 tts.stop();
			 tts.shutdown();
			 
		 }
		 super.onPause();
	 }
}
