package com.qiu.greendao.db;


import com.qiu.greendao.MyApplication;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qiu on 2016/9/4 0004.
 */
public class DBHelper
{
    private final String DB_NAME = "qiu.db3";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static class LazyDBHelper
    {
        private static final DBHelper DB_HELPER = new DBHelper ();
    }

    public static DBHelper getInstance ()
    {
        return LazyDBHelper.DB_HELPER;
    }

    public DaoMaster getDaoMaster ()
    {
        if (null == daoMaster)
        {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper (MyApplication.appContext,DB_NAME);
            Database database = devOpenHelper.getEncryptedWritableDb ("qiu1992");
            daoMaster = new DaoMaster (database);
        }

        return daoMaster;
    }

    public DaoSession getDaoSession ()
    {
        if (null == daoSession)
        {
            daoSession = getDaoMaster ().newSession ();
        }
        return daoSession;
    }

    /**
     * 关闭数据库
     */
    private void closeDB ()
    {
        if (null != daoMaster)
        {
            daoMaster.getDatabase ().close ();
        }
    }
}
