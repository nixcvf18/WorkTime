package com.example.worktime.interacor;
import android.os.AsyncTask;
import android.util.Log;
import com.example.worktime.util.GsonUtil;
import com.example.worktime.util.KsoapRequestUtil;
import com.example.worktime.util.WebServiceUtil;
import java.util.HashMap;
import java.util.Map;
public class MainInteractor {
    public static final String TAG = MainInteractor.class.getSimpleName();
    OnMainCreateFinishListener listener;
    public interface  OnMainCreateFinishListener{
       void onGetNameSuccess(String userName);
    }
    public void getName(String WorkId, OnMainCreateFinishListener listener) {
        this.listener = listener;
        Map<String, String> map = new HashMap<>();
        map.put("userid", WorkId);
        toRequestWebService(WebServiceUtil.method_checkWorkid_return_W_Name, map);
    }
    private void toRequestWebService(Object... params) {
        new MainTask().execute(params);
    }
    private class MainTask extends AsyncTask<Object, Object, String> {
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
            String finalResult="";
            Log.e(TAG, "onPostExecute: "+s );
            finalResult = GsonUtil.getInstance().getTBlogList(s).get(0).getWorkName();
            listener.onGetNameSuccess(finalResult);
        }
    }
}
