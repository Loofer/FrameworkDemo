package cc.wudoumi.framework.net.convert;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cc.wudoumi.framework.net.exception.DataException;
import cc.wudoumi.framework.net.exception.NoDataException;


/**
 * @author qianjujun
 * @time   2016/3/5 16:14
 * @TODO  gson解析list
 */
public  class GsonListConvertJson<T> extends BaseGsonConvertJson<List<T>> {
    private final Class<T> clazz;

    public GsonListConvertJson(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public List<T> convert(String str) throws DataException {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i = 0;i<jsonArray.length();i++){
                String beanString = jsonArray.getString(i);
                list.add(gson.fromJson(beanString,clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException();
        }
        if(list.size()==0){
            throw new NoDataException();
        }
        return list;
    }
}
