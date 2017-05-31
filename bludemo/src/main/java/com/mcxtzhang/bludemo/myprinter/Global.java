package com.mcxtzhang.bludemo.myprinter;

/**
 * 全局皆可访问的变量放在这里，例如服务和线程通讯的msg
 * 
 * @author Administrator
 * 
 */
public class Global {

	public static final String PREFERENCES_FILENAME = "com.lvrenyang.drawer.PREFERENCES_FILENAME";

	public static final String PREFERENCES_IPADDRESS = "com.lvrenyang.drawer.PREFERENCES_IPADDRESS";
	public static final String PREFERENCES_PORTNUMBER = "com.lvrenyang.drawer.PREFERENCES_PORTNUMBER";
	public static final String PREFERENCES_BTADDRESS = "com.lvrenyang.drawer.PREFERENCES_BTADDRESS";

	public static final int MSG_WORKTHREAD_HANDLER_CONNECTNET = 100000;
	public static final int MSG_WORKTHREAD_SEND_CONNECTNETRESULT = 100001;
	public static final int MSG_WORKTHREAD_HANDLER_OPENDRAWERNET = 100002;
	public static final int MSG_WORKTHREAD_SEND_OPENDRAWERNETRESULT = 100003;
	public static final int MSG_WORKTHREAD_HANDLER_CONNECTBT = 100004;
	public static final int MSG_WORKTHREAD_SEND_CONNECTBTRESULT = 100005;
	public static final int MSG_WORKTHREAD_HANDLER_OPENDRAWERBT = 100006;
	public static final int MSG_WORKTHREAD_SEND_OPENDRAWERBTRESULT = 100007;
	public static final int MSG_WORKTHREAD_HANDLER_STRINGINFOBT = 100008;
	public static final int MSG_WORKTHREAD_SEND_STRINGINFOBTRESULT = 100009;
	public static final int MSG_WORKTHREAD_HANDLER_STRINGINFONET = 100010;
	public static final int MSG_WORKTHREAD_SEND_STRINGINFONETRESULT = 100011;
	public static final int MSG_WORKTHREAD_HANDLER_SETKEYBT = 100012;
	public static final int MSG_WORKTHREAD_SEND_SETKEYBTRESULT = 100013;
	public static final int MSG_WORKTHREAD_HANDLER_SETKEYNET = 100014;
	public static final int MSG_WORKTHREAD_SEND_SETKEYNETRESULT = 100015;
	public static final int MSG_WORKTHREAD_HANDLER_SETBTPARABT = 100016;
	public static final int MSG_WORKTHREAD_SEND_SETBTPARABTRESULT = 100017;
	public static final int MSG_WORKTHREAD_HANDLER_SETBTPARANET = 100018;
	public static final int MSG_WORKTHREAD_SEND_SETBTPARANETRESULT = 100019;
	public static final int MSG_WORKTHREAD_HANDLER_SETIPPARABT = 100020;
	public static final int MSG_WORKTHREAD_SEND_SETIPPARABTRESULT = 100021;
	public static final int MSG_WORKTHREAD_HANDLER_SETIPPARANET = 100022;
	public static final int MSG_WORKTHREAD_SEND_SETIPPARANETRESULT = 100023;
	public static final int MSG_WORKTHREAD_HANDLER_SETWIFIPARABT = 100024;
	public static final int MSG_WORKTHREAD_SEND_SETWIFIPARABTRESULT = 100025;
	public static final int MSG_WORKTHREAD_HANDLER_SETWIFIPARANET = 100026;
	public static final int MSG_WORKTHREAD_SEND_SETWIFIPARANETRESULT = 100027;
	public static final int MSG_WORKTHREAD_HANDLER_CONNECTUSB = 100028;
	public static final int MSG_WORKTHREAD_SEND_CONNECTUSBRESULT = 100029;

	// Bundle data使用
	public static final String BYTESPARA1 = "bytespara1";
	public static final String BYTESPARA2 = "bytespara2";
	public static final String BYTESPARA3 = "bytespara3";
	public static final String BYTESPARA4 = "bytespara4";
	public static final String INTPARA1 = "intpara1";
	public static final String INTPARA2 = "intpara2";
	public static final String INTPARA3 = "intpara3";
	public static final String INTPARA4 = "intpara4";
	public static final String INTPARA5 = "intpara5";
	public static final String INTPARA6 = "intpara6";
	public static final String STRPARA1 = "strpara1";
	public static final String STRPARA2 = "strpara2";
	public static final String STRPARA3 = "strpara3";
	public static final String STRPARA4 = "strpara4";
	public static final String OBJECT1 = "object1";
	public static final String OBJECT2 = "object2";
	public static final String OBJECT3 = "object3";
	public static final String OBJECT4 = "object4";
	public static final String PARCE1 = "parce1";
	public static final String PARCE2 = "parce2";
	public static final String SERIAL1 = "serial1";
	public static final String SERIAL2 = "serial2";

	public static final int CMD_POS_WRITE = 100100;
	public static final int CMD_POS_WRITERESULT = 100101;
	public static final int CMD_POS_READ = 100102;
	public static final int CMD_POS_READRESULT = 100103;
	public static final int CMD_POS_SETKEY = 100104;
	public static final int CMD_POS_SETKEYRESULT = 100105;
	public static final int CMD_POS_CHECKKEY = 100106;
	public static final int CMD_POS_CHECKKEYRESULT = 100107;
	public static final int CMD_POS_PRINTPICTURE = 100108;
	public static final int CMD_POS_PRINTPICTURERESULT = 100109;
	public static final int CMD_POS_STEXTOUT = 100110;
	public static final int CMD_POS_STEXTOUTRESULT = 100111;
	public static final int CMD_POS_SALIGN = 100112;
	public static final int CMD_POS_SALIGNRESULT = 100113;
	public static final int CMD_POS_SETLINEHEIGHT = 100114;
	public static final int CMD_POS_SETLINEHEIGHTRESULT = 100115;
	public static final int CMD_POS_SETRIGHTSPACE = 100116;
	public static final int CMD_POS_SETRIGHTSPACERESULT = 100117;
	public static final int CMD_POS_SETCHARSETANDCODEPAGE = 100118;
	public static final int CMD_POS_SETCHARSETANDCODEPAGERESULT = 100119;
	public static final int CMD_POS_SETBARCODE = 100120;
	public static final int CMD_POS_SETBARCODERESULT = 100121;
	public static final int CMD_POS_SETQRCODE = 100122;
	public static final int CMD_POS_SETQRCODERESULT = 100123;
	public static final int CMD_EPSON_SETQRCODE = 100123;
	public static final int CMD_EPSON_SETQRCODERESULT = 100124;
	public static final int MSG_ALLTHREAD_READY = 100300;
	public static final int MSG_PAUSE_HEARTBEAT = 100301;
	public static final int MSG_RESUME_HEARTBEAT = 100302;
	public static final int MSG_ON_RECIVE = 100303;
	public static final int CMD_WRITE = 100304;
	public static final int CMD_WRITERESULT = 100305;
}
