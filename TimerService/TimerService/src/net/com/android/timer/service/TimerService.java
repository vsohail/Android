package net.com.android.timer.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TimerService extends Service {
	
	private final static String TAG = TimerService.class.getSimpleName();
	
	private Timer timer;
	
	private NotificationManager notif;
	
	private TimerTask updateTask = new TimerTask(){
		public void run() {
			Log.i(TAG, "Timer Task Doing Work");
			
			showNotification();
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.i(TAG, "Service creating");
		
		notif = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		timer	= new Timer("TimerService");
		timer.schedule(updateTask, 1000L, 60 * 1000L);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.i(TAG, "Service destroyed");
		
		timer.cancel();
		timer = null;
	}
	
	public void showNotification(){

		CharSequence text 		  	= getText(R.string.service_started);
        Notification notification 	= new Notification(R.drawable.icon, text, System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Launcher.class), 0);

        notification.setLatestEventInfo(this, getText(R.string.service_label), text, contentIntent);
        
        notif.notify(R.string.service_started, notification);
	}
	
	public IBinder onBind(Intent intent) {
		return null;
	}
}
