package cc.wudoumi.framework.net.convert;


import cc.wudoumi.framework.net.exception.DataException;

/**
 * @author qianjujun
 * @time   2016/3/5 16:13
 * @TODO  json解析接口
 */
public interface ConvertJson<T> {

    T convert(String str) throws DataException;



}
