package com.qiu.greendao;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qiu.greendao.bean.User;
import com.qiu.greendao.db.DBHelper;
import com.qiu.greendao.db.DaoMaster;
import com.qiu.greendao.db.UserDao;

import org.greenrobot.greendao.rx.RxDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button writeBtn;
    private Button readBtn;
    private TextView resTv;
    private final String TAG = "qiuqiu";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        findViews ();
    }

    private void findViews ()
    {
        writeBtn = (Button) findViewById (R.id.write_btn);
        readBtn = (Button) findViewById (R.id.read_btn);
        resTv = (TextView) findViewById (R.id.res_tv);

        writeBtn.setOnClickListener (this);
        readBtn.setOnClickListener (this);
    }

    @Override
    public void onClick (View v)
    {
        if (v == writeBtn)
        {
            // Handle clicks for writeBtn
//            final User user = new User ("qiu" + System.currentTimeMillis (), 21.8f);
//            user.setDesc ("你好吗");
//
//            final DaoMaster daoMaster = DBHelper.getInstance ().getDaoMaster ();
//            final UserDao userDao = daoMaster.newSession ().getUserDao ();

//            daoMaster.getDatabase ().beginTransaction ();
//            userDao.insert (user);
//            daoMaster.getDatabase ().setTransactionSuccessful ();
//            daoMaster.getDatabase ().endTransaction ();

//            userDao.getSession ().runInTx (new Runnable ()
//            {
//                @Override
//                public void run ()
//                {
//                    userDao.insertInTx (user);
//                }
//            });

//            try
//            {
//                userDao.getSession ().callInTx (new Callable<Object> ()
//                {
//                    @Override
//                    public Object call () throws Exception
//                    {
//                        Log.d (TAG, "call: ");
//                        userDao.insertInTx (user);
//                        return null;
//                    }
//                });
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace ();
//            }


//            userDao.insertInTx (user);

            RxDao<User, Void> rxDao = DBHelper.getInstance ().getDaoSession ().getUserDao ().rx ();
//            rxDao.insertInTx (user)
//                    .observeOn (AndroidSchedulers.mainThread ())
//                    .subscribe (new Subscriber<Object[]> ()
//                    {
//                        @Override
//                        public void onCompleted ()
//                        {
//                            Log.d (TAG, "onCompleted: ");
//                        }
//
//                        @Override
//                        public void onError (Throwable e)
//                        {
//                            Log.d (TAG, "onError: " + Log.getStackTraceString (e));
//                        }
//
//                        @Override
//                        public void onNext (Object[] objects)
//                        {
//                            Log.d (TAG, "onNext: ");
//                        }
//                    });

            List<User> userList = new ArrayList<> ();
            for (int i = 0; i < 100; i++)
            {
                userList.add (new User ("qiu" + System.currentTimeMillis (), 25f, "fdfds"));
                SystemClock.sleep (10);
            }

            rxDao.insertInTx (userList)
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (new Subscriber<Iterable<User>> ()
                    {
                        @Override
                        public void onCompleted ()
                        {
                            Log.d (TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError (Throwable e)
                        {
                            Log.d (TAG, "onError: " + Log.getStackTraceString (e));
                        }

                        @Override
                        public void onNext (Iterable<User> users)
                        {
                            Log.d (TAG, "onNext: ");
                        }
                    });
        }
        else if (v == readBtn)
        {
            // Handle clicks for readBtn
            UserDao userDao = DBHelper.getInstance ().getDaoSession ().getUserDao ();
            final int count = (int) userDao.queryBuilder ().count ();

//            User user = userDao.queryBuilder ().list ().get (count - 1);
//            resTv.setText (user.getName () + "/" + user.getAge () + "/" + user.getDesc ());

            userDao.queryBuilder ()
                    .rx ()
                    .list ()
//                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (new Subscriber<List<User>> ()
                    {
                        @Override
                        public void onCompleted ()
                        {
                            Log.d (TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError (Throwable e)
                        {
                            Log.d (TAG, "onError: " + Log.getStackTraceString (e));
                        }

                        @Override
                        public void onNext (List<User> users)
                        {
                            Log.d (TAG, "onNext: ");
                            User user = users.get (count - 1);
                            resTv.setText (user.getName () + "/" + user.getAge () + "/" + user.getDesc ());
                        }
                    });
        }
    }

    /*
    09-05 16:01:53.519 6828-6828/com.qiu.greendao E/SQLiteOpenHelper: Couldn't open qiu.db3 for writing (will try read-only):
                                                                  net.sqlcipher.database.SQLiteConstraintException: UNIQUE constraint failed: USER.NAME: INSERT INTO USER (NAME,AGE) SELECT NAME,AGE FROM USER_TEMP;
                                                                      at net.sqlcipher.database.SQLiteDatabase.native_execSQL(Native Method)
                                                                      at net.sqlcipher.database.SQLiteDatabase.execSQL(SQLiteDatabase.java:2161)
                                                                      at org.greenrobot.greendao.database.EncryptedDatabase.execSQL(EncryptedDatabase.java:37)
                                                                      at com.qiu.greendao.MigrationHelper.restoreData(MigrationHelper.java:115)
                                                                      at com.qiu.greendao.MigrationHelper.migrate(MigrationHelper.java:29)
                                                                      at com.qiu.greendao.MySQLiteOpenHelper.onUpgrade(MySQLiteOpenHelper.java:30)
                                                                      at org.greenrobot.greendao.database.DatabaseOpenHelper$EncryptedHelper.onUpgrade(DatabaseOpenHelper.java:185)
                                                                      at net.sqlcipher.database.SQLiteOpenHelper.getWritableDatabase(SQLiteOpenHelper.java:173)
                                                                      at net.sqlcipher.database.SQLiteOpenHelper.getReadableDatabase(SQLiteOpenHelper.java:227)
                                                                      at net.sqlcipher.database.SQLiteOpenHelper.getReadableDatabase(SQLiteOpenHelper.java:214)
                                                                      at org.greenrobot.greendao.database.DatabaseOpenHelper.getEncryptedWritableDb(DatabaseOpenHelper.java:134)
                                                                      at com.qiu.greendao.db.DBHelper.getDaoMaster(DBHelper.java:36)
                                                                      at com.qiu.greendao.db.DBHelper.getDaoSession(DBHelper.java:47)
                                                                      at com.qiu.greendao.MainActivity.onClick(MainActivity.java:156)
                                                                      at android.view.View.performClick(View.java:5198)
                                                                      at android.view.View$PerformClick.run(View.java:21147)
                                                                      at android.os.Handler.handleCallback(Handler.java:739)
                                                                      at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                      at android.os.Looper.loop(Looper.java:148)
                                                                      at android.app.ActivityThread.main(ActivityThread.java:5417)
                                                                      at java.lang.reflect.Method.invoke(Native Method)
                                                                      at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:726)
                                                                      at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:616)

     */
}
