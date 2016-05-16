package cc.wudoumi.framework.net;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;



/**
 * @author qianjujun
 * @time   2016/3/5 16:55
 * @TODO  请求参数
 */
public class RequestParem {

    protected static Gson gson = new Gson();
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";


    private final String url;
    private final String requestMethod;

    protected Map<String,String> mapParems = new HashMap<>();

    protected Map<String,String> mapHeader = new HashMap<>();



    private boolean intercept;//设置是否拦截请求结果，默认拦截

    private InterceptNet interceptNet;//设置自定义拦截

    protected RequestParem(String url, String requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.intercept = true;
        mapHeader.put("Content-Type", "application/json");
    }

    public static RequestParem auto(String url, String type){
        RequestParem requestParem = new RequestParem(url,type);
        return requestParem;
    }

    public static RequestParem get(String url){
        RequestParem requestParem = new RequestParem(url,GET);
        return requestParem;
    }

    public static RequestParem put(String url){
        RequestParem requestParem = new RequestParem(url,PUT);
        return requestParem;
    }

    public static RequestParem post(String url){
        RequestParem requestParem = new RequestParem(url,POST);
        return requestParem;
    }

    public static RequestParem delete(String url){
        RequestParem requestParem = new RequestParem(url,DELETE);
        return requestParem;
    }


    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }


    public Map<String, String> getMapParems() {
        return mapParems;
    }

    public void setMapParems(Map<String, String> mapParems) {
        if(mapParems!=null){
            this.mapParems = mapParems;
        }
    }

    public Map<String, String> getMapHeader() {
        return mapHeader;
    }

    public void setMapHeader(Map<String, String> mapHeader) {
        if(mapHeader!=null){
            mapHeader.put("Content-Type", "application/json");
            this.mapHeader = mapHeader;
        }
    }

    public boolean isIntercept() {
        return intercept;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }

    public InterceptNet getInterceptNet() {
        return interceptNet;
    }

    public void setInterceptNet(InterceptNet interceptNet) {
        this.interceptNet = interceptNet;
    }



    public RequestParem put(String key,Object value){
        mapParems.put(key,String.valueOf(value));
        return this;
    }



    public static String toJsonFormMap(Map<String,String> map){
        if(map==null){
            map = new HashMap<>();
        }
        return gson.toJson(map);
    }


}
