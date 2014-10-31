package com.example.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setup3Activity extends BaseActivity implements OnClickListener {
	
	private EditText et_setup3_phone;
	private Button btn_setup3_selectcontact;
	private Button btn_setup3_pre;
	private Button btn_setup3_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		et_setup3_phone = (EditText) findViewById(R.id.et_setup3_phone);
		btn_setup3_selectcontact = (Button) findViewById(R.id.btn_setup3_selectcontact);
		btn_setup3_pre = (Button) findViewById(R.id.btn_setup3_pre);
		btn_setup3_next = (Button) findViewById(R.id.btn_setup3_next);
		
		et_setup3_phone.setText(sp.getString("safenumber", ""));
		
		btn_setup3_selectcontact.setOnClickListener(this);
		btn_setup3_pre.setOnClickListener(this);
		btn_setup3_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_setup3_selectcontact:
			startActivityForResult(new Intent(Setup3Activity.this,SelectContactActivity.class),0);
			break;
		case R.id.btn_setup3_pre:
			startActivity(new Intent(Setup3Activity.this,Setup2Activity.class));
			Setup3Activity.this.finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		case R.id.btn_setup3_next:
			
			String phone = et_setup3_phone.getText().toString().trim();
			if(TextUtils.isEmpty(phone)){
				Toast.makeText(Setup3Activity.this, "安全号码还没设置", Toast.LENGTH_SHORT).show();
				return;
			}
			Editor editor = sp.edit();
			editor.putString("safenumber", phone);
			editor.commit();
			
			startActivity(new Intent(Setup3Activity.this,Setup4Activity.class));
			Setup3Activity.this.finish();
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data==null){
			return;
		}
		String phone = data.getStringExtra("phone").replace("-", "").replace(" ", "");
		et_setup3_phone.setText(phone);
	}
	
}
