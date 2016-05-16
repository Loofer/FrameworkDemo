package cc.wudoumi.framework.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/19
 */
public class ToastUtil {
    public static void showToast(Context context,String msg){
        if(TextUtils.isEmpty(msg)||context==null){
            return;
        }
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
