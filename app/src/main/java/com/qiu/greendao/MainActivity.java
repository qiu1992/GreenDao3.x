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
            for (int i = 0;i < 100;i++)
            {
                userList.add (new User ("qiu" + System.currentTimeMillis (),25f));
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
}
