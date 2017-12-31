package com.example.developerhaoz.sleephelper.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by lizhonglian on 2017/12/31.
 */

public class ActivityStackUtil {


    private final String TAG = this.getClass().getSimpleName();
    private static Stack<Activity> mActivityStack;
    private static volatile ActivityStackUtil sInstance;

    private ActivityStackUtil() {
    }

    public static ActivityStackUtil getInstance() {
        if (sInstance == null) {
            synchronized (ActivityStackUtil.class) {
                if (sInstance == null) {
                    sInstance = new ActivityStackUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取Stack
     *
     * @return
     */
    public Stack<Activity> getStack() {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        return mActivityStack;
    }

    /**
     * 获取最后一个入栈Activity理论上是应用当前停留Activity
     * (前提是所有Activity均在onCreate调用 push onDestroy调用pop)
     *
     * @return
     */
    public Activity getCurrent() {
        if (mActivityStack != null && mActivityStack.size() != 0) {
            Activity activity = mActivityStack.lastElement();
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 获取前一个Activity
     *
     * @return
     */
    public Activity getPrevious() {
        if (mActivityStack != null && mActivityStack.size() >= 2) {
            Activity activity = mActivityStack.get(mActivityStack.size() - 2);
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public void push(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        mActivityStack.add(activity);
    }

    /**
     * 出栈
     *
     * @param activity Activity对象
     * @param isFinish 是否调用finish(),onDestroy()生命周期只做移除数组操作不做finish()
     */
    public void pop(Activity activity, boolean isFinish) {
        if (activity != null) {
            if (isFinish) {
                activity.finish();
            }
            if (mActivityStack != null && mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
            }
        }
    }

    public void pop(Activity activity) {
        pop(activity, true);
    }

    /**
     * 将栈里的Activity全部清空
     */
    public void popAll() {
        if (mActivityStack != null) {
            while (mActivityStack.size() > 0) {
                Activity activity = this.getCurrent();
                if (activity == null) {
                    break;
                }
                pop(activity);
            }
        }
    }

    /**
     * 将堆栈里退回某个Activity为止
     *
     * @param cls
     */
    public void popAllExceptCurrent(Class cls) {
        while (true) {
            Activity activity = this.getCurrent();
            if (activity == null || activity.getClass().equals(cls)) {
                return;
            }
            pop(activity);
        }
    }

    /**
     * 将栈里除某个Activity全部清空
     *
     * @param cls
     */
    public void popAllExcept(Class cls) {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return;
        }
        for (Activity activity : mActivityStack) {
            if (activity != null && !activity.getClass().equals(cls)) {
                pop(activity);
            }
        }
    }

    /**
     * 应用程序退出
     */
    public void exit() {
        try {
            popAll();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
