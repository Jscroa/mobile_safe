package com.example.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setup4Activity extends BaseActivity implements OnClickListener,OnCheckedChangeListener {

	private CheckBox cb_setup4_protect;
	private Button btn_setup4_pre;
	private Button btn_setup4_next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		cb_setup4_protect = (CheckBox) findViewById(R.id.cb_setup4_protect);
		btn_setup4_pre = (Button) findViewById(R.id.btn_setup4_pre);
		btn_setup4_next = (Button) findViewById(R.id.btn_setup4_next);
		
		boolean protecting = sp.getBoolean("protecting", false);
		cb_setup4_protect.setChecked(protecting);
		if(protecting){
			cb_setup4_protect.setText("你已经开启防盗保护");
		}else{
			cb_setup4_protect.setText("你没有开启防盗保护");
		}
		
		cb_setup4_protect.setOnCheckedChangeListener(this);
		btn_setup4_pre.setOnClickListener(this);
		btn_setup4_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_setup4_pre:
			startActivity(new Intent(Setup4Activity.this,Setup3Activity.class));
			Setup4Activity.this.finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		case R.id.btn_setup4_next:
			Editor editor = sp.edit();
			editor.putBoolean("configed", true);
			editor.commit();
			startActivity(new Intent(Setup4Activity.this,LostFindActivity.class));
			Setup4Activity.this.finish();
			break;
			default:
				break;
		}
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch(buttonView.getId()){
		case R.id.cb_setup4_protect:
			if(isChecked){
				cb_setup4_protect.setText("你已经开启防盗保护");
			}else{
				cb_setup4_protect.setText("你没有开启防盗保护");
			}
			Editor editor = sp.edit();
			editor.putBoolean("protecting", isChecked);
			editor.commit();
			break;
			default:
				break;
		}
	}
	
}
