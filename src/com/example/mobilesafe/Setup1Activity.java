package com.example.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setup1Activity extends BaseActivity implements OnClickListener {
	
	private Button btn_setup1_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		btn_setup1_next = (Button) findViewById(R.id.btn_setup1_next);
		
		btn_setup1_next.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_setup1_next:
			startActivity(new Intent(Setup1Activity.this,Setup2Activity.class));
			Setup1Activity.this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
			default:
				break;
		}
		
	}
	
}
