package com.example.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

/**
 * 自定义的组合控件，有两个TextView，一个CheckBox，和一个View
 * @author 尧
 *
 */
public class SettingItemView extends LinearLayout {

	private TextView tv_title;
	private TextView tv_desc;
	private CheckBox cb_select;
	
	private String title;
	private String descOn;
	private String descOff;
	
	private static final String name_space = "http://schemas.android.com/apk/res/com.example.mobilesafe";
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * 在布局文件中实例化调用此方法
	 * @param context
	 * @param attrs
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		title = attrs.getAttributeValue(name_space, "title");
		descOn = attrs.getAttributeValue(name_space, "descOn");
		descOff = attrs.getAttributeValue(name_space, "descOff");
		tv_title.setText(title);
		setChecked(cb_select.isChecked());
	}

	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View v = View.inflate(context, R.layout.setting_item, this);
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_desc = (TextView) v.findViewById(R.id.tv_desc);
		cb_select = (CheckBox) v.findViewById(R.id.cb_select);
	}
	
	public void setChecked(boolean checked){
		cb_select.setChecked(checked);
		if(checked){
			tv_desc.setText(descOn);
		}else {
			tv_desc.setText(descOff);
		}
	}
	
	public boolean isChecked(){
		return cb_select.isChecked();
	}

}
