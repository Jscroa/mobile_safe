package com.example.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends BaseActivity {
	
	private TextView tv_lostfind_safenumber;
	private ImageView img_lostfind_lock;
	private TextView tv_lostfind_reenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean configed = sp.getBoolean("configed", false);
		if(configed){ // 已经做过手机设置向导
			setContentView(R.layout.activity_lostfind); // 进入手机防盗页面
			tv_lostfind_safenumber = (TextView) findViewById(R.id.tv_lostfind_safenumber);
			img_lostfind_lock = (ImageView) findViewById(R.id.img_lostfind_lock);
			tv_lostfind_reenter = (TextView) findViewById(R.id.tv_lostfind_reenter);
			
			String safenumber = sp.getString("safenumber", "");
			tv_lostfind_safenumber.setText(safenumber);
			
			boolean protecting = sp.getBoolean("protecting", false);
			if(protecting){
				img_lostfind_lock.setImageResource(R.drawable.locked);
			}else{
				img_lostfind_lock.setImageResource(R.drawable.unlock);
			}
			
			tv_lostfind_reenter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					reEnterSetup();
				}
			});
		}else{
			startActivity(new Intent(LostFindActivity.this,Setup1Activity.class));
			LostFindActivity.this.finish();
		}
		
	}

	protected void reEnterSetup() {
		startActivity(new Intent(LostFindActivity.this,Setup1Activity.class));
		LostFindActivity.this.finish();
	}
	
}
