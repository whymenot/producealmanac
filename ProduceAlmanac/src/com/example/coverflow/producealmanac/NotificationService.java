package com.example.coverflow.producealmanac;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// Getting Notification Service
		mManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(
						this.getApplicationContext().NOTIFICATION_SERVICE);
		/*
		 * When the user taps the notification we have to show the Home Screen
		 * of our App, this job can be done with the help of the following
		 * Intent.
		 */
		Intent intent1 = new Intent(this.getApplicationContext(), DetailActivity.class);

		// temporarily directing to apricots page
		intent1.putExtra("name", "apricots");

		Notification notification = new Notification(com.example.coverflow.R.drawable.ic_launcher,
				"Apricots have arrived at Berkeley Bowl.", System.currentTimeMillis());

		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				this.getApplicationContext(), 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notification.setLatestEventInfo(this.getApplicationContext(),
				"ProduceAlmanac", "Apricots have arrived at Berkeley Bowl.",
				pendingNotificationIntent);
		mManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}