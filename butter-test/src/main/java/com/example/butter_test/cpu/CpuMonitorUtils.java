package com.example.butter_test.cpu;


import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * 对外暴露start(key),end(key)方法，当end方法调用时，输出key start-end 期间 对应当  CPU 使用率
 * Created by zhangxutong on 2018/5/21.
 */

public enum CpuMonitorUtils {
    INSTANCE;

    private static final String TAG = CpuMonitorUtils.class.getSimpleName();
    private int mPid = 0;

    private ArrayMap<String, CpuCacheModel> mCpuCaches = new ArrayMap<>();

    private static class CpuCacheModel {
        public CpuCacheModel(long sysCpuTime, long pidCpuTime) {
            this.sysCpuTime = sysCpuTime;
            this.pidCpuTime = pidCpuTime;
        }

        long sysCpuTime;
        long pidCpuTime;
    }

    public void startMonitor(String key) {
        long time1 = System.currentTimeMillis();
        long[] cpu = getCpu();
        long time2 = System.currentTimeMillis() - time1;
        Log.d(TAG, "startMonitor() called with: key = [" + time2 + "]");
        if (null == cpu || cpu[0] == -1 || cpu[1] == -1) {
            return;
        }
        mCpuCaches.put(key, new CpuCacheModel(cpu[0], cpu[1]));
    }

    public long endMonitor(String key) {
        CpuCacheModel cpuCacheModel = mCpuCaches.get(key);
        if (null == cpuCacheModel) {
            return -1;
        }
        long time1 = System.currentTimeMillis();
        long[] cpu = getCpu();
        long time2 = System.currentTimeMillis() - time1;
        Log.d(TAG, "endMonitor() called with: key = [" + time2 + "]");
        if (null == cpu || cpu[0] == -1 || cpu[1] == -1) {
            return -1;
        }
        long sysCpuTimeCost = cpu[0] - cpuCacheModel.sysCpuTime;
        if (sysCpuTimeCost > 0) {
            //appCpuRate = 100*( processCpuTime2 – processCpuTime1) / (totalCpuTime2 – totalCpuTime1) 
            return 100 * (cpu[1] - cpuCacheModel.pidCpuTime) / sysCpuTimeCost;
        } else {
            return -1;
        }
    }

    public long[] getCpu() {
        BufferedReader sysReader = null;
        BufferedReader pidReader = null;
        try {
            sysReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sysCpuInfo = null;
        try {
            sysCpuInfo = sysReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sysCpuInfo == null) {
            sysCpuInfo = "";
        }

        if (mPid == 0) {
            mPid = android.os.Process.myPid();
        }
        try {
            pidReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + mPid + "/stat")), 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String pidCpuInfo = null;
        try {
            pidCpuInfo = pidReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pidCpuInfo == null) {
            pidCpuInfo = "";
        }

        return new long[]{parseSysTotalCpuTime(sysCpuInfo), parseProcessCpuTime(pidCpuInfo)};
    }

    private long parseSysTotalCpuTime(String sysTotalCpuInto) {
        String[] cpuInfoArray = sysTotalCpuInto.split(" +");
        if (cpuInfoArray.length < 9) {
            return -1;
        }
        // 从系统启动开始累计到当前时刻，用户态的CPU时间，不包含 nice值为负进程
        long user = Long.parseLong(cpuInfoArray[1]);
        // 从系统启动开始累计到当前时刻，nice值为负的进程所占用的CPU时间
        long nice = Long.parseLong(cpuInfoArray[2]);
        // 从系统启动开始累计到当前时刻，核心时间
        long system = Long.parseLong(cpuInfoArray[3]);
        // 从系统启动开始累计到当前时刻，除硬盘IO等待时间以外其它等待时间
        long idle = Long.parseLong(cpuInfoArray[4]);
        // 从系统启动开始累计到当前时刻，硬盘IO等待时间
        long ioWait = Long.parseLong(cpuInfoArray[5]);
        //从系统启动开始累计到当前时刻，硬中断时间
        long irq = Long.parseLong(cpuInfoArray[6]);
        //从系统启动开始累计到当前时刻，软中断时间
        long softirq = Long.parseLong(cpuInfoArray[7]);
        // CPU总时间 = 以上所有加上irq（硬中断）和softirq（软中断）的时间
        long sysCpuTime = user + nice + system + idle + ioWait + irq + softirq;
        return sysCpuTime;
    }

    private long parseProcessCpuTime(String processCpuInfo) {
        String[] pidCpuInfos = processCpuInfo.split(" ");
        if (pidCpuInfos.length < 17) {
            return -1;
        }
        long pidCpuTime = Long.parseLong(pidCpuInfos[13]) + Long.parseLong(pidCpuInfos[14])
                + Long.parseLong(pidCpuInfos[15]) + Long.parseLong(pidCpuInfos[16]);
        return pidCpuTime;
    }

}
