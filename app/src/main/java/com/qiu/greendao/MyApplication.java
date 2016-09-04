package com.qiu.greendao;

import android.app.Application;
import android.content.Context;

/**
 * Created by qiu on 2016/9/4 0004.
 */
public class MyApplication extends Application
{
    public static Context appContext;

    @Override
    public void onCreate ()
    {
        super.onCreate ();

        appContext = this;
    }
}
