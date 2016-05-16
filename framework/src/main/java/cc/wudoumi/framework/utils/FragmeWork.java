package cc.wudoumi.framework.utils;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import cc.wudoumi.framework.net.NetInterfaceFactory;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/17
 */
public class FragmeWork {
    private static Application application;

    private FragmeWork(){}
    public static void init(Application application){
        FragmeWork.application = application;

        initMainThread();
    }

    public static Context getContext(){
        if(application==null){
            throw new RuntimeException("请执行init");
        }
        return application;
    }


    private static void initMainThread(){
        NetworkUtils.setContext(getContext());
        NetInterfaceFactory.getNetInterface().start(getContext());
    }




}
