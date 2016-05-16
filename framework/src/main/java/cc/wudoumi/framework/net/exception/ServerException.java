package cc.wudoumi.framework.net.exception;


import cc.wudoumi.framework.net.ResultMessage;

/**
 * @author qianjujun
 * @time   2016/3/5 16:15
 * @TODO  服务器返回数据时 flag！=1时的各种异常，，如密码不正确等
 */
public class ServerException extends Exception{
    private int errorCode;

    public ServerException(String detailMessage, int errorCode) {
        super(detailMessage);
        this.errorCode = errorCode;
    }


    public ResultMessage parse(){
        return ResultMessage.error(errorCode, this);
    }
}
