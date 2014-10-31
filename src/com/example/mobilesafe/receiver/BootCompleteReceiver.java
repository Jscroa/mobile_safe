package com.example.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting) {
			tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			// �����SIM����Ϣ
			String savedSim = sp.getString("sim", "");
			String realSim = tm.getSimSerialNumber();

			if (savedSim.equals(realSim)) {
				// SIM��û�б��
			} else {
				// SIM���Ѿ���� ���͸���ȫ����
				Log.v("BotCompleteReceiver", "SIM���Ѿ����");
				Toast.makeText(context, "SIM���Ѿ����", Toast.LENGTH_SHORT).show();
				String safenumber = sp.getString("safenumber", "");
				SmsManager.getDefault().sendTextMessage(safenumber, null, "SIM changed", null, null);
			}
		}

	}

}
