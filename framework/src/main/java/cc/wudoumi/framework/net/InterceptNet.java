package cc.wudoumi.framework.net;


import cc.wudoumi.framework.net.exception.DataException;
import cc.wudoumi.framework.net.exception.ServerException;

/**
 * @author qianjujun
 * @time   2016/3/5 16:44
 * @TODO  网络请求结果拦截,可以在assent文件下的config.properties里动态配置实现类
 */
public interface InterceptNet {
    /**
     *
     * @param resopne  接口返回的原始字符串
     * @return         返回结果见InterceptResult
     * @throws DataException
     * @throws ServerException
     */
    InterceptResult handler(String resopne) throws DataException,ServerException;
}
