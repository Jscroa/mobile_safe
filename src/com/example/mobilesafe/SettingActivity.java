package com.example.mobilesafe;

import com.example.mobilesafe.ui.SettingItemView;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SettingActivity extends BaseActivity implements OnClickListener {
	
	private SettingItemView siv_setting_update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		siv_setting_update = (SettingItemView) findViewById(R.id.siv_setting_update);
		init();
		siv_setting_update.setOnClickListener(this);
	}

	/**
	 * 初始化设置项
	 */
	private void init() {
		if(sp.getBoolean("auto_update", false)){
			siv_setting_update.setChecked(true);
		}else {
			siv_setting_update.setChecked(false);
		}
		
	}

	@Override
	public void onClick(View v) {
		Editor editor = SettingActivity.this.sp.edit();
		switch(v.getId()){
		case R.id.siv_setting_update:
			if(siv_setting_update.isChecked()){
				siv_setting_update.setChecked(false);
				editor.putBoolean("auto_update", false);
			}else {
				siv_setting_update.setChecked(true);
				editor.putBoolean("auto_update", true);
			}
			break;
		default:
			Toast.makeText(SettingActivity.this, ".....", Toast.LENGTH_SHORT).show();
			break;
		}
		editor.commit();
		
	}
}
