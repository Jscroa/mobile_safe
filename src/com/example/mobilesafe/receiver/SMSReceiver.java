package com.example.mobilesafe.receiver;

import com.example.mobilesafe.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		// 写接受短信的代码
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
			String sender = sms.getOriginatingAddress();
			String body = sms.getMessageBody();
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			String safenumber = sp.getString("safenumber", "");
			if (sender.endsWith(safenumber)) {
				if ("#*location*#".equals(body)) {
					Log.i(TAG, "#*location*#");
					abortBroadcast();
				} else if ("#*alarm*#".equals(body)) {
					Log.i(TAG, "#*alarm*#");
					MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
					player.setLooping(true);
					player.setVolume(1.0f, 1.0f);
					player.start();
					abortBroadcast();
				} else if ("#*wipedata*#".equals(body)) {
					Log.i(TAG, "#*wipedata*#");
					abortBroadcast();
				} else if ("#*lockscreen*#".equals(body)) {
					Log.i(TAG, "#*lockscreen*#");
					abortBroadcast();
				}
			}
		}

	}

}
