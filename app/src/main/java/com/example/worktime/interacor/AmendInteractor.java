package com.example.worktime.interacor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.MaterialBeanBackup;
import com.example.worktime.model.TaskBean;
import com.example.worktime.util.ConstantUtil;
import com.example.worktime.util.GsonUtil;
import com.example.worktime.util.KsoapRequestUtil;
import com.example.worktime.util.WebServiceUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AmendInteractor {
    public static final String TAG = AmendInteractor.class.getSimpleName();
    AmendCreateFinishListener amendCreateFinishListener;
    public interface  AmendCreateFinishListener{
        //获取物料成功后回调
        void onGetMaterialSuccess(List<MaterialBean> materialBeans);
        //获取物料失败后回调
        void onGetMaterialFailed(String failedMessage);
        //获取任务详情后回调
        void onGetTaskSuccess(List<TaskBean> taskBeans);
        //执行修改操作成功后的回调
        void onAmendInfoToSQLServerSuccess(String successMessage);
    }
    //自定义个函数  用来将保存后的内容上传到SQL数据表中
    public void amendInfoToSQLSERVER(String IdSign,String date,
                                    String workerId,
                                    String workerName,
                                    String taskCode,
                                    String orderNumber,
                                    String materialNumber,
                                    String materialDsc,
                                    String opNO,
                                    String OKQty,
                                    String NGQty,
                                    String WorkHour,
                                    String WorkMin,
                                    String Remark,
                                    AmendCreateFinishListener listener
    ){
        this.amendCreateFinishListener = listener;
        /*将录入的数据 保存到SqlServer的表中*/
        Map<String, String> map = new HashMap<>();
        map.put("NO", IdSign);
        map.put("Pdate", date);
        map.put("Workid", workerId);
        map.put("Workname", workerName);
        map.put("TaskCode", taskCode);
        map.put("OrderNo", orderNumber);
        map.put("Item", materialNumber);
        map.put("Itemname",materialDsc);
        map.put("Opno", opNO);
        map.put("OKQty", OKQty);
        map.put("NgQty", NGQty);
        map.put("WorkHour", WorkHour);
        map.put("WorkMin", WorkMin);
        map.put("Remark", Remark);



/*
        Log.e("defaultDateValue", date);
        Log.e("defaultWorkerIdValue", workerId);
        Log.e("defaultWorkerNameValue", workerName);
        Log.e("defaultTaskCodeValue", taskCode);
        Log.e("defaultOrderNumberValue", orderNumber);
        Log.e("defaultMaterialNumberVa", materialNumber);
        Log.e("defaultMaterialDscValue", materialDsc);
        Log.e("defaultOpnoValue", opNO);
        Log.e("defaultOkQtyValue",  OKQty);
        Log.e("defaultNgQtyValue", NGQty);
        Log.e("defaultWorkHourValue", WorkHour);
        Log.e("defaultWorkMinValue", WorkMin);
        Log.e("defaultRemark",  Remark);
*/


        toRequestWebService(WebServiceUtil.method_performance_update, map);
    }
    public void getTaskDescriptionFromWebService(String taskCodeValue, AmendCreateFinishListener listener) {
        this.amendCreateFinishListener = listener;
        Map<String, String> map = new HashMap<>();
        map.put("Code", taskCodeValue);
        toRequestWebService(WebServiceUtil.method_query_taskCode, map);
    }
    /*自定义一个函数  用来进行网络请求 获取界面展示需要的结果*/
    public void getMaterialFromWebService(String orderNumber, AmendCreateFinishListener listener) {
        this.amendCreateFinishListener = listener;
        Map<String, String> map = new HashMap<>();
        map.put("OrderNo", orderNumber);
        toRequestWebService(WebServiceUtil.method_Query_OrderInfo, map);
    }
    private void toRequestWebService(Object... params) {
        if (params[0].equals(WebServiceUtil.method_Query_OrderInfo)) {
            //根据订单号    查询     物料
            new MaterialTask().execute(params);
        } else if (params[0].equals(WebServiceUtil.method_query_taskCode)) {
            //根据任务码    查询    任务详情
            new TaskDescriptionTask().execute(params);
            /*根据返回值   判断是否修改成功*/
        }  else if (params[0].equals(WebServiceUtil.method_performance_update)) {
            new AmendInfoTask().execute(params);
        }
    }
    private class  AmendInfoTask extends AsyncTask<Object,Object,String> {
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
            Log.e(TAG, "onPostExecute: " + s);
            //返回1 代表修改成功
            if (s.equals("1")) {
              amendCreateFinishListener.onAmendInfoToSQLServerSuccess(ConstantUtil.DATAAMENDSUCESS);
            } else {
                amendCreateFinishListener.onAmendInfoToSQLServerSuccess(ConstantUtil.DATAAMENDFAILED);
            }
        }
    }
    private class TaskDescriptionTask extends  AsyncTask<Object,Object,String>{
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
            Log.e(TAG, "onPostExecute: " + s);
            if (!s.equals("请求数据出错")) {
                List<TaskBean> taskBeans;
                taskBeans = GsonUtil.getInstance().getTasklogList(s);
                amendCreateFinishListener.onGetTaskSuccess(taskBeans);
            }
        }
    }
    private class MaterialTask extends AsyncTask<Object, Object, String> {
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
            Log.e(TAG, "onPostExecute: " + s);
            if (!s.equals("请求数据出错")) {
                List<MaterialBean> materialBeans;
                materialBeans = GsonUtil.getInstance().getMaterialList(s);
                /*将得到的数据使用回调函数的方式传回去 PrintPresenter实现的函数中*/
                amendCreateFinishListener.onGetMaterialSuccess(materialBeans);
            } else {
                /*显示当前订单号  不存在的信息*/
                amendCreateFinishListener.onGetMaterialFailed("当前订单号不存在");
            }
        }
    }
}
