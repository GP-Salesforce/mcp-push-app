package com.goldenplanet.mcppushdemo_aos.util;

import android.util.Log;

import com.goldenplanet.mcppushdemo_aos.BuildConfig;


/**
 * Created by Kim Namhoon on 11/23/23.
 */

public class LogMsg {
    private static final int VERBOSE   = 1;
    private static final int DEBUG     = 2;
    private static final int INFO      = 3;
    private static final int WARN      = 4;
    private static final int ERROR     = 5;
    private static final int NO_LOG    = 6;

    private static final int MSG_LEVEL = BuildConfig.DEBUG ?  VERBOSE : NO_LOG;


    private static String myTAG = " [GP]";


    /**
     * Do not send a log message.
     */
    public static void x(String tag, String msg)
    {
        return;
    }


    public static void v(String msg)
    {
        if (VERBOSE < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.v(tag, totalMsg);
    }


    public static void v(String msg, Throwable tr)
    {
        if (VERBOSE < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.v(tag, totalMsg, tr);
    }


    public static void d(String msg)
    {
        if (DEBUG < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.d(tag, totalMsg);
    }


    public static void d(String msg, Throwable tr)
    {
        if (DEBUG < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.d(tag, totalMsg, tr);
    }


    public static void i(String msg)
    {
        if (INFO < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();


        Log.i(tag, totalMsg);
    }


    public static void i(String msg, Throwable tr)
    {
        if (INFO < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.i(tag, totalMsg, tr);
    }


    public static void w(String msg)
    {
        if (WARN < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.w(tag, totalMsg);
    }


    public static void w(String msg, Throwable tr)
    {
        if (WARN < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.w(tag, totalMsg, tr);
    }


    public static void e( String msg)
    {
        if (ERROR < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.e(tag, totalMsg);
    }


    public static void e(String msg, Throwable tr)
    {
        if (ERROR < MSG_LEVEL)
            return;
        StackTraceElement[] ste = (new Throwable()).getStackTrace();
        String totalMsg = makeMsg(msg, ste);
        String tag = ste[1].getClassName();

        Log.e(tag, totalMsg, tr);
    }

    private static String makeMsg(String msg, StackTraceElement[] ste)
    {
        return "[" + ste[1].getFileName() + ":" + ste[1].getLineNumber() + "]" + "[" + ste[1].getMethodName() + "()] #[" + msg + "]#"+myTAG;
    }
}
