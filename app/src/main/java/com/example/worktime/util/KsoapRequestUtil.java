package com.example.worktime.util;
import android.content.Context;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
public class KsoapRequestUtil {
    public static  String requestToWebservice(String methodName, Map<String, String> params) {
        SoapObject soapObject = new SoapObject(WebServiceUtil.Namespace, methodName);
        if (params != null) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = ((Map.Entry) iterator.next());
                soapObject.addProperty(((String) entry.getKey()), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        /*外网连接*/
       HttpTransportSE httpTransportSE = new HttpTransportSE(WebServiceUtil.MOBILE_DATA_WEB_SERVICE_URL);
        /*内网连接*/
       // HttpTransportSE httpTransportSE = new HttpTransportSE(WebServiceUtil.INNER_WIFI_WEB_SERVICE_URL);

        try {
            httpTransportSE.call(null, envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            SoapPrimitive soapPrimitive = ((SoapPrimitive) envelope.getResponse());
            if (soapPrimitive != null) {
                String jsonResult=soapPrimitive.getValue().toString();
                return jsonResult;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return "请求数据出错";
    }




/*   测试用的   暂时没有集成到app中  根据网络状态进行判断 选择哪一个地址进行连接*/
    public static  String requestToWebservice(String methodName, Map<String, String> params, Context context) {
        SoapObject soapObject = new SoapObject(WebServiceUtil.Namespace, methodName);
        HttpTransportSE httpTransportSE=null;

        if (params != null) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = ((Map.Entry) iterator.next());
                soapObject.addProperty(((String) entry.getKey()), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;


        if (ConnectUtil.isNetworkConnecting(ContextUtil.getApplicationContext()) && ConnectUtil.isWifiConnected(ContextUtil.getApplicationContext())) {


           httpTransportSE = new HttpTransportSE(WebServiceUtil.INNER_WIFI_WEB_SERVICE_URL);


        } else if (ConnectUtil.isNetworkConnecting(ContextUtil.getApplicationContext()) && ConnectUtil.isMobileDataConnected(ContextUtil.getApplicationContext())) {


       httpTransportSE = new HttpTransportSE(WebServiceUtil.MOBILE_DATA_WEB_SERVICE_URL);

        }







       // HttpTransportSE httpTransportSE = new HttpTransportSE(WebServiceUtil.INNER_WIFI_WEB_SERVICE_URL);
        try {
            httpTransportSE.call(null, envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            SoapPrimitive soapPrimitive = ((SoapPrimitive) envelope.getResponse());
            if (soapPrimitive != null) {
                String jsonResult=soapPrimitive.getValue().toString();
                return jsonResult;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return "请求数据出错";
    }
}
