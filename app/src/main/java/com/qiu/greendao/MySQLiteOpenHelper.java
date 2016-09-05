package com.qiu.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiu.greendao.db.DaoMaster;
import com.qiu.greendao.db.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qiu on 2016/9/5 0005.
 */
public class MySQLiteOpenHelper extends DaoMaster.DevOpenHelper
{
    public MySQLiteOpenHelper (Context context, String name)
    {
        super (context, name);
    }

    public MySQLiteOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super (context, name, factory);
    }

    @Override
    public void onUpgrade (Database db, int oldVersion, int newVersion)
    {
//        super.onUpgrade (db, oldVersion, newVersion);
        MigrationHelper.migrate (db,UserDao.class);
    }
}
