package com.mcxtzhang.bludemo.myprinter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.lvrenyang.pos.IO;
import com.lvrenyang.pos.Pos;
import com.lvrenyang.pos.Protocol;
import com.lvrenyang.rwbt.BTHeartBeatThread;
import com.lvrenyang.rwbt.BTRWThread;
import com.lvrenyang.rwusb.PL2303Driver.TTYTermios;
import com.lvrenyang.rwusb.USBDriver.USBPort;
import com.lvrenyang.rwusb.USBHeartBeatThread;
import com.lvrenyang.rwusb.USBRWThread;
import com.lvrenyang.rwwifi.NETHeartBeatThread;
import com.lvrenyang.rwwifi.NETRWThread;

public class WorkThread extends Thread {

	private static final String TAG = "WorkThread";
	public static Handler workHandler = null;
	private static Looper mLooper = null;
	public static Handler targetHandler = null;
	public static NETRWThread netRW;
	public static NETHeartBeatThread netHeartBeat;
	public static BTRWThread btRW;
	public static BTHeartBeatThread btHeartBeat;
	public static USBRWThread usbRW;
	public static USBHeartBeatThread usbHeartBeat;
	private static boolean threadInitOK = false;
	private static boolean isConnecting = false;

	public WorkThread(Handler handler) {
		threadInitOK = false;
		targetHandler = handler;
		netRW = NETRWThread.InitInstant();
		netHeartBeat = NETHeartBeatThread.InitInstant(targetHandler);
		btRW = BTRWThread.InitInstant();
		btHeartBeat = BTHeartBeatThread.InitInstant(targetHandler);
		usbRW = USBRWThread.InitInstant();
		usbHeartBeat = USBHeartBeatThread.InitInstant(targetHandler);
	}

	@Override
	public void start() {
		super.start();
		while (!threadInitOK)
			;

		netRW.start();
		netHeartBeat.start();
		btRW.start();
		btHeartBeat.start();
		usbRW.start();
		usbHeartBeat.start();
	}

	@Override
	public void run() {
		Looper.prepare();
		mLooper = Looper.myLooper();
		if (null == mLooper)
			Log.v(TAG, "mLooper is null pointer");
		else
			Log.v(TAG, "mLooper is valid");
		workHandler = new WorkHandler();
		threadInitOK = true;
		Looper.loop();
	}

	private static class WorkHandler extends Handler {

		boolean needPauseHeartBeat(int msgCode) {
			boolean isNeeded = true;
			switch (msgCode) {
			case Global.CMD_POS_WRITE:
			case Global.CMD_POS_READ:
			case Global.CMD_POS_SETKEY:
			case Global.CMD_POS_CHECKKEY:
			case Global.MSG_PAUSE_HEARTBEAT:
				isNeeded = true;
				break;
			default:
				isNeeded = false;
				break;
			}
			return isNeeded;
		}

		boolean needResumeHeartBeat(int msgCode) {
			boolean isNeeded = true;
			switch (msgCode) {
			case Global.CMD_POS_WRITE:
			case Global.CMD_POS_READ:
			case Global.CMD_POS_SETKEY:
			case Global.CMD_POS_CHECKKEY:
			case Global.MSG_RESUME_HEARTBEAT:
				isNeeded = true;
				break;
			default:
				isNeeded = false;
				break;
			}
			return isNeeded;
		}

		@Override
		public void handleMessage(Message msg) {

			// 有些消息，需要暂停心跳线程
			if (needPauseHeartBeat(msg.what)) {
				BTHeartBeatThread.PauseHeartBeat();
				NETHeartBeatThread.PauseHeartBeat();
				USBHeartBeatThread.PauseHeartBeat();
			}

			switch (msg.what) {
			case Global.MSG_WORKTHREAD_HANDLER_CONNECTBT: {
				isConnecting = true;
				IO.SetCurPort(IO.PORT_BT);
				String BTAddress = (String) msg.obj;
				boolean result = BTRWThread.Open(BTAddress);
				// boolean result = BTRWThread.OpenOfficial(BTAddress);
				Message smsg = targetHandler
						.obtainMessage(Global.MSG_WORKTHREAD_SEND_CONNECTBTRESULT);
				if (result) {
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				if (result) {
					// 开启心跳线程
					BTHeartBeatThread.BeginHeartBeat();
				}
				isConnecting = false;
				break;
			}

			case Global.MSG_WORKTHREAD_HANDLER_CONNECTNET: {
				isConnecting = true;
				IO.SetCurPort(IO.PORT_NET);
				int PortNumber = msg.arg1;
				String IPAddress = (String) msg.obj;
				boolean result = NETRWThread.Open(IPAddress, PortNumber);
				Message smsg = targetHandler
						.obtainMessage(Global.MSG_WORKTHREAD_SEND_CONNECTNETRESULT);
				if (result) {
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				// 开启心跳线程
				if (result) {
					NETHeartBeatThread.BeginHeartBeat();
				}
				isConnecting = false;
				break;
			}

			case Global.MSG_WORKTHREAD_HANDLER_CONNECTUSB: {
				isConnecting = true;
				IO.SetCurPort(IO.PORT_USB);
				Bundle data = msg.getData();
				USBPort port = (USBPort) data.getParcelable(Global.PARCE1);
				TTYTermios serial = (TTYTermios) data
						.getParcelable(Global.PARCE2);

				boolean result = USBRWThread.Open(port, serial);
				Message smsg = targetHandler
						.obtainMessage(Global.MSG_WORKTHREAD_SEND_CONNECTUSBRESULT);
				if (result) {
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				// 开启心跳线程
				if (result) {
					USBHeartBeatThread.BeginHeartBeat();
				}
				isConnecting = false;
				break;
			}

			case Global.CMD_WRITE: {
				Bundle data = msg.getData();
				byte[] buffer = data.getByteArray(Global.BYTESPARA1);
				int offset = data.getInt(Global.INTPARA1);
				int count = data.getInt(Global.INTPARA2);

				Message smsg = targetHandler
						.obtainMessage(Global.CMD_WRITERESULT);
				if (IO.Write(buffer, offset, count) == count) {
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_WRITE: {
				Bundle data = msg.getData();
				byte[] buffer = data.getByteArray(Global.BYTESPARA1);
				int offset = data.getInt(Global.INTPARA1);
				int count = data.getInt(Global.INTPARA2);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_WRITERESULT);

				if (result) {
					IO.Write(buffer, offset, count);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETKEY: {
				Bundle data = msg.getData();
				byte[] key = data.getByteArray(Global.BYTESPARA1);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETKEYRESULT);

				if (result) {
					Pos.POS_SetKey(key);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_CHECKKEY: {
				Bundle data = msg.getData();
				byte[] key = data.getByteArray(Global.BYTESPARA1);
				byte[] random = data.getByteArray(Global.BYTESPARA2);
				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_CHECKKEYRESULT);
				if (result) {
					if (Pos.POS_CheckKey(key, random))
						smsg.arg1 = 1;
					else
						smsg.arg1 = 0;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_PRINTPICTURE: {
				Bundle data = msg.getData();
				Bitmap mBitmap = (Bitmap) data.get(Global.OBJECT1);
				int nWidth = data.getInt(Global.INTPARA1);
				int nMode = data.getInt(Global.INTPARA2);
				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_PRINTPICTURERESULT);
				if (result) {
					Pos.POS_PrintPicture(mBitmap, nWidth, nMode);
					if (Protocol.Test())
						smsg.arg1 = 1;
					else
						smsg.arg1 = 0;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SALIGN: {
				Bundle data = msg.getData();
				int align = data.getInt(Global.INTPARA1);
				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SALIGNRESULT);
				if (result) {
					Pos.POS_S_Align(align);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETLINEHEIGHT: {
				Bundle data = msg.getData();
				int nHeight = data.getInt(Global.INTPARA1);
				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETLINEHEIGHTRESULT);
				if (result) {
					Pos.POS_SetLineHeight(nHeight);
					;
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETRIGHTSPACE: {
				Bundle data = msg.getData();
				int nDistance = data.getInt(Global.INTPARA1);
				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETRIGHTSPACERESULT);
				if (result) {
					Pos.POS_SetRightSpacing(nDistance);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_STEXTOUT: {
				Bundle data = msg.getData();
				String pszString = data.getString(Global.STRPARA1);
				String encoding = data.getString(Global.STRPARA2);
				int nOrgx = data.getInt(Global.INTPARA1);
				int nWidthTimes = data.getInt(Global.INTPARA2);
				int nHeightTimes = data.getInt(Global.INTPARA3);
				int nFontType = data.getInt(Global.INTPARA4);
				int nFontStyle = data.getInt(Global.INTPARA5);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_STEXTOUTRESULT);
				if (result) {
					Pos.POS_S_TextOut(pszString, encoding, nOrgx, nWidthTimes,
							nHeightTimes, nFontType, nFontStyle);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETCHARSETANDCODEPAGE: {
				Bundle data = msg.getData();
				int nCharSet = data.getInt(Global.INTPARA1);
				int nCodePage = data.getInt(Global.INTPARA2);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETCHARSETANDCODEPAGERESULT);
				if (result) {
					Pos.POS_SetCharSetAndCodePage(nCharSet, nCodePage);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETBARCODE: {
				Bundle data = msg.getData();
				String strBarcode = data.getString(Global.STRPARA1);
				int nOrgx = data.getInt(Global.INTPARA1);
				int nType = data.getInt(Global.INTPARA2);
				int nWidthX = data.getInt(Global.INTPARA3);
				int nHeight = data.getInt(Global.INTPARA4);
				int nHriFontType = data.getInt(Global.INTPARA5);
				int nHriFontPosition = data.getInt(Global.INTPARA6);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETBARCODERESULT);
				if (result) {
					Pos.POS_S_SetBarcode(strBarcode, nOrgx, nType, nWidthX,
							nHeight, nHriFontType, nHriFontPosition);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_POS_SETQRCODE: {
				Bundle data = msg.getData();
				String strQrcode = data.getString(Global.STRPARA1);
				int nWidthX = data.getInt(Global.INTPARA1);
				int necl = data.getInt(Global.INTPARA2);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_POS_SETQRCODERESULT);
				if (result) {
					Pos.POS_S_SetQRcode(strQrcode, nWidthX, necl);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}

			case Global.CMD_EPSON_SETQRCODE: {
				Bundle data = msg.getData();
				String strQrcode = data.getString(Global.STRPARA1);
				int nWidthX = data.getInt(Global.INTPARA1);
				int necl = data.getInt(Global.INTPARA2);

				boolean result = Protocol.Test();
				Message smsg = targetHandler
						.obtainMessage(Global.CMD_EPSON_SETQRCODERESULT);
				if (result) {
					Pos.POS_EPSON_SetQRCode(strQrcode, nWidthX, necl);
					smsg.arg1 = 1;
				} else {
					smsg.arg1 = 0;
				}
				targetHandler.sendMessage(smsg);

				break;
			}
			}

			// 如果暂停了心跳线程，在这里需要恢复
			if (needResumeHeartBeat(msg.what)) {
				BTHeartBeatThread.ResumeHeartBeat();
				NETHeartBeatThread.ResumeHeartBeat();
				USBHeartBeatThread.ResumeHeartBeat();
			}
		}
	}

	public void quit() {
		try {
			BTHeartBeatThread.Quit();
			btHeartBeat = null;
			BTRWThread.Quit();
			btRW = null;
			NETHeartBeatThread.Quit();
			netHeartBeat = null;
			NETRWThread.Quit();
			netRW = null;
			USBHeartBeatThread.Quit();
			usbHeartBeat = null;
			USBRWThread.Quit();
			usbRW = null;

			if (null != mLooper) {
				mLooper.quit();
				mLooper = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnectBt() {
		try {
			BTRWThread.Close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnectNet() {
		try {
			NETRWThread.Close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnectUsb() {
		try {
			USBRWThread.Close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectBt(String BTAddress) {
		if ((null != workHandler) && (null != mLooper)) {
			Message msg = workHandler
					.obtainMessage(Global.MSG_WORKTHREAD_HANDLER_CONNECTBT);
			msg.obj = BTAddress;
			workHandler.sendMessage(msg);
		} else {
			if (null == workHandler)
				Log.v(TAG, "workHandler is null pointer");

			if (null == mLooper)
				Log.v(TAG, "mLooper is null pointer");
		}
	}

	public void connectNet(String IPAddress, int PortNumber) {
		if ((null != workHandler) && (null != mLooper)) {
			Message msg = workHandler
					.obtainMessage(Global.MSG_WORKTHREAD_HANDLER_CONNECTNET);
			msg.arg1 = PortNumber;
			msg.obj = IPAddress;
			workHandler.sendMessage(msg);
		} else {
			if (null == workHandler)
				Log.v("WorkThread connectNet", "workHandler is null pointer");

			if (null == mLooper)
				Log.v("WorkThread connectNet", "mLooper is null pointer");
		}
	}

	public void connectUsb(USBPort port, TTYTermios serial) {
		if ((null != workHandler) && (null != mLooper)) {
			Message msg = workHandler
					.obtainMessage(Global.MSG_WORKTHREAD_HANDLER_CONNECTUSB);
			Bundle data = new Bundle();
			data.putParcelable(Global.PARCE1, port);
			data.putParcelable(Global.PARCE2, serial);
			msg.setData(data);
			workHandler.sendMessage(msg);
		} else {
			if (null == workHandler)
				Log.v("WorkThread connectUsb", "workHandler is null pointer");

			if (null == mLooper)
				Log.v("WorkThread connectUsb", "mLooper is null pointer");
		}
	}

	public boolean isConnecting() {
		return isConnecting;
	}

	public boolean isConnected() {
		if (BTRWThread.IsOpened() || NETRWThread.IsOpened()
				|| USBRWThread.IsOpened())
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param cmd
	 */
	public void handleCmd(int cmd, Bundle data) {
		if ((null != workHandler) && (null != mLooper)) {
			Message msg = workHandler.obtainMessage(cmd);
			msg.setData(data);
			workHandler.sendMessage(msg);
		} else {
			if (null == workHandler)
				Log.v(TAG, "workHandler is null pointer");

			if (null == mLooper)
				Log.v(TAG, "mLooper is null pointer");
		}
	}

}
