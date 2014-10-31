package com.example.mobilesafe.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	/**
	 * get App's version name
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getVersionName(Context context, String packageName){
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(packageName, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
