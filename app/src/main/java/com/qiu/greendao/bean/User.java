package com.qiu.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by qiu on 2016/9/4 0004.
 */
@Entity
public class User
{
    // 隐含了@Index注解
    @Unique
    private String name;

    @NotNull
    private float age;

    @Transient
    private String desc;

    @Generated(hash = 1803680259)
    public User(String name, float age) {
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getAge ()
    {
        return age;
    }

    public void setAge (float age)
    {
        this.age = age;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }
}
