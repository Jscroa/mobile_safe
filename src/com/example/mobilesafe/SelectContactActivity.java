package com.example.mobilesafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectContactActivity extends BaseActivity {

	private ListView lv_selectcontact;
	private List<Map<String,Object>> conracts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectcontact);
		lv_selectcontact = (ListView) findViewById(R.id.lv_selectcontact);
		conracts=getContactInfo();
		lv_selectcontact.setAdapter(new SimpleAdapter(this, conracts, R.layout.contact_item, new String[]{"name","phone"}, new int[]{R.id.tv_name_contact_item,R.id.tv_phone_contact_item}));
		
		lv_selectcontact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String phone = (String) conracts.get(position).get("phone");
				Intent data = new Intent();
				data.putExtra("phone", phone);
				setResult(0,data);
				SelectContactActivity.this.finish();
			}
		});
		
	}
	
	/**
	 * 获取联系人列表
	 * @return
	 */
	private List<Map<String,Object>> getContactInfo(){
		
		List<Map<String,Object>> contacts = new ArrayList<Map<String,Object>>();
		
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
		Log.v("asdsdds", ""+cursor.getCount());
		while(cursor.moveToNext()){
			String contact_id = cursor.getString(0);
			if(contact_id != null){
				Cursor dataCursor = resolver.query(uriData, new String[]{"data1","mimetype"}, "contact_id=?", new String[]{contact_id}, null);
				// 每一个Map保存一个联系人
				Map<String,Object> contact = new HashMap<String,Object>();
				while(dataCursor.moveToNext()){
					String data1 = dataCursor.getString(0);
					String mimetype = dataCursor.getString(1);
					if("vnd.android.cursor.item/name".equals(mimetype)){
						// data1为联系人姓名
						contact.put("name", data1);
					}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						// data1为联系人电话
						contact.put("phone", data1);
					}
				}
				contacts.add(contact);
				dataCursor.close();
				
			}
		}
		Log.v("asdad", ""+contacts.size());
		cursor.close();
		return contacts;
	}
	
}
