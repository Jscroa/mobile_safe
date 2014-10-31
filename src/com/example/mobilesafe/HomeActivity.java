package com.example.mobilesafe;

import sss.yyao.MD5Utils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity implements OnItemClickListener {

	private GridView gv_home_list;
	private MyAdapter adapter;

	private static String[] names = { 
		"�ֻ�����", "ͨѶ��ʿ", "�������", 
		"���̹���", "����ͳ��", "�ֻ�ɱ��", 
		"��������", "�߼�����", "��������" 
		};

	private static int[] ids = { 
		R.drawable.shou_ji_fang_dao, R.drawable.tong_xun_wei_shi, R.drawable.ruan_jian_guan_li,
		R.drawable.jin_cheng_guan_li, R.drawable.liu_liang_tong_ji, R.drawable.shou_ji_sha_du, 
		R.drawable.huan_cun_qing_li, R.drawable.gao_ji_gong_ju, R.drawable.she_zhi_zhong_xin
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		gv_home_list = (GridView) findViewById(R.id.gv_home_list);
		adapter = new MyAdapter();
		gv_home_list.setAdapter(adapter);

		gv_home_list.setOnItemClickListener(this);

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.home_item,
					null);
			ImageView img = (ImageView) view.findViewById(R.id.img_item_home);
			TextView tv = (TextView) view.findViewById(R.id.tv_item_home);
			img.setImageResource(ids[position]);
			tv.setText(names[position]);
			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0: // ��������
			showLostFindDialog();
			break;
		case 1: // ͨѶ��ʿ
			break;
		case 2: // �������
			break;
		case 3: // ���̹���
			break;
		case 4: // ����ͳ��
			break;
		case 5: // �ֻ�ɱ��
			break;
		case 6: // ��������
			break;
		case 7: // �߼�����
			break;
		case 8: // ��������
			startActivity(new Intent(HomeActivity.this, SettingActivity.class));
			break;
		default:
			break;
		}

	}

	private void showLostFindDialog() {
		if (isSetupPwd()) {
			// �Ѿ����ù������ˣ�������������Ի���
			showEnterDialog();
		} else {
			// û�����ù����룬����������������Ի���
			showSetupPwdDialog();
		}
	}

	private EditText et_dialog_pwd1;
	private EditText et_dialog_pwd2;
	private Button btn_dialog_cancle;
	private Button btn_dialog_ok;
	private AlertDialog dialog;
	
	/**
	 * ������������Ի���
	 */
	private void showSetupPwdDialog() {
		Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		et_dialog_pwd1 = (EditText) view.findViewById(R.id.et_dialog_pwd1);
		et_dialog_pwd2 = (EditText) view.findViewById(R.id.et_dialog_pwd2);
		btn_dialog_cancle = (Button) view.findViewById(R.id.btn_dialog_cancle);
		btn_dialog_ok = (Button) view.findViewById(R.id.btn_dialog_ok);
		btn_dialog_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_dialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd1 = et_dialog_pwd1.getText().toString().trim();
				String pwd2 = et_dialog_pwd2.getText().toString().trim();
				if(TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)){
					Toast.makeText(HomeActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				if(pwd1.equals(pwd2)){
					// �������룬�����Ի��򣬽����ֻ�����ҳ��
					Editor editor = sp.edit();
					editor.putString("password",  MD5Utils.encrypt(pwd1, 30));
					editor.commit();
					dialog.dismiss();
					startActivity(new Intent(HomeActivity.this,Setup1Activity.class));
				}else {
					Toast.makeText(HomeActivity.this, "���벻һ��", Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}

	/**
	 * ������������Ի���
	 */
	private void showEnterDialog() {
		Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_input_password, null);
		et_dialog_pwd1 = (EditText) view.findViewById(R.id.et_dialog_pwd);
		btn_dialog_cancle = (Button) view.findViewById(R.id.btn_dialog_cancle);
		btn_dialog_ok = (Button) view.findViewById(R.id.btn_dialog_ok);
		btn_dialog_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_dialog_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String password = et_dialog_pwd1.getText().toString().trim();
				if(TextUtils.isEmpty(password)){
					Toast.makeText(HomeActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				if(MD5Utils.encrypt(password, 30).equals(sp.getString("password", null))){
					//�����Ի��� �����ֻ�����ҳ
					dialog.dismiss();
					startActivity(new Intent(HomeActivity.this,LostFindActivity.class));
				}else {
					Toast.makeText(HomeActivity.this, "�������", Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}

	/**
	 * �ж��Ƿ����ù�����
	 * @return
	 */
	private boolean isSetupPwd() {
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
	}

}
