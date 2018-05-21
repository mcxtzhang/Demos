package com.example.butter_test.cpu;


import android.support.v4.util.ArrayMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * 对外暴露start(key),end(key)方法，当end方法调用时，输出key start-end 期间 对应当  CPU 使用率
 * Created by zhangxutong on 2018/5/21.
 */

public enum CpuMonitorUtils {
    INSTANCE;

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
        long[] cpu = getCpu();
        mCpuCaches.put(key, new CpuCacheModel(cpu[0], cpu[1]));
    }

    public long endMonitor(String key) {
        CpuCacheModel cpuCacheModel = mCpuCaches.get(key);
        if (null == cpuCacheModel) {
            return -1;
        }
        long[] cpu = getCpu();
        long sysCpuTimeCost = cpu[0] - cpuCacheModel.sysCpuTime;
        if (sysCpuTimeCost > 0) {
            return 100 * (cpu[1] - cpuCacheModel.pidCpuTime) / sysCpuTimeCost;
        } else {
            return -1;
        }
    }

    public long[] getCpu() {
        //通过读取"/proc/stat",  "/proc/pid/stat"分别获取 整个系统CPU时间 totalCpuTime 以及 App进程的CPU时间 processCpuTime。
        BufferedReader cpuReader = null;
        BufferedReader pidReader = null;
        try {
            cpuReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cpuRate = null;
        try {
            cpuRate = cpuReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cpuRate == null) {
            cpuRate = "";
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
        String pidCpuRate = null;
        try {
            pidCpuRate = pidReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pidCpuRate == null) {
            pidCpuRate = "";
        }

        return parseCpuRate(cpuRate, pidCpuRate);
    }

    //appCpuRate = 100*( processCpuTime2 – processCpuTime1) / (totalCpuTime2 – totalCpuTime1) 
    private long[] parseCpuRate(String cpuRate, String pidCpuRate) {
        long[] result = new long[2];
        String[] cpuInfoArray = cpuRate.split(" ");
        if (cpuInfoArray.length < 9) {
            return result;
        }
        // 从系统启动开始累计到当前时刻，用户态的CPU时间，不包含 nice值为负进程
        long user = Long.parseLong(cpuInfoArray[2]);
        // 从系统启动开始累计到当前时刻，nice值为负的进程所占用的CPU时间
        long nice = Long.parseLong(cpuInfoArray[3]);
        // 从系统启动开始累计到当前时刻，核心时间
        long system = Long.parseLong(cpuInfoArray[4]);
        // 从系统启动开始累计到当前时刻，除硬盘IO等待时间以外其它等待时间
        long idle = Long.parseLong(cpuInfoArray[5]);
        // 从系统启动开始累计到当前时刻，硬盘IO等待时间
        long ioWait = Long.parseLong(cpuInfoArray[6]);
        // CPU总时间 = 以上所有加上irq（硬中断）和softirq（软中断）的时间
        long sysCpuTime = user + nice + system + idle + ioWait + Long.parseLong(cpuInfoArray[7]) + Long.parseLong(cpuInfoArray[8]);

        String[] pidCpuInfos = pidCpuRate.split(" ");
        if (pidCpuInfos.length < 17) {
            return result;
        }

        long pidCpuTime = Long.parseLong(pidCpuInfos[13]) + Long.parseLong(pidCpuInfos[14])
                + Long.parseLong(pidCpuInfos[15]) + Long.parseLong(pidCpuInfos[16]);

        result[0] = sysCpuTime;
        result[1] = pidCpuTime;
        return result;
//
//        if (mTotalLast != 0) {
//            long totalTime = total - mTotalLast;
//            if (totalTime > 0 && event != null) {
//                event.computeAvg((pidCpuTime - mAppCpuTimeLast) * 100L / totalTime);
//            }
//        }
//        mTotalLast = total;
//        mAppCpuTimeLast = pidCpuTime;
    }

}
