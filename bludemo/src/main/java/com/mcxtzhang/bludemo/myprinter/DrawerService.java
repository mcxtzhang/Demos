package com.mcxtzhang.bludemo.myprinter;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * 
 * @author Administrator
 * 
 */
public class DrawerService extends Service {

	// Service和workThread通信用mHandler
	public static WorkThread workThread = null;
	private static Handler mHandler = null;
	private static List<Handler> targetsHandler = new ArrayList<Handler>(5);

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		mHandler = new MHandler(this);
		workThread = new WorkThread(mHandler);
		workThread.start();
		Log.v("DrawerService", "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("DrawerService", "onStartCommand");
		Message msg = Message.obtain();
		msg.what = Global.MSG_ALLTHREAD_READY;
		notifyHandlers(msg);
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		workThread.disconnectBt();
		workThread.disconnectNet();
		workThread.disconnectUsb();
		workThread.quit();
		workThread = null;
		Log.v("DrawerService", "onDestroy");
	}

	static class MHandler extends Handler {

		WeakReference<DrawerService> mService;

		MHandler(DrawerService service) {
			mService = new WeakReference<DrawerService>(service);
		}

		@Override
		public void handleMessage(Message msg) {
			notifyHandlers(msg);
		}
	}

	/**
	 * 
	 * @param handler
	 */
	public static void addHandler(Handler handler) {
		if (!targetsHandler.contains(handler)) {
			targetsHandler.add(handler);
		}
	}

	/**
	 * 
	 * @param handler
	 */
	public static void delHandler(Handler handler) {
		if (targetsHandler.contains(handler)) {
			targetsHandler.remove(handler);
		}
	}

	/**
	 * 
	 * @param msg
	 */
	public static void notifyHandlers(Message msg) {
		for (int i = 0; i < targetsHandler.size(); i++) {
			Message message = Message.obtain(msg);
			targetsHandler.get(i).sendMessage(message);
		}
	}

}
