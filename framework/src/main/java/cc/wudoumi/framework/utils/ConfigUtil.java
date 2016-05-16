package cc.wudoumi.framework.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by huyf on 2016/2/27.
 */
public class ConfigUtil {


    private static Properties pro;

    private static String interceptNetName;
    private static Boolean debug;

    static {
        init();
    }

    private static void init(){
        pro = new Properties();
        try {
            pro.load(ConfigUtil.class.getResourceAsStream("/assets/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInterceptNet(){
        if(interceptNetName==null){
            interceptNetName = pro.getProperty(ConfigKey.INTERCEPTNET);
        }
        return interceptNetName;
    }

    public static boolean isDebug(){
        if(debug==null){
            debug = Boolean.parseBoolean(pro.getProperty(ConfigKey.DEBUG));
            Log.d("qianjujun","debug:"+pro.getProperty(ConfigKey.DEBUG));
            Log.d("qianjujun","debug:"+debug);
        }
        return debug;
    }

}
