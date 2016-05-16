package cc.wudoumi.framework.net;


import android.content.Context;

import cc.wudoumi.framework.net.convert.ConvertJson;
import cc.wudoumi.framework.net.request.RequestListner;


/**
 * @author qianjujun
 * @time   2016/3/5 16:50
 * @TODO    网络请求的封装接口
 */
public interface NetInterface {
    /**
     * 初始化，在application的oncreate中调用
     * @param app
     */
    void start(Context app);

    /**
     * 在合适的地方取消所有接口
     * 此处需要注意：如在退出最后一个Activity调用，会清掉appliaction的对象
     * 而系统会缓存app（时间不确定），如未被系统杀死，再次启动APP，不会执行oncreat方法
     *
     * 正确处理：1，在退出app时强制杀死进程，2不调用（建议）
     */
    void stop();
    <T>void doRequest(RequestParem requestParem, RequestListner<T> requestListner);
    <T>void doRequest(RequestParem requestParem, RequestListner<T> requestListner, ConvertJson<T> convertJson);
    void cancel(Object tag);
}
