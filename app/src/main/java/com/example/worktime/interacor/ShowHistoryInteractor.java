package com.example.worktime.interacor;
import android.os.AsyncTask;
import android.util.Log;
import com.example.worktime.model.TBlogBean;
import com.example.worktime.util.GsonUtil;
import com.example.worktime.util.KsoapRequestUtil;
import com.example.worktime.util.WebServiceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ShowHistoryInteractor
{
    public static final String TAG = ShowHistoryInteractor.class.getSimpleName();
    //声明接口实例
    OnHistoryCreateFinishListener historyCreateFinishListener;
    //定义一个回调接口
    public  interface OnHistoryCreateFinishListener{
        void onGetHistorySuccess(List<TBlogBean> tBlogBeans);
        void onGetHistoryFailed();
    }
    public void getHistory(String Pdate, String WorkId, OnHistoryCreateFinishListener listener) {
        this.historyCreateFinishListener = listener;
        Map<String, String> map = new HashMap<>();
        map.put("Pdate", Pdate);
        map.put("WorkId", WorkId);
        toRequestWebService(WebServiceUtil.method_get_performance_with_date, map);
    }
    private void toRequestWebService(Object... params) {
        new ShowHistoryTask().execute(params);
    }
    /*定义一个内部类*/
    public class ShowHistoryTask extends AsyncTask<Object, Object, String> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param objects The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
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
            List<TBlogBean> beans;
           // String finalResult="";
            Log.e(TAG, "onPostExecute: "+s );
            if (!s.equals("请求数据出错")) {
                beans = GsonUtil.getInstance().getTBlogList(s);
                historyCreateFinishListener.onGetHistorySuccess(beans);
            } else {
                historyCreateFinishListener.onGetHistoryFailed();

            }
        }
    }
}
