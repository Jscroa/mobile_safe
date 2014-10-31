package com.example.mobilesafe;

import com.example.mobilesafe.ui.SettingItemView;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Setup2Activity extends BaseActivity implements OnClickListener {
	private SettingItemView siv_setup2_sim;
	private Button btn_setup2_pre;
	private Button btn_setup2_next;
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		siv_setup2_sim =(SettingItemView) findViewById(R.id.siv_setup2_sim);
		btn_setup2_pre = (Button) findViewById(R.id.btn_setup2_pre);
		btn_setup2_next = (Button) findViewById(R.id.btn_setup2_next);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		siv_setup2_sim.setOnClickListener(this);
		btn_setup2_pre.setOnClickListener(this);
		btn_setup2_next.setOnClickListener(this);
		
		if(TextUtils.isEmpty(sp.getString("sim", null))){
			siv_setup2_sim.setChecked(false);
		}else {
			siv_setup2_sim.setChecked(true);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.siv_setup2_sim:
			if(siv_setup2_sim.isChecked()){
				siv_setup2_sim.setChecked(false);
				String sim = tm.getSimSerialNumber();
				Editor editor = sp.edit();
				editor.putString("sim", null);
				editor.commit();
			}else {
				siv_setup2_sim.setChecked(true);
				String sim = tm.getSimSerialNumber();
				Editor editor = sp.edit();
				editor.putString("sim", sim);
				editor.commit();
			}
			break;
		case R.id.btn_setup2_pre:
			startActivity(new Intent(Setup2Activity.this,Setup1Activity.class));
			Setup2Activity.this.finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		case R.id.btn_setup2_next:
			if(TextUtils.isEmpty(sp.getString("sim", null))){
				Toast.makeText(Setup2Activity.this, "SIM¿¨Ã»ÓÐ°ó¶¨", Toast.LENGTH_SHORT).show();
				break;
			}
			startActivity(new Intent(Setup2Activity.this,Setup3Activity.class));
			Setup2Activity.this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
			default:
				break;
		}
	}
	
}
