package com.example.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.WindowManager;

public class BaseActivity extends Activity {
	
	protected SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT){
			// ͸��״̬��
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		sp = getSharedPreferences("config", MODE_PRIVATE);
	}
	
}
