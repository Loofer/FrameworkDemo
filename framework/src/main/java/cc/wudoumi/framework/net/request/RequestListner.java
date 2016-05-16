package cc.wudoumi.framework.net.request;


import java.util.List;
import java.util.UUID;

import cc.wudoumi.framework.net.NetInterfaceFactory;
import cc.wudoumi.framework.net.ResultMessage;


/**
 * @author qianjujun
 * @time   2016/3/5 16:40
 * @TODO   网络接口回调
 */
public abstract class RequestListner<T> {

    private final Class<T> clazz;
    private final Object tag;


    public RequestListner(Class<T> clazz) {
        tag = UUID.randomUUID().toString();
        this.clazz = clazz;
    }

    public RequestListner(Object tag, Class<T> clazz) {
        this.tag = tag;
        this.clazz = clazz;
    }

    /**
     * 开始网络请求
     */
    public void onStart() {

    }

    /**
     * 网络请求结束
     * @param resultMessage 请求结束后的处理结果
     */
    public void onEnd(ResultMessage resultMessage) {

    }

    public final void cancel() {
        NetInterfaceFactory.getNetInterface().cancel(tag);
    }

    public final Object getTag() {
        return tag;
    }


    public boolean onSuccess(T t) throws Exception {
        return true;
    }

    public boolean onSuccess(List<T> tList) throws Exception {
        return true;
    }

    public final Class<T> getClazz() {
        return clazz;
    }

}
