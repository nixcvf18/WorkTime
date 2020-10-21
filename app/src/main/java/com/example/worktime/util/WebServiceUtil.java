package com.example.worktime.util;
public class WebServiceUtil {
    public static  final String INNER_WIFI_WEB_SERVICE_URL = "http://192.168.100.2:8018/WebService.asmx?wsdl";
   public static  final String MOBILE_DATA_WEB_SERVICE_URL = "http://116.6.44.141:8018/WebService.asmx?wsdl";
    public  static  final String Namespace = "http://tempuri.org/";
    //登录需要调用的函数名
    public  static final String method_checklogin = "CheckLogin";
    public  static final String method_checkTask_Type = "CheckTaskType";
    //返回工号对应的员工姓名
    public  static final String method_checkWorkid_return_W_Name = "CheckWorkidReturnWname";
    //返回当前日期对应的数据
    public static  final  String method_get_performance_with_date="GetPerformanceWithDate";
    public static final String method_performance_add = "Performance_Add";
    //删除工时数据
    public static final String method_performance_delete = "Performance_Delete";
    //修改工时数据
    public static final String method_performance_update = "Performance_Update";
    public static final String method_Query_OrderInfo = "QueryOrderInfo";
    public static final String method_query_perDate = "QueryPerDate";
    public static final String method_query_taskCode = "QueryTaskCode";
}
