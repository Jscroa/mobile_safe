package com.example.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.utils.AppUtil;
import com.example.mobilesafe.utils.StreamTools;


public class SplashActivity extends BaseActivity {
	
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	
	private TextView tv_splash_version;
	private TextView tv_splash_download_progress;
	
	private String version = "";
	private String desc = "";
	private String apkurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_download_progress = (TextView) findViewById(R.id.tv_splash_download_progress);
        
        tv_splash_version.setText(AppUtil.getVersionName(getApplicationContext(), getApplicationContext().getPackageName()));
        
        //屏蔽 或 不屏蔽 自动升级
        if(sp.getBoolean("auto_update", false)){
        	checkUpdate();
        } else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}
        
    }

    /**
     * 检查更新
     */
	private void checkUpdate() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				Message msg = Message.obtain();
				try {
					URL url = new URL(getString(R.string.update_url));
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(1000);
					int code = connection.getResponseCode();
					if(code == 200){
						InputStream is = connection.getInputStream();
						String result = StreamTools.readFromStream(is);
						JSONObject json = new JSONObject(result);
						version = json.getString("version");
						desc = json.getString("desc");
						apkurl = json.getString("apkurl");
						if(version.equals(AppUtil.getVersionName(getApplicationContext(), getApplicationContext().getPackageName()))){
							msg.what = ENTER_HOME;
						} else {
							msg.what = SHOW_UPDATE_DIALOG;
						}
					} else {
						msg.what = ENTER_HOME;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					msg.what = URL_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = JSON_ERROR;
				} finally {
					long endTime = System.currentTimeMillis();
					long dTime = endTime - startTime;
					if(dTime < 2000){
						try {
							Thread.sleep(2000 - dTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendMessage(msg);
				}
			}
		}).start();
		
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case ENTER_HOME:
				enterHome();
				break;
			case SHOW_UPDATE_DIALOG:
				showUpdate();
				break;
			case URL_ERROR:
				enterHome();
				break;
			case NETWORK_ERROR:
				enterHome();
				break;
			case JSON_ERROR:
				enterHome();
				break;
			default:
				break;
			}
		}
		
	};

	/**
	 * 进入主页
	 */
	protected void enterHome() {
		startActivity(new Intent(SplashActivity.this,HomeActivity.class));
		finish();
	}

	/**
	 * 弹出升级框
	 */
	protected void showUpdate() {
		Builder builder = new Builder(this);
		builder.setTitle("升级提示");
		builder.setMessage(desc);
		builder.setNegativeButton("立刻升级", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(apkurl, 
							Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/mobile-safe.apk",
							new AjaxCallBack<File>() {
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									super.onFailure(t, errorNo, strMsg);
									Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
									enterHome();
								}
								@Override
								public void onLoading(long count, long current) {
									super.onLoading(count, current);
									int percent = (int) (current * 100 / count);
									tv_splash_download_progress.setText("下载进度: "+percent+"%");
								}
								@Override
								public void onSuccess(File t) {
									super.onSuccess(t);
									tv_splash_download_progress.setText("下载成功");
									installApk(t);
								}
								private void installApk(File t) {
									Intent intent = new Intent();
									intent
										.setAction("android.intent.action.VIEW")
										.addCategory("android.intent.category.DEFAULT")
										.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
									startActivity(intent);
								}
						
							});
				} else {
					Toast.makeText(SplashActivity.this, "没有SD卡", Toast.LENGTH_SHORT).show();
				}
			}
		});
		builder.setPositiveButton("下次再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
			}
		});
		builder.show();
	}

}
