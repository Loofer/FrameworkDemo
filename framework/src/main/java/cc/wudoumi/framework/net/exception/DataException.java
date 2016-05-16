package cc.wudoumi.framework.net.exception;

/**
 * @author qianjujun
 * @time   2016/3/5 16:14
 * @TODO  数据相关异常  如解析数据失败
 */
public class DataException extends Exception {


    public static final String ERROR_PARSE = "数据解析错误";

    public static final String ERROR_NO_DATA = "无数据";

    public DataException(String detailMessage) {
        super(detailMessage);
    }

    public DataException(String message,Throwable throwable) {
        super(ERROR_PARSE,throwable);
    }

    public DataException(){
        super(ERROR_PARSE);
    }


}
