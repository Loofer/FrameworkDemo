package cc.wudoumi.framework.net.exception;

/**
 * @author qianjujun
 * @time   2016/3/5 16:15
 * @TODO  无数据
 */
public class NoDataException extends DataException{
    public NoDataException() {
        super(ERROR_NO_DATA);
    }
}
