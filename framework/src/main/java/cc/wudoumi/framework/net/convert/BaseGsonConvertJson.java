package cc.wudoumi.framework.net.convert;

import com.google.gson.Gson;


/**
 * @author qianjujun
 * @time   2016/3/5 16:13
 * @TODO  解析json
 */
public abstract class BaseGsonConvertJson<T>  implements ConvertJson<T> {
    protected static Gson gson = new Gson();

    public static void initGson(Gson gson){
        BaseGsonConvertJson.gson = gson;
    }



}
