package com.example.worktime.interacor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.worktime.util.ContextUtil;
import com.example.worktime.util.KsoapRequestUtil;
import com.example.worktime.util.WebServiceUtil;
import java.util.HashMap;
import java.util.Map;
public class LoginInteractor {
    public interface OnLoginFinishedListener {
        void onFailed(String errorMessage);
        void onSuccess();
    }
    OnLoginFinishedListener listener;
    public void login(String userId, String password, OnLoginFinishedListener listener) {
        this.listener = listener;
        //获取 用户id 和 密码  并将其组装成map  传给webservice
        Map<String, String> map = new HashMap<>();
        map.put("loginName", userId);
        map.put("password", password);
        toRequestWebService(WebServiceUtil.method_checklogin, map);

    }
    private void toRequestWebService(Object... params) {
        new loginTask().execute(params);
    }
    private class loginTask extends AsyncTask<Object, Object, String> {
        /**
         * @param objects
         * @deprecated
         */
        @Override
        protected String doInBackground(Object... objects) {
            String resultFromWeb = "";
            if (objects != null) {
                resultFromWeb = KsoapRequestUtil.requestToWebservice(((String) objects[0]),
                        ((Map<String, String>) objects[1]));

            }
            return resultFromWeb;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("onPostExecute: ", s);
            switch (s) {
                case "0":
                    /*调用耦合子回调接口中的函数*/
                    listener.onFailed("网络连接超时");
                    break;
                case "1":
                    listener.onFailed("用户名为空");
                    break;
                case "2":
                    listener.onFailed("密码为空");
                    break;
                case "3":
                    listener.onFailed("用户名或密码错误");
                    break;
                case "4":
                    listener.onFailed("用户名被停用");
                    break;
                case "5":
                    listener.onSuccess();
                    break;
                default:
                    break;
            }
        }
    }
}