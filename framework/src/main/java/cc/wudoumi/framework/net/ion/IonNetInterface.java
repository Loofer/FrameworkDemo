package cc.wudoumi.framework.net.ion;

import android.content.Context;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.FutureBuilder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

import cc.wudoumi.framework.net.InterceptNet;
import cc.wudoumi.framework.net.InterceptResult;
import cc.wudoumi.framework.net.NetInterface;
import cc.wudoumi.framework.net.RequestParem;
import cc.wudoumi.framework.net.ResultCode;
import cc.wudoumi.framework.net.ResultMessage;
import cc.wudoumi.framework.net.convert.ConvertJson;
import cc.wudoumi.framework.net.convert.GsonConvertJson;
import cc.wudoumi.framework.net.convert.GsonListConvertJson;
import cc.wudoumi.framework.net.exception.DataException;
import cc.wudoumi.framework.net.exception.NoDataException;
import cc.wudoumi.framework.net.exception.ServerException;
import cc.wudoumi.framework.net.request.RequestListner;
import cc.wudoumi.framework.utils.ConfigUtil;
import cc.wudoumi.framework.utils.NetworkUtils;


/**
 * @author qianjujun
 * @time   2016/3/5 16:16
 * @TODO  使用ion框架实现数据请求
 */
public class IonNetInterface implements NetInterface{



    private static IonNetInterface ionNetInterface;

    private IonNetInterface(){}

    public static IonNetInterface get(){
        if(ionNetInterface==null){
            synchronized (IonNetInterface.class){
                if(ionNetInterface==null){
                    ionNetInterface = new IonNetInterface();
                }
            }
        }

        return ionNetInterface;
    }


    private Context app;
    private InterceptNet interceptNet;
    @Override
    public void start(Context app) {
        if(this.app==null){
            this.app = app;

            //此处获取拦截接口的实现类，剥离json数据的封装
            try {
                String interceptNetName = ConfigUtil.getInterceptNet();
                interceptNet = (InterceptNet) Class.forName(interceptNetName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        Ion.getDefault(app).cancelAll();
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner) {
        doRequest(requestParem, requestListner, null);
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, final RequestListner<T> requestListner, final ConvertJson<T> convertJson) {
        requestListner.onStart();

        if(!NetworkUtils.isNetwork()){
            ResultMessage e = ResultMessage.error(ResultCode.ERROR_NO_NET,"无网络");
            requestListner.onEnd(e);
            return;
        }
        //处理参数
        FutureBuilder builder = handlerParems(requestParem);

        //处理回调
        FutureCallback<String> callback = handlerCallBack(requestListner,convertJson,requestParem.isIntercept(),requestParem.getInterceptNet());

        //请求
        //builder.group(requestListner.getTag()).asString().setCallback(callback);


        builder.group(requestListner.getTag()).asString(Charset.forName("UTF-8")).setCallback(callback);

    }



    @Override
    public void cancel(Object tag) {
        Ion.getDefault(app).cancelAll(tag);
    }


    /**
     * 处理ion的请求参数
     * @param requestParem
     * @return
     */
    private FutureBuilder handlerParems(RequestParem requestParem){

        //requestParem.putHeader("Content-Type", "application/json");
        //Map<String, List<String>> headerParams = requestParem.getHeader();  php测试不通过
        //Map<String, List<String>> bodyParameter = requestParem.getBodyParameter();
        Map<String,String> headerParams = requestParem.getMapHeader();
        Map<String,String> mapParameter = requestParem.getMapParems();

        boolean jsonParem = false;

        Builders.Any.B b = Ion.with(app).
                load(requestParem.getRequestMethod(), requestParem.getUrl());
        if(headerParams!=null&&headerParams.size()>0){
            for(String key:headerParams.keySet()){
                b = b.addHeader(key,headerParams.get(key));
            }

            jsonParem = headerParams.containsKey("Content-Type")&&headerParams.get("Content-Type").contains("json");
        }
        if(mapParameter!=null&&mapParameter.size()>0){

            if(jsonParem){
                b.setStringBody(RequestParem.toJsonFormMap(mapParameter));
            }else{
                Builders.Any.U u = null;
                for(String key : mapParameter.keySet()){
                    if(null==u){
                        u = b.setBodyParameter(key,mapParameter.get(key));
                    }else{
                        u = u.setBodyParameter(key,mapParameter.get(key));
                    }
                }
                return u;
            }
        }
        return b;
    }

    private <T> FutureCallback<String> handlerCallBack(final RequestListner<T> requestListner, final ConvertJson<T> convertJson, final boolean intercept,final InterceptNet cstInterceptNet){
        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception exception, String response) {
                if(response!=null&&response.length()>0){
                    if(response.charAt(0) == 65279){//去掉Bom头
                        response = response.substring(1, response.length());
                    }
                }

                ResultMessage resultMessage = null;
                if(null==exception){
                    //拦截到服务器返回的字符串，剥离包装的flag，message
                    InterceptNet tempInterceptNet = cstInterceptNet==null?interceptNet:cstInterceptNet;
                    if (tempInterceptNet != null&&intercept) {
                        InterceptResult result = null;
                        try {
                            result = tempInterceptNet.handler(response);
                        } catch (DataException e) {
                            e.printStackTrace();
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                        } catch (ServerException e) {
                            resultMessage = e.parse();
                            e.printStackTrace();
                        } catch (Exception e) {
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, DataException.ERROR_PARSE, e);
                        }
                        if (result == null) {
                            requestListner.onEnd(resultMessage == null ? ResultMessage.error(ResultMessage.ERROR_OTHER, "未知错误") : resultMessage);
                            return;
                        } else {
                            response = result.getResult();
                            resultMessage = ResultMessage.success(result.getMessage());
                        }
                    }
                    try {
                        boolean success = false;
                        if(convertJson != null){
                            T t = convertJson.convert(response);
                            success = requestListner.onSuccess(t);
                        }else if(requestListner.getClazz() == String.class){
                            success = requestListner.onSuccess((T)response);
                        }else if(response.charAt(0)=='['&&response.charAt(response.length()-1)==']'){
                            List<T> t = new GsonListConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        }else if(response.charAt(0)=='{'&&response.charAt(response.length()-1)=='}'){
                            T t = new GsonConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        }
                        if(!success){
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, "数据处理失败");
                        }
                    } catch (NoDataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_NO_DATA, e);
                    }catch (DataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_UI, "数据处理异常", e);
                    } finally {
                        requestListner.onEnd(resultMessage == null ? ResultMessage.success() : resultMessage);
                    }

                }else{
                    if(exception instanceof CancellationException){
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_CANCEL, "已取消请求", exception);
                    }else{
                        resultMessage = ResultMessage.error(ResultMessage.EROOR_NET, exception);
                    }
                    requestListner.onEnd(resultMessage);
                }
            }
        };
        return callback;
    }

}
